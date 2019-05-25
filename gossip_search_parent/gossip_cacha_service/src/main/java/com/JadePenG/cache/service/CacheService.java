package com.JadePenG.cache.service;

/**
 * @author Peng
 * @Description    用户缓存数据
 */
public interface CacheService {

    //调用索引服务, 根据热词获取热点新闻, 缓存到redis中
    public void  cacheNews(String keywords);
}
