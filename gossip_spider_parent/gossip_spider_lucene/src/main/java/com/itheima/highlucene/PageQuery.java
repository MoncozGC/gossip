package com.itheima.highlucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/**
 * @ClassName PageQuery
 * @Description TODO 分页查询
 * 前端---->  后端
 * 当前页码pageNum 2
 * 每页显示条数 pageSize 5
 */
public class PageQuery {
    public static void main(String[] args) throws Exception {
        int pageNum=2;
        int pageSize=5;
        //limit  起始  结束
        int start = (pageNum-1)*pageSize;
        int end =start+pageSize;
        Directory d = FSDirectory.open(new File("d:/index"));
        IndexReader reader = DirectoryReader.open(d);
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        QueryParser queryParser = new QueryParser("content", new IKAnalyzer());
        Query query = queryParser.parse("娱乐");

        TopDocs topDocs = indexSearcher.search(query, end);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs; //end =100  100条结果

        for(int i=start;i<end;i++){//从起始位置开始循环，到end结束
            int id = scoreDocs[i].doc;
            Document doc = indexSearcher.doc(id);
            System.out.println(doc.get("content"));

        }


    }
}
