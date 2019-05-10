package com.JadePenG.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * @author Peng
 * @date 2019/5/15 19:18
 * @Description 多样化查询
 */
public class DftSearcherTest {

    public void baseQuery(Query query) throws Exception {
        Directory d = FSDirectory.open(new File("h:index"));
        IndexReader read = DirectoryReader.open(d);
        IndexSearcher indexSearcher = new IndexSearcher(read);

        QueryParser queryParser = new QueryParser("content", new IKAnalyzer());

        TopDocs topDocs = indexSearcher.search(query, 10);
        int total = topDocs.totalHits;
        System.out.println("总记录数: " + total);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int id = scoreDoc.doc;
            System.out.println("id: " + id);

            float score = scoreDoc.score;
            System.out.println("匹配度: " + score);

            Document doc = indexSearcher.doc(id);
            String content = doc.get("content");
            System.out.println(content + "\r\n");
        }
    }

    //词条查询
    @Test
    public void termQuery() throws Exception {
        /**
        创建词条对象
        搜索的文本必须在词条中, 或者它在中文中是一个词
        注意: 词条是不可在分割的, 词条可以是一个字, 也可以是一句话
        使用场景: 主要是针对的是不可在分割的字段, 例如id
        由于其不可再分, 可以搜索  全文, 但是不能搜索 全文检索*/
        Query query = new TermQuery(new Term("content", "1"));
        baseQuery(query);
    }

    //通配符查询like  _代表一位  % 0-多位   ?代表一位  *代表 0-多位
    @Test
    public void WildcardQuery() throws Exception {
        WildcardQuery wildcardQuery = new WildcardQuery(new Term("content", "?执行*"));
        baseQuery(wildcardQuery);
    }

    //模糊查询
    @Test
    public void FuzzyQuery() throws Exception {
     /**
      模糊查询:
     *      指的是通过替换, 补位, 移动 能够在二次切换内查询数据即可返回
     *          参数1: term  指定查询的字段和内容
     *          参数2: int n   表示最大编辑的次数(系统帮你修改的次数)  最大为: 2次  默认为: 2次
     *
     */
        FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("content", "程行执序可"), 2);
        baseQuery(fuzzyQuery);
    }

    //数值范围查询
    @Test
    public void NumericRangeQuery() throws Exception {
        /**
         * 获取NumericRangeQuery的方式:
         *      通过提供的静态方法获取:
         *          NumericRangeQuery.newIntRange()
         *          NumericRangeQuery.newFloatRange()
         *          NumericRangeQuery.newDoubleRange()
         *          NumericRangeQuery.newLongRange()
         *
         * 数值范围查询:
         *      参数1: 指定要查询的字段
         *      参数2: 指定要查询的开始值
         *      参数3: 指定要查询的结束值
         *      参数4: 是否包含开始
         *      参数5: 是否包含结束
         */
        NumericRangeQuery numericRangeQuery = NumericRangeQuery.newIntRange("id", 2, 5, true, true);
        baseQuery(numericRangeQuery);
    }

    //组合查询
    @Test
    public void BooleanQuery() throws Exception {
        Query fuzzyQuery = new TermQuery(new Term("content", "经历"));
        Query Number2 = NumericRangeQuery.newIntRange("id", 0, 30, true, true);

        /**
         * boolean查询本身没有查询条件，它可以组合其他查询
         *
         *  交集： Occur.MUST + Occur.MUST         where content like '经历' and 2<id<10
            并集：Occur.SHOULD + Occur.SHOULD      where content not like '经历' or 2<id<10
            非：Occur.MUST_NOT                     where content not like '经历' and 2<id<10
         */
        BooleanQuery booleanClauses = new BooleanQuery();
        booleanClauses.add(fuzzyQuery, BooleanClause.Occur.MUST);
        booleanClauses.add(Number2, BooleanClause.Occur.MUST);

        baseQuery(booleanClauses);
    }
}
