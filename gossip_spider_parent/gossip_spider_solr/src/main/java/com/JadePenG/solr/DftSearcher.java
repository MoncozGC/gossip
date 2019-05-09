package com.JadePenG.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.junit.Test;

import javax.management.Query;
import java.util.List;

/**
 * @author Peng
 * @date 2019/5/16 16:36
 * @Description
 */
public class DftSearcher {

    //共有的方法
    public void baseQuery(SolrQuery query) throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");

        QueryResponse response = solrServer.query(query);

        List<SolrDocument> documents = response.getResults();
        for (SolrDocument document : documents) {
            Object id = document.get("id");
            Object content = document.get("content");
            Object title = document.get("title");
            System.out.println("id: " + id + " " + title + "\r\n" + content + "\r\n");
        }
    }

    /**
     * 通配符查询
     *   ? 表示一个字符   * 表示多个字符(0-多个)
     * @throws SolrServerException
     */
    @Test
    public void wildCardQueryToSolr() throws SolrServerException {
        SolrQuery query = new SolrQuery("content:何?");

        //设置显示条数
        query.setRows(2);
        baseQuery(query);
    }


    /**
     *
     * 布尔查询
     * * 1.布尔查询:
     *      AND  OR NOT:
     *      AND : MUST      交集
     *      OR  : SHOULD    并集
     *      NOT : MUST_NOT  非
     */
    @Test
    public void booleanQueryToSolr() throws SolrServerException {
        SolrQuery query = new SolrQuery("content:何? AND 何");
        baseQuery(query);
    }

    /**
     * 子表达式查询
     */
    @Test
    public void expressionQueryToSolr() throws SolrServerException {
        //先执行括号内的, 再执行括号外的
        SolrQuery query = new SolrQuery("content:白? OR content:白?? OR content:白");
        baseQuery(query);
    }


    /**
     * 相似度查询   最大修改为2
     */
    @Test
    public void fuzzQueryToSolr() throws SolrServerException {
        SolrQuery query = new SolrQuery("content: 子娘白~2");
        baseQuery(query);
    }


    /**
     * 范围查询
     * @throws SolrServerException
     */
    @Test
    public void rangeQueryToSolr() throws SolrServerException {
        /**
         *根据id查询, 注意: id是字符串,所以再排序的时候,是根据字符串来排序,
         * 而solr是  先 一位数字排  然后 两位数字排  三位数字排 ...
         * 0
         * 1
         * 2
         * 10
         * 11
         * 12
         * 13
         * 14
         * 15
         * 16
         * 17
         * 18
         * 19
         * 20
         * 100
         * 101
         */

        SolrQuery query = new SolrQuery("id:[0 TO 20]");

        //默认只显示前10条数据
        //query.setRows(20);
        baseQuery(query);
    }
}
