package com.itheima.lucene;

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
 * @ClassName IndexWriter
 * @Description TODO 索引写入测试
 */
public class IndexWriterTest {
    //创建索引，添加到数据库
    @Test
    public void createIndex() throws IOException {

        //创建文件夹对象  索引库的位置
        Directory d = FSDirectory.open(new File("d:/index"));

        //配置对象 参数1：获取最新版本-当前版本 参数2：分词器对数据，进行切割
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST,new IKAnalyzer());
        //索引库打开方式  APPEND 追加（没有文件夹不会创建，会报错） CREATE（会每次都创建文件夹）   默认值CREATE_OR_APPEND
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        //1.创建索引写入器对象  参数1：目录对象  参数2：索引写入器对象的配置对象
        IndexWriter indexWriter = new IndexWriter(d,config);

        //创建文档对象
        Document doc = new Document();
        //向文档对象中添加内容  参数1 ：字段名称  参数2 ：字段的值 参数3：是否存储
        doc.add(new IntField("id",1, Field.Store.YES));
        doc.add(new StringField("title","娱乐第一站", Field.Store.YES));
        //doc.add(new LongField("size",100L, Field.Store.YES));
        TextField textField = new TextField("content", "张云雷被封杀", Field.Store.YES);
        textField.setBoost(100);//默认值是1
        doc.add(textField);

        //将一条文档数据添加到索引库  相当于insert
        indexWriter.addDocument(doc);
        indexWriter.commit();
    }

    @Test
    public void adds() throws Exception {
        //创建文件夹对象  索引库的位置
        Directory d = FSDirectory.open(new File("d:/index"));

        //配置对象 参数1：获取最新版本-当前版本 参数2：分词器对数据，进行切割
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST,new IKAnalyzer());
        //索引库打开方式  APPEND 追加（没有文件夹不会创建，会报错） CREATE（会每次都创建文件夹）   默认值CREATE_OR_APPEND
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        //1.创建索引写入器对象  参数1：目录对象  参数2：索引写入器对象的配置对象
        IndexWriter indexWriter = new IndexWriter(d,config);

        List<Document> docs = new ArrayList<>();

        for(int i=1;i<=200;i++){
            //创建文档对象
            Document doc = new Document();
            //向文档对象中添加内容  参数1 ：字段名称  参数2 ：字段的值 参数3：是否存储
            doc.add(new IntField("id",i, Field.Store.YES));
            doc.add(new StringField("title",ReaderFile.nextTitle(), Field.Store.YES));
            doc.add(new TextField("content",ReaderFile.nextContent(), Field.Store.YES));

            docs.add(doc);

        }

        //将一条文档数据添加到索引库  相当于insert
        indexWriter.addDocuments(docs);
        indexWriter.commit();
    }
}
