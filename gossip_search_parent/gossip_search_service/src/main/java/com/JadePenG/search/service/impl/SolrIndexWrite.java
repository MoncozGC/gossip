package com.JadePenG.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.JadePenG.gossip.pojo.News;
import com.JadePenG.search.service.IndexWriter;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.springframework.beans.factory.annotation.Autowired;



import java.util.List;

/**
 * @author Peng
 * @date 2019/5/19 20:02
 * @Description
 */
//发布服务到注册中心
@Service
public class SolrIndexWrite implements IndexWriter {

    //CloudSolrServer由spring注入进来的,
    //在applicationContext.xml中注入, 没有起名字默认小写
    @Autowired
    private CloudSolrServer cloudSolrServer;

    @Override
    public void saveBeans(List<News> newsList) throws Exception {
        //添加
        cloudSolrServer.addBeans(newsList);
        //提交数据到solr库中
        cloudSolrServer.commit();
    }
}
