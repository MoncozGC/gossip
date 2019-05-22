package com.JadePenG.gossip.service;

import java.util.List;
import java.util.Map;

/**
 * @author Peng
 * @Description     热点新闻
 */
public interface TopKeySearcherService {

    //查询redis中热词排行榜前几条数据
    public List<Map<String,Object>> topkeyFindByNum(Integer num);
}
