package com.JadePenG.solr;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author Peng
 * @date 2019/5/16 13:49
 * @Description
 */
public class News {
    @Field
    private String id;
    @Field
    private String title;
    private String price;
    @Field
    private String brand;
    private String category;
    private String content;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
