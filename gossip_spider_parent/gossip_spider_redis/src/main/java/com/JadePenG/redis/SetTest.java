package com.JadePenG.redis;

import com.JadePenG.util.JedisUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Created by JadePenG on 2019/5/9 13:41
 *
 * set : [a b c]
 */
public class SetTest {

    //赋值, 如果key中的值已有就不会重复添加
    @Test
    public void sadd(){
        Jedis jedis = JedisUtils.getConn();
        jedis.sadd("set1", "a", "b", "c", "a");
        jedis.sadd("set3", "a", "b", "c", "a");
        jedis.sadd("set2", "q", "w", "e", "r");
        jedis.close();
    }

    //获取, 获取所有元素
    @Test
    public void smembers(){
        Jedis jedis = JedisUtils.getConn();
        Set<String> set = jedis.smembers("set1");
        Set<String> set2 = jedis.smembers("set2");
        Set<String> set3 = jedis.smembers("set3");
        System.out.println(set);
        System.out.println(set2);
        System.out.println(set3);
        jedis.close();
    }

    //删除
    @Test
    public void srem(){
        Jedis jedis = JedisUtils.getConn();
        //1. 删除set中指定的成员
        jedis.srem("set1","a");
        jedis.close();
    }

    //判断是否存在
    @Test
    public void sismember(){
        Jedis jedis = JedisUtils.getConn();
        //1. 删除set中指定的成员  1表示存在，0表示不存在或者该key本身就不存在。
        Boolean sismember = jedis.sismember("set1", "a");
        System.out.println(sismember);
        jedis.close();
    }

    // 集合的差集运算
    @Test
    public void sdiff(){
        Jedis jedis = JedisUtils.getConn();
        //1. 删除set中指定的成员  1表示存在，0表示不存在或者该key本身就不存在。
        Set<String> sdiff = jedis.sdiff("set3", "set1");
        // 返回key1与key2中相差的成员，而且与key的顺序有关。那个在前, 返回那个key对应的差集
        Set<String> sdiff2 = jedis.sdiff("set3", "set2");
        Set<String> sdiff3 = jedis.sdiff("set2", "set3");
        System.out.println(sdiff);
        System.out.println(sdiff2);
        System.out.println(sdiff3);
        jedis.close();
    }


    // 集合的交集运算
    @Test
    public void sinter(){
        Jedis jedis = JedisUtils.getConn();
        //1. 返回交集, 两个key都有的, 没有并集返回为空
        Set<String> sinter = jedis.sinter("set3", "set1");
        Set<String> sinter2 = jedis.sinter("set3", "set2");
        Set<String> sinter3 = jedis.sinter("set2", "set3");
        System.out.println(sinter);
        System.out.println(sinter2);
        System.out.println(sinter3);
        jedis.close();
    }


    // 集合的交集运算
    @Test
    public void sunion(){
        Jedis jedis = JedisUtils.getConn();
        //1. 返回交集, 两个key都有的, 没有并集返回为空
        Set<String> sunion = jedis.sunion("set3", "set1");
        Set<String> sunion2 = jedis.sunion("set3", "set2");
        Set<String> sunion3 = jedis.sunion("set2", "set3");
        System.out.println(sunion);
        System.out.println(sunion2);
        System.out.println(sunion3);
        jedis.close();
    }

    // 获取成员的数量
    @Test
    public void scard(){
        Jedis jedis = JedisUtils.getConn();
        //1.
        Long set1 = jedis.scard("set1");
        System.out.println("set1中的数量 : "+set1);
        String set = jedis.srandmember("set1");
        System.out.println("随机返回一个成员 : "+set);
        jedis.close();
    }

        /*
        * - 将key1、key2相差的成员存储在destination上:
          - sdiffstore destination key1 key2…
        - 将返回的交集存储在destination上:
          - sinterstore destination key[key…]
        - 将返回的并集存储在destination上:
          - sunionstore destination key[key…]

        * */
}
