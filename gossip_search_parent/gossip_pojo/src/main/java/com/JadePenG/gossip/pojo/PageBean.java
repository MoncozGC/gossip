package com.JadePenG.gossip.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PageBean
 * @Description
 */
public class PageBean implements Serializable{
    //当前页码  前端给后端的
    private Integer page = 1;
    //每页显示条数，前端给后端--（默认值）
    private Integer pageSize = 15;
    //总记录数  计算出来的
    private Integer pageCount;
    //总页数  后端给前端的
    private Integer pageNum;
    //当前页新闻列表 后端给前端的
    private List<News> newsList;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
