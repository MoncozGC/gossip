package com.JadePenG.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Peng
 * @date 2019/5/15 13:10
 */
public class IndexWriterTest {

    @Test
    public void createIndex() throws IOException {
        //索引库对象---创建文件夹对象, 索引库的位置
        Directory d = FSDirectory.open(new File("h://index"));
        //索引写入器配置对象 ---配置对象, 参数1: 版本(选择最新版本) 参数2: 分词器 默认分词英德
        IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST, new StandardAnalyzer());
        //索引写入器对象---1. 创建索引写入器对象 参数1: 目录对象 参数2: 索引写入器配置对象
        IndexWriter indexWriter = new IndexWriter(d, conf);

        //创建文档对象
        Document doc = new Document();
        //向文档对象中添加内容  param1: 字段名称 param2: 字段的值 param3: 是否存储
        doc.add(new IntField("id", 1, Field.Store.YES));
        doc.add(new StringField("file_name", "pom.xml", Field.Store.YES));
        doc.add(new LongField("size", 100L, Field.Store.YES));
        doc.add(new TextField("content", "说到何洁，传智播客这", Field.Store.YES));

        //执行操作  将一条数据保存到索引库, 相当于insert
        indexWriter.addDocument(doc);
        indexWriter.commit();
    }

    @Test
    public void adds() throws IOException {
        //创建文档对象 索引库的位置
        Directory d = FSDirectory.open(new File("h://index"));
        //配置文件
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
        //创建索引写入器对象
        IndexWriter indexWriter = new IndexWriter(d, config);

        List<Document> docs = new ArrayList<>();
        for (int i = 0; i <= 200; i++) {
            Document doc = new Document();
            doc.add(new IntField("id", i, Field.Store.YES));
            doc.add(new StringField("title", ReaderFile.nextTitle(), Field.Store.YES));
            doc.add(new TextField("content", ReaderFile.nextContent(), Field.Store.YES));

            docs.add(doc);
        }
        indexWriter.addDocuments(docs);
        indexWriter.commit();
    }

    @Test
    public void test() throws IOException {
        File directory = new File("");//参数为空 
        String courseFile = directory.getCanonicalPath();
        String file = courseFile+"\\src\\main\\java\\resources\\ReaderFile__title.txt";
        System.out.println(file);
    }
}
