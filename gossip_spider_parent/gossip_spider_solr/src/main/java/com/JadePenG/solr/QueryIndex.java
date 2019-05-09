package com.JadePenG.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.List;

/**
 * @author Peng
 * @date 2019/5/16 16:18
 * @Description 查询操作
 */
public class QueryIndex {

    /**
     *  基本查询操作
     * @throws SolrServerException
     */
    @Test
    public void baseQueryToSolr() throws SolrServerException {
        //1. 创建SolrServer对象
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
        //2. 执行查询, *:* 查询全部
        SolrQuery solrQuery = new SolrQuery("*:*");

        QueryResponse response = httpSolrServer.query(solrQuery);
        //3. 解析response
        SolrDocumentList documents = response.getResults();

        for (SolrDocument document : documents) {
            Object id = document.get("id");
            Object content = document.get("content");
            Object title = document.get("title");
            System.out.println("id: " + id + " " + title + "\r\n" + content + "\r\n");
        }

        /**
         * 返回javaBean
         * 解析response
         */
        List<News> list = response.getBeans(News.class);
        for (News news : list) {
            System.out.println(news);
        }
    }

}
