package com.itheima.lucene;

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
 * @ClassName IndexSearcherTest
 * @Description TODO 查询索引
 */
public class IndexSearcherTest {

   @Test
   public void searchIndex() throws IOException, ParseException {
       //文件夹对象--索引库的位置
       Directory d = FSDirectory.open(new File("d:/index"));
       //索引读取器--文件夹读取器
       IndexReader reader = DirectoryReader.open(d);
       //1.创建索引查询器
       IndexSearcher indexSearcher = new IndexSearcher(reader);

       // 1基本查询  参数1：查询的字段的名称  参数2：分词器                   //2多样化查询
       QueryParser queryParser = new QueryParser("content", new IKAnalyzer());
       //查询条件  select * from news where content like '蔡徐坤'
       Query query = queryParser.parse("张");
       //执行查询 参数1：查询条件  参数2：查询多少条
       TopDocs topDocs = indexSearcher.search(query, 10);
       int total = topDocs.totalHits;//总记录数
       ScoreDoc[] scoreDocs = topDocs.scoreDocs;//得到的不是文档   ScoreDoc得分文档
       for (ScoreDoc scoreDoc : scoreDocs) {
           float score = scoreDoc.score;
           System.out.println("匹配度"+score);
           int id = scoreDoc.doc;//文档的id lucene的id
           //根据id查询文档库
           Document doc = indexSearcher.doc(id);
           System.out.println(doc.get("id")+doc.get("content"));
       }
   }

}
