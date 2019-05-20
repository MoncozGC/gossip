package com.JadePenG.gossip.pojo;

import java.io.Serializable;

/**
 * @ClassName ResultBean
 * @Description
 *
 * 前端传过来的数据太多了  需要封装数据
 * 返回结果bean 封装前端传递的条件也可以用它封装
 *
 * 既要封装前端给后端的  也要 封装后端给前端的
 */
public class ResultBean implements Serializable{
    //封装页面传递的查询条件 这五个参数是前端给后端的
    private String keywords;
    private String startDate;
    private String endDate;
    private String source;
    private String editor;

    //返回结果中封装pageBean
    private PageBean pageBean;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }
}
