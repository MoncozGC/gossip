package com.JadePenG.lucene;

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
import java.io.Writer;

/**
 * @author Peng
 * @date 2019/5/15 21:15
 * @Description: Lucene索引更新操作(修改)  先进行删除, 在进行添加
 */
public class UpdateIndexTest {
    public static void main(String[] args) throws IOException {
        //索引库对象
        Directory d = FSDirectory.open(new File("h:/index"));
        //索引写入器配置对象
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        //索引写入器对象
        IndexWriter indexWriter = new IndexWriter(d, config);


        // 创建文档对象
        Document doc = new Document();
        doc.add(new IntField("id", 10, Field.Store.YES));
        doc.add(new StringField("title", "Lucene", Field.Store.YES));
        doc.add(new TextField("content", "进行了修改操作", Field.Store.YES));

        //执行更新操作
        indexWriter.updateDocument(new Term("content", "1"), doc);
        //提交
        indexWriter.commit();
        //关闭
        indexWriter.close();
    }
}
