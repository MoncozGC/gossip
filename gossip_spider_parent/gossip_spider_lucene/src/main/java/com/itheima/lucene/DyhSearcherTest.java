package com.itheima.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;

/**
 * @ClassName DyhSearcherTest
 * @Description TODO 多样化查询
 */
public class DyhSearcherTest {

    public void baseQuery(Query query ) throws Exception {
        Directory d = FSDirectory.open(new File("d:/index"));
        IndexReader reader = DirectoryReader.open(d);
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        //where content like  ''  注意，查询条件必须是一个词条
        //执行查询
        TopDocs topDocs = indexSearcher.search(query, 10);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int id = scoreDoc.doc;
            Document doc = indexSearcher.doc(id);
            System.out.println(scoreDoc.score+" "+doc.get("id")+" "+doc.get("title")+" "+doc.get("content"));
        }
    }

    //词条查询
    @Test
    public void termQuery() throws Exception {
        Query query = new TermQuery(new Term("content","1"));
        //执行查询
        baseQuery(query);
    }

    //通配符查询  like  _代表一位  % 0-多位   ?代表一位  *代表 0-多位
    @Test
    public void wildcardQuery() throws Exception {
        Query query = new WildcardQuery(new Term("content","何*"));
        //执行查询
        baseQuery(query);
    }

    //模糊匹配
    @Test
    public void fuzzyQuery() throws Exception {
        Query query = new FuzzyQuery(new Term("content","徐坤蔡"));//最大调整次数是2
        baseQuery(query);
    }

    //数值范伟查询
    @Test
    public void numericRangeQuery() throws Exception {
        //创建数值范伟查询条件，查询的类型是int 1字段名称 2-10  参数4 是否包含最小值  参数5 是否包含最大值
        Query query = NumericRangeQuery.newIntRange("id", 2, 10, false, false);
        baseQuery(query);
    }

    //布尔查询 本身并不是查询条件，可以组装查询条件，多条件查询


    @Test
    public void booleanQuery() throws Exception {

        Query query1 = new TermQuery(new Term("content","白"));
        Query query2 = NumericRangeQuery.newIntRange("id", 2, 10, false, false);
        BooleanQuery query = new BooleanQuery();//组装  where content not like "白百何" or 2<id<10
        // 交集： Occur.MUST + Occur.MUST
        // 并集：Occur.SHOULD + Occur.SHOULD
        // 非：Occur.MUST_NOT
        query.add(query1, BooleanClause.Occur.SHOULD);
        query.add(query2, BooleanClause.Occur.SHOULD);
        baseQuery(query);
    }
}
