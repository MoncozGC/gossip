package com.JadePenG.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import java.io.IOException;

/**
 * @author Peng
 * @date 2019/5/16 13:54
 * @Description   删除操作
 */
public class DeleteIndex {
    public static void main(String[] args) throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");

        //删除id为2的
        //solrServer.deleteById("2");
        //删除全部
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }
}
