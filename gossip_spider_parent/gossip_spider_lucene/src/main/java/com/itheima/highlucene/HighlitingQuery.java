package com.itheima.highlucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName HighlitingQuery
 * @Description TODO 高亮查询
 */
public class HighlitingQuery {

    public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {


        Directory d = FSDirectory.open(new File("d:/index"));
        IndexReader reader = DirectoryReader.open(d);
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        QueryParser queryParser = new QueryParser("content",new IKAnalyzer());
        Query query = queryParser.parse("娱乐");

        //要对条件怎样包裹   <em>娱乐</em>
        Formatter format = new SimpleHTMLFormatter("<font style='color:red'>","</font>");
        Scorer socrer = new QueryScorer(query);
        //创建高亮对象
        Highlighter highlighter = new Highlighter(format,socrer);

        TopDocs topDocs = indexSearcher.search(query, 10);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int id = scoreDoc.doc;
            Document doc = indexSearcher.doc(id);//当前结果并不是高亮的，需要处理结果
            //我们要对没有高亮的结果进行高亮处理  参1：ik 参数2：对那个字段上的值进行高亮，参数3：你要处理的内容
            String content = highlighter.getBestFragment(new IKAnalyzer(), "content", doc.get("content"));
            System.out.println(content);
        }

    }


}
