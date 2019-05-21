package com.JadePenG.gossip.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

/**
 *  Serializable 实现序列化接口在传递数据的时候要将pojo类转换为二进制的流, 所以需要序列化
 * @author Peng
 * @date 2019/5/19 19:40
 * @Description
 */
public class News implements Serializable {

    @Field
    private String id;
    @Field
    private String title;
    @Field
    private String content;
    @Field
    private String docurl;
    @Field
    private String time;
    @Field
    private String source;
    @Field
    private String editor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDocurl() {
        return docurl;
    }

    public void setDocurl(String docurl) {
        this.docurl = docurl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}
