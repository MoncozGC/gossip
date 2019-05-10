package com.itheima.highlucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/**
 * @ClassName SortQuery
 * @Description TODO
 */
public class SortQuery {

    public static void main(String[] args) throws Exception {
        Directory d = FSDirectory.open(new File("d:/index"));
        IndexReader reader = DirectoryReader.open(d);
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        QueryParser queryParser = new QueryParser("content", new IKAnalyzer());
        Query query = queryParser.parse("娱乐");

        //创建排序对象  where content like '娱乐' order by  id   参数1：根据那个字段排序 参数2:字段的类型 参数3：反转 true是反转 降序 DESC
        SortField sortField = new SortField("id", SortField.Type.INT,true);
        Sort sort = new Sort(sortField);
        TopFieldDocs fieldDocs = indexSearcher.search(query, 10, sort);

        ScoreDoc[] scoreDocs = fieldDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int id = scoreDoc.doc;
            Document doc = indexSearcher.doc(id);
            System.out.println(doc.get("id")+" "+scoreDoc.score+" "+doc.get("content"));
        }
    }

}
