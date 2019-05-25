package com.JadePenG.cache.service.impl;


import com.JadePenG.cache.service.CacheService;
import com.JadePenG.gossip.pojo.PageBean;
import com.JadePenG.gossip.pojo.ResultBean;
import com.JadePenG.search.service.IndexSearcher;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.container.page.Page;
import com.google.gson.Gson;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.ParseException;

/**
 * @author Peng
 * @Description
 */
@Component
public class CacheServiceImpl implements CacheService {

    //1. 调用索引服务
    @Reference
    private IndexSearcher indexSearcher;

    @Autowired
    private JedisPool jedisPool = new JedisPool();

    private Gson gson = new Gson();


    @Override
    public void cacheNews(String keywords) {
        try {
            //2. 根据热词查询热点新闻
            ResultBean resultBean = new ResultBean();
            resultBean.setKeywords(keywords);
            System.out.println(keywords);
            //查询的时候pageBean对象不能为空, 获取分页参数和封装pageBean的时候会报空指针异常
            //null 和 空 不是一个意思
            PageBean pageBean = new PageBean();
            //给pageBean设置一个值
            resultBean.setPageBean(pageBean);
            //查询的是第几页的数据  默认查询的是第1页数据
            resultBean = indexSearcher.findByKeywords(resultBean);
            //得到总页数
            Integer pageNum = resultBean.getPageBean().getPageNum();
            System.out.println(pageNum);
            //只缓存5页  判断是否空值, 空值就不缓存有数据才缓存
            if (pageNum != null) {
                //当大于5, 就等于5 不再缓存
                if (pageNum > 5) {
                    pageNum = 5;
                }
                for (int i = 1; i <= pageNum; i++) {
                    //查询的就是第i页的数据
                    pageBean.setPage(i);
                    resultBean.setPageBean(pageBean);
                    //System.out.println();
                    //返回回来的是第i页数据
                    resultBean = indexSearcher.findByKeywords(resultBean);
                    System.out.println(resultBean);
                    String resultBeanStr = gson.toJson(resultBean);
                    //3. 将数据缓存到redis中
                    Jedis jedis = jedisPool.getResource();
                    jedis.set(keywords + ":" + i, resultBeanStr);
                    jedis.close();

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
