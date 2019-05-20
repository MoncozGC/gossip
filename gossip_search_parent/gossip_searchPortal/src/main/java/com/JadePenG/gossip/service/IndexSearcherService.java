package com.JadePenG.gossip.service;

import com.JadePenG.gossip.pojo.News;
import com.JadePenG.gossip.pojo.ResultBean;
import org.apache.solr.client.solrj.SolrServerException;

import java.util.List;

/**
 * @author Peng
 * @Description    门户系统的搜索接口
 */
public interface IndexSearcherService {

    //接受web层传递的参数, 调用索引服务(service工程), 查询数据, 进行返回给web
    //public List<News> findByKeywords(String keywords) throws SolrServerException;
    public ResultBean findByKeywords(ResultBean resultBean) throws Exception;
}
