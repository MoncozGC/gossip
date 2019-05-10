package com.itheima.lucene;

import org.apache.lucene.document.*;
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
 * @ClassName UpdateIndexTest
 * @Description TODO 修改索引 增删改用的都是IndexWriter  先删除，在添加
 */
public class UpdateIndexTest {
    public static void main(String[] args) throws IOException {

        Directory d = FSDirectory.open(new File("d:/index"));
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST,new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(d,config);

        Document doc = new Document();
        doc.add(new IntField("id",10, Field.Store.YES));
        doc.add(new StringField("title","lucene第二课", Field.Store.YES));
        doc.add(new TextField("content","今天我们学习了lucene", Field.Store.YES));
        // 参数1，根据条件查询要修改的内容 要被覆盖的内容  参数2:拿什么去覆盖
        indexWriter.updateDocument(new Term("content","1"),doc);
        indexWriter.commit();

    }
}
