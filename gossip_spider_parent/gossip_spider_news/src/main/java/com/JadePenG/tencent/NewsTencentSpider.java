package com.JadePenG.tencent;

import com.JadePenG.dao.NewsDao;
import com.JadePenG.pojo.News;
import com.JadePenG.system.Constants;
import com.JadePenG.util.HttpClientUtils;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by JadePenG on 2019/5/12 23:39
 */
public class NewsTencentSpider {

    private static Gson gson = new Gson();
    private static NewsDao newsDao = new NewsDao();

    public static void main(String[] args) throws IOException {
        //1.1.确定地址 热点
        String topIndexUrl = "https://pacaio.match.qq.com/irs/rcd?cid=137&token=d0f13d594edfc180f5bf6b845456f3ea&id=&ext=ent&num=200";
        //1.2 发送请求, 获取数据
        String jsonString = getJson(topIndexUrl);

        //1.3 解析数据 得到新闻列表
        List<News> newsList = parseJson(jsonString);

        //1.4 保存数据
        saveNewsList(newsList);

        //2.1非热点
        String noTopIndexUrl = "https://pacaio.match.qq.com/irs/rcd?cid=137&token=d0f13d594edfc180f5bf6b845456f3ea&id=&ext=ent&num=200";

        Integer page = 1;
        while (true) {
            System.out.println(noTopIndexUrl);
            String noTopjsonString = getJson(noTopIndexUrl);

            Map<String, Object> map = gson.fromJson(noTopjsonString, Map.class);
            Double datanum = (Double) map.get("datanum");
            if (datanum == 0) {
                break;
            }

            //1.3解析json 得到新闻列表
            List<News> noTopnewsList = parseJson(noTopjsonString);

            //1.4保存数据
            saveNewsList(noTopnewsList);

            noTopIndexUrl = "https://pacaio.match.qq.com/irs/rcd?cid=58&token=c232b098ee7611faeffc46409e836360&ext=ent&page=" + page;
            page++;

        }
    }

    /**
     * 发送请求, 获取json数据
     *
     * @param indexUrl
     * @return
     */
    private static String getJson(String indexUrl) throws IOException {
        String jsonString = HttpClientUtils.doGet(indexUrl);
        return jsonString;
    }


    /***
     *   解析数据 得到新闻列表
     * @param jsonString
     * @return
     */
    private static List<News> parseJson(String jsonString) {

        List<News> newsList = new ArrayList<>();

        Map<String, Object> map = gson.fromJson(jsonString, Map.class);
        //得到新闻部分数据，转化为新闻列表
        List<Map<String, String>> mapList = (List<Map<String, String>>) map.get("data");
        for (Map<String, String> newsMap : mapList) {//遍历，得到新闻map--->news对象--->添加到list中
            News news = new News();
            //解析title
            String title = newsMap.get("title");
            news.setTitle(title);
            //解析content
            String content = newsMap.get("intro");
            news.setContent(content);
            //解析time
            String time = newsMap.get("publish_time");
            news.setTime(time);
            //解析editor
            String editor = newsMap.get("source");
            news.setEditor(editor);
            //解析source
            String source = newsMap.get("source");
            news.setSource(source);
            //解析docurl
            String docurl = newsMap.get("url");
            news.setDocurl(docurl);

            //获取到的url一定要保存到数据库吗？验证去重
            Jedis jedis = new Jedis("192.168.190.100", 6379);
            Boolean sismember = jedis.sismember(Constants.NEWS_TENCENT_URL, docurl);
            jedis.close();
            if (sismember) {
                //爬取过
                continue;
            }

            //保存数据
            newsList.add(news);
        }
        return newsList;
    }


    /**
     *  保存数据
     * @param newsList
     */
    private static void saveNewsList(List<News> newsList) {
        for (News news : newsList) {
            newsDao.saveNews(news);
            Jedis jedis = new Jedis("192.168.190.100", 6379);
            jedis.sadd(Constants.NEWS_TENCENT_URL,news.getDocurl());
            jedis.close();
        }
    }
}
