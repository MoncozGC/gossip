package com.JadePenG.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Peng
 * @date 2019/5/16 11:12
 * @Description    添加操作 ,  当ID相同时添加就是修改操作
 */
public class CreateIndex {

    @Test
    public void add() throws IOException, SolrServerException {
        SolrServer httpSolrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "2");
        doc.addField("content", "solr的添加1111");
        doc.addField("title", "solr");

        httpSolrServer.add(doc);
        httpSolrServer.commit();
    }

    //使用javaBean进行写入
    @Test
    public void addBean() throws IOException, SolrServerException {
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");

        News news = new News();
        news.setId("10");
        news.setContent("iPhone X 64G");
        news.setBrand("苹果");
        news.setPrice("9900");
        news.setTitle("亮瞎你的双眼,没办法就是贵");
        news.setCategory("手机");

        httpSolrServer.addBean(news);
        httpSolrServer.commit();
    }

    //添加文本文件中的内容
    @Test
    public void adds() throws IOException, SolrServerException {
        HttpSolrServer httpSolrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");

        List<SolrInputDocument> docs = new ArrayList<>();

        for (int i = 0; i <= 200; i++) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", i + "");
            doc.addField("title", ReaderFile.nextTitle());
            doc.addField("content", ReaderFile.nextContent());

            docs.add(doc);
        }
        httpSolrServer.add(docs);
        httpSolrServer.commit();
    }

}
