package com.JadePenG.gossip.mapper;

import com.JadePenG.gossip.pojo.News;

import java.util.List;

/**
 * @author Peng
 * @date 2019/5/19 20:38
 * @Description
 */
public interface NewsMapper {

    //查询最大id后100条数据
    public List<News> queryListByMaxId(String id);

    //查询本次id的最大值
    public String queryNextMaxIdByMaxId(String id);
}
