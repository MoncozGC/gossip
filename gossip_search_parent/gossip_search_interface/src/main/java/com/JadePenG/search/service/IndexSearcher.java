package com.JadePenG.search.service;

import com.JadePenG.gossip.pojo.News;
import com.JadePenG.gossip.pojo.ResultBean;
import org.apache.solr.client.solrj.SolrServerException;

import java.text.ParseException;
import java.util.List;

/**
 * @author Peng
 * @date 2019/5/21 11:09
 * @Description   用于查询索引库的接口
 */
public interface IndexSearcher {

    //根据页面输入的关键字,查询索引库新闻列表
    //public List<News> findByKeywords(String keywords) throws SolrServerException;
    public ResultBean findByKeywords(ResultBean resultBean) throws SolrServerException, ParseException;

}
