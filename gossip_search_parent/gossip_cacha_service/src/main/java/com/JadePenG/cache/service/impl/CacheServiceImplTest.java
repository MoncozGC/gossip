package com.JadePenG.cache.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Peng
 * @Description     模拟热词,缓存热点新闻到redis中
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class CacheServiceImplTest {

    @Autowired
    private CacheServiceImpl cacheServiceImpl;

    @Test
    public void CacheTest(){
        cacheServiceImpl.cacheNews("娱乐");
    }

}
