package com.JadePenG.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * @author Peng
 * @date 2019/5/15 16:42
 * @Description: 查询
 */
public class IndexSearcherTest {

    @Test
    public void searchIndex() throws IOException, ParseException {
        //创建文件夹对象  索引库位置
        Directory d = FSDirectory.open(new File("h://index"));
        //创建索引读取器--文件夹读取器
        IndexReader reader = DirectoryReader.open(d);
        //1. 创建索引查询器
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        //基本查询 参数1: 查询字段的名称   参数2: 分词器
        QueryParser queryParser = new QueryParser("content", new IKAnalyzer());
        //查询条件  相当于sql语句: select * from news where content like 'aaa';
        Query query = queryParser.parse("何");
        //执行查询 参数1: 查询条件  参数2: 查询条数
        TopDocs topDocs = indexSearcher.search(query, 10);

        //获取查询到的总记录数    scoreDocs: 得到分值的文档
        int total = topDocs.totalHits;

        //获取分值  匹配度的分值(不大于1, 1是完全匹配)
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            float score = scoreDoc.score;
            System.out.println("匹配度: " + score);

            //文档的id lucene的id
            int id = scoreDoc.doc;
            System.out.println("ID: " + id);

            //根据id查询文档库
            Document doc = indexSearcher.doc(id);
            System.out.println(doc.get("id") +"  "+ doc.get("content")+"\r\n");
        }
    }
}
