package com.JadePenG.higsolr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author Peng
 * @date 2019/5/16 17:56
 * @Description
 */
public class HighQuery {

    private void baseQuery(SolrQuery query) throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
        QueryResponse response = solrServer.query(query);
        SolrDocumentList list = response.getResults();
        for (SolrDocument doc : list) {
            System.out.println(doc.get("id")+" "+doc.get("title")+" "+doc.get("content"));
        }
    }

    /**
     * 排序   asc:从小到大 正序  desc: 从大到小 倒叙
     */
    @Test
    public void sortQuery() throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery("id:[0 TO 200]");
        SolrQuery.SortClause sortClause = new SolrQuery.SortClause("id", SolrQuery.ORDER.asc);
        solrQuery.setSort(sortClause);
        baseQuery(solrQuery);
    }

    /**
     * 分页
     * @throws SolrServerException
     */
    @Test
    public void pageQuery() throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery("id:[0 TO 200]");
        SolrQuery.SortClause sortClause = new SolrQuery.SortClause("id", SolrQuery.ORDER.asc);
        solrQuery.setSort(sortClause);

        //设置起始位置
        solrQuery.setStart(0);
        //设置显示条数
        solrQuery.setRows(30);
        baseQuery(solrQuery);
    }

    /**
     * 高亮
     */
    @Test
    public void highlitingQuery() throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");


        SolrQuery solrQuery = new SolrQuery("content:娱乐");
        SolrQuery.SortClause sortClause = new SolrQuery.SortClause("id", SolrQuery.ORDER.asc);
        solrQuery.setSort(sortClause);

        solrQuery.setStart(0);
        solrQuery.setRows(30);

        //对查询对象进行高亮设定
        //开启高亮
        solrQuery.setHighlight(true);
        //寻找的字段
        solrQuery.addHighlightField("content");
        solrQuery.setHighlightSimplePost("<em>");
        solrQuery.setHighlightSimplePre("</em>");

        QueryResponse response = solrServer.query(solrQuery);
        //没有被高亮的doc对象
        response.getResults();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        for (String id : highlighting.keySet()) {
            Map<String, List<String>> stringListMap = highlighting.get(id);
            for (String fieldName : stringListMap.keySet()) {
                List<String> strings = stringListMap.get(fieldName);
                String content = strings.get(0);
                System.out.println(content);
            }
        }

    }
}
