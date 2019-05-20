package com.JadePenG.gossip.service.impl;

import com.JadePenG.gossip.mapper.NewsMapper;
import com.JadePenG.gossip.pojo.News;
import com.JadePenG.gossip.service.IndexWriterService;
import com.JadePenG.search.service.IndexWriter;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Peng
 * @date 2019/5/19 21:09
 * @Description
 */
//发布服务使用dobbo的service, 选择duboo会占用20880端口号
//这里选择普通的service
@Service
public class IndexWriteServiceImpl implements IndexWriterService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private NewsMapper newsMapper;

    @Reference(timeout = 5000000)
    private IndexWriter indexWriter;

    @Override
    public void saveBean() throws Exception {
        Jedis jedis = jedisPool.getResource();
        String id = jedis.get("bigdataJade:gossip:maxid");
        jedis.close();
        //第一次查询进行判断
        if(id==null){
            id="0";
        }

        while (true) {
            System.out.println("最大id："+id);
            List<News> newsList = newsMapper.queryListByMaxId(id);
            //当list为空时跳出循环
            if (newsList == null || newsList.size() <= 0) {
                //最大id，如果当前并不是没有增量，而是循环结束了，
                // 最大id可能发生了变化，将最大id更新到redis中
                System.out.println("没有增量数据了");
                jedisPool.getResource();
                jedis.set("bigdataJade:gossip:maxid", id);
                break;
            }
            System.out.println("当前查询到: "+newsList.size());

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            for (News news : newsList) {
                String time = news.getTime();
                Date oldTime = format1.parse(time);
                String newTime = format2.format(oldTime);
                news.setTime(newTime);
            }

            indexWriter.saveBeans(newsList);
            id = newsMapper.queryNextMaxIdByMaxId(id);
        }


    }

}
