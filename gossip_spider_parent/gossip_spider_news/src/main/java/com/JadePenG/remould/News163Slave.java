package com.JadePenG.remould;

import com.JadePenG.pojo.News;
import com.JadePenG.system.Constants;
import com.JadePenG.util.HttpClientUtils;
import com.JadePenG.util.IdWorker;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

/**
 * Created by JadePenG on 2019/5/13 11:39
 * 1.从redis中的list获取到url
 * 2.根据url发送请求，获取到新闻详情页html
 * 3.解析html，封装一个news对象，将对象转为json，保存到redis的list中
 */
public class News163Slave {
    private static Gson gson = new Gson();
    //private static IdWorker idWorker = new IdWorker();

    public static void main(String[] args) throws IOException {
        while (true) {
            Jedis jedis = new Jedis("192.168.190.100", 6379);
            List<String> brpop = jedis.brpop(20, Constants.NEWS_LIST_URL);
            if (brpop == null || brpop.size() == 0) {
                break;
            }
            String url = brpop.get(1);
            parseNewsJson(url);
        }

    }


    /**
     * @param docurl
     * @Description 通过新闻的url获取新闻的详情内容
     */
    private static void parseNewsJson(String docurl) throws IOException {
        //利用工具类, 发送请求获取html源码
        String html = HttpClientUtils.doGet(docurl);

        //解析页面,获取document
        Document document = Jsoup.parse(html);

        //创建新闻对象
        News news = new News();

        /*
        //新添加ID  数据库有问题所有不获取id
        long id = idWorker.nextId();
        news.setId(id+"");*/

        //解析标题   https://ent.163.com/19/0512/07/EEV8DG4M00038FO9.html
        Elements el1 = document.select("#epContentLeft h1");
        String title = el1.get(0).text();
        news.setTitle(title);

        //解析时间和来源
        Elements el2 = document.select("#epContentLeft > div[class=post_time_source]");
        String timeSource = el2.get(0).text();
        //页面中, 时间和来源是在同一个p标签中, 所以我们进行切割
        String[] split = timeSource.split("　来源: ");
        news.setTime(split[0]);
        news.setSource(split[1]);

        //解析新闻编辑
        Elements el3 = document.select(".ep-editor");
        // " : " 切割  页面内容是 ->责任编辑：杨明_NV5736 | 但是不需要 "责任编辑: " 这几个字,所以利用": "进行切割
        String editor = el3.get(0).text().split("责任编辑：")[1];
        news.setEditor(editor);

        //解析内容  p[class!=f_center] 新闻列表中, 只有正文文本没有 class="f_center" 这个属性, 可以根据这个条件来删选
        Elements el4 = document.select("#endText > p[class!=f_center]");
        String content = "";
        for (Element element : el4) {
            content += element.text();
        }
        news.setContent(content);

        //设置url
        news.setDocurl(docurl);

        /*
        //保存数   据
        newsDao.saveNews(news);
        //将爬取过的url存到redis的去重set中   存入redis数据库中
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        jedis.sadd(Constants.NEWS_163_URL, docurl);
        jedis.close();
        */

        //将news对象转为json，
        String newString = gson.toJson(news);
        System.out.println(newString);
        //保存到redis的一个list（news-json）集合中
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        jedis.lpush(Constants.NEWS_LIST_NEWSJSON, newString);
        jedis.close();

    }
}
