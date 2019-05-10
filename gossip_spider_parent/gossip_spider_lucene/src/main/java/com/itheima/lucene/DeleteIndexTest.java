package com.itheima.lucene;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName DeleteIndexTest
 * @Description TODO
 */
public class DeleteIndexTest {
    public static void main(String[] args) throws IOException {

        Directory d = FSDirectory.open(new File("d:/index"));
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST,new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(d,config);

        //indexWriter.deleteAll();//删除所有
        indexWriter.deleteDocuments(new Term("content","何"));//根据条件删除
        indexWriter.commit();
    }
}
