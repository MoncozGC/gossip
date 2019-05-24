package com.JadePenG.remould;

import com.JadePenG.dao.NewsDao;
import com.JadePenG.pojo.News;
import com.JadePenG.system.Constants;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by JadePenG on 2019/5/13 11:39
 * <p>
 * 1. 判断去重
 * 2. 保存对象到数据库 NewsDao
 * 3. 将保存的对象的url放到redis的set中 ,用于后期的去重
 */
public class PublicDaoNode {

    private static Gson gson = new Gson();
    private static NewsDao newsDao = new NewsDao();

    public static void main(String[] args) {
        while (true) {
            Jedis jedis = new Jedis("192.168.190.100", 6379);
            List<String> brpop = jedis.brpop(20, Constants.NEWS_LIST_NEWSJSON);
            jedis.close();
            if(brpop==null || brpop.size()==0){
                break;
            }
            System.out.println(brpop);
            String newsJsonString = brpop.get(1);
            News news = gson.fromJson(newsJsonString, News.class);
            //判断去重
            jedis = new Jedis("192.168.190.100", 6379);
            Boolean sismember = jedis.sismember(Constants.NEWS_SET_URL, news.getDocurl());
            if (sismember){
                continue;
            }
            jedis.close();

            // 将数据发送到kafka的生产者  增量更新  全量更新
            newsProducer.sendNewsToKafka(newsJsonString);
            newsDao.saveNews(news);

            jedis = new Jedis("192.168.190.100",6379);
            jedis.sadd(Constants.NEWS_SET_URL, news.getDocurl());
            jedis.close();

        }
    }
}
