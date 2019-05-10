package com.JadePenG.lucene;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * @author Peng
 * @date 2019/5/15 21:31
 * @Description    删除操作
 */
public class DeleteIndex {
    public static void main(String[] args) throws IOException {
        Directory d = FSDirectory.open(new File("H:/index"));
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(d, config);

        // 执行删除操作(根据词条)，要求id字段必须是字符串类型
        //indexWriter.deleteDocuments(new Term("id", "1"));
        //根据查询条件删除
        //indexWriter.deleteDocuments(NumericRangeQuery.newIntRange("id", 2, 10, true, true));
        //删除所有
        indexWriter.deleteAll();

        indexWriter.commit();
        indexWriter.close();
    }
}
