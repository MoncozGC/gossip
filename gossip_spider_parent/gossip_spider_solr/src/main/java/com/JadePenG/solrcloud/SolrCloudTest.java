package com.JadePenG.solrcloud;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;

/**
 * @author Peng
 * @date 2019/5/17 19:36
 * @Description
 */
public class SolrCloudTest {
    public static void main(String[] args) throws SolrServerException {
        //node01--> 在本地hosts中需要配置定向到自己服务器的ip地址
        String zhHost = "node01:2181,node02:2181,node03:2181";
        CloudSolrServer cloudSolrServer = new CloudSolrServer(zhHost);

        //默认连接的collection
        cloudSolrServer.setDefaultCollection("collection1");
        //solr集群连接zookeeper的超时时间
        cloudSolrServer.setZkClientTimeout(5000);
        //连接solr的超时时间
        cloudSolrServer.setZkConnectTimeout(5000);

        //查询条件
        SolrParams solrQuery = new SolrQuery("*:*");
        QueryResponse response = cloudSolrServer.query(solrQuery);
        SolrDocumentList documentList = response.getResults();
        for (SolrDocument entries : documentList) {
            System.out.println(entries.get("id")+" " +entries.get("title")+" "+entries.get("content"));
        }
    }
}
