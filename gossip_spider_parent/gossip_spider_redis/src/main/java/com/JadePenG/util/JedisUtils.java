package com.JadePenG.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by JadePenG on 2019/5/9 20:19
 */
public class JedisUtils {
    private static JedisPool jedisPool;

    static {
        //创建连接池对象
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最小空闲数
        poolConfig.setMinIdle(5);
        //最大空闲数
        poolConfig.setMaxIdle(20);
        //最长等待时间
        poolConfig.setMaxWaitMillis(1000);
        //最多不超过100个链接
        poolConfig.setMaxTotal(100);
        jedisPool = new JedisPool(poolConfig,"192.168.190.100", 6379);
    }

    //JedisUtils.getConn就会返回一个jedis对象  从连接池中获取一个对象
    public static Jedis getConn(){
        return jedisPool.getResource();
    }
}
