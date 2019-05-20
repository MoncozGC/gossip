package com.JadePenG.gossip.service.impl;

import com.JadePenG.gossip.pojo.News;
import com.JadePenG.gossip.pojo.ResultBean;
import com.JadePenG.gossip.service.IndexSearcherService;
import com.JadePenG.search.service.IndexSearcher;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Peng
 * @Description
 */
@Service
public class IndexSearcherServiceImpl implements IndexSearcherService{

    @Reference
    private IndexSearcher indexSearcher;

    @Override
    public ResultBean findByKeywords(ResultBean resultBean) throws Exception {
        return indexSearcher.findByKeywords(resultBean);
    }
}
