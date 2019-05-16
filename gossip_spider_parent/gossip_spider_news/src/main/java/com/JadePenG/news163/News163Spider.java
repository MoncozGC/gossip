package com.JadePenG.news163;

import com.JadePenG.dao.NewsDao;
import com.JadePenG.pojo.News;
import com.JadePenG.system.Constants;
import com.JadePenG.util.HttpClientUtils;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by JadePenG on 2019/5/12 15:38
 */
public class News163Spider {


    private static Gson gson = new Gson();

    private static NewsDao newsDao = new NewsDao();

    public static void main(String[] args) throws Exception {
        //1. 确定url
        String index163Url = "https://ent.163.com/special/000380VU/newsdata_index.js?callback=data_callback";

        //2. 发送请求, 获取数据   数据的格式 --> json
        //解析数据
        //parseNewsListJson(index163Url);

        //3. 分页
        page(index163Url);

    }

    /**
     * @param jsonString
     * @Description 解析数据Json
     */
    private static void parseNewsListJson(String jsonString) throws IOException {

        //String jsonString = HttpClientUtils.doGet(index163Url);

        //源码中不是完整的Json格式数据, 它前面还有 data_callback([ ])  data_callback( 和 ) 不需要, []里面是数组形式的Json
        //进行截取  ( 左括号以前前面的数据不需要  ) 右括号不需要
        int first = jsonString.indexOf("(");//左半括号的下标
        int last = jsonString.lastIndexOf(")");//右半括号的下标
        String newsListJson = jsonString.substring(first + 1, last);
        //得到每一条新闻的url
        List<Map<String, String>> list = gson.fromJson(newsListJson, List.class);
        //循环新闻对象列表，获取每条新闻url
        for (Map<String, String> map : list) {
            String docurl = map.get("docurl");
            //过滤图片新闻和视频新闻
            if (!docurl.contains("ent.163.com") || docurl.contains("photoview")) {
                //如果是,就跳过不爬取
                continue;
            }
            System.out.println(docurl);

            //判断url是否爬取过，如果爬取过，就不在爬取
            Jedis jedis = new Jedis("192.168.190.100", 6379);
            Boolean sismember = jedis.sismember(Constants.NEWS_163_URL, docurl);
            jedis.close();
            if(sismember){
                //跳过
                continue;
            }
            //获取新闻列表, 遍历获取每天新闻详情内容
            parseNewsJson(docurl);
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

        //保存数据
        newsDao.saveNews(news);
        //将爬取过的url存到redis的去重set中   存入redis数据库中
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        jedis.sadd(Constants.NEWS_163_URL, docurl);
        jedis.close();

    }

    private static String pageParse(String indexUrl) throws IOException {
        //发送请求获取列表数据
        String jsonString = HttpClientUtils.doGet(indexUrl);

        return jsonString;

    }

    /**
     * 分页
     *
     * @param index163Url
     */
    private static void page(String index163Url) throws IOException {

        Integer page = 2;
        while (true) {
            String index = "";
            String json = pageParse(index163Url);
            if (json == null || "".equals(json)) {
                break;
            }
            //如果不跳出循环, 解析数据
            parseNewsListJson(json);

            //如果页面小于10, 那么应该是0x(根据具体的地址而定)  大于10就让它等于page
            if (page < 10) {
                index = "_0" + page;
            } else {
                index = "_" + page;
            }

            //拼接无数次  如果有数据，我就拼接，如果没数据，跳出循环
            index163Url = "https://ent.163.com/special/000380VU/newsdata_index" + index + ".js?callback=data_callback";
            System.out.println(index163Url);
            page++;
        }
    }

}
