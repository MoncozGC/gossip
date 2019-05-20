package com.JadePenG.search.service;

import com.JadePenG.gossip.pojo.News;

import java.util.List;

/**
 * @author Peng
 * @date 2019/5/19 19:56
 * @Description     newsList: 门户系统传递过来的新闻数据
 */
public interface IndexWriter {

    //将查询到的数据保存到solr库中
    public void saveBeans(List<News> newsList) throws Exception;
}
