package com.JadePenG.redis;

import com.JadePenG.util.JedisUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by JadePenG on 2019/5/9 13:42
 *
 * SortedSet : ZSet  可以用作排行榜
 */
public class Sortedset {

    @Test
    public void zadd(){
        Jedis jedis = JedisUtils.getConn();
        jedis.zadd("zset1",11,"set1");
        jedis.zadd("zset2",12,"set2");
        jedis.zadd("zset3",13,"set3");
        jedis.zadd("zset3",14,"set3");
        //2. 获取元素
        Double zscore = jedis.zscore("zset1", "set1");
        System.out.println(zscore);
        //3. 获取集合中的成员数量
        Long zset2 = jedis.zcard("zset3");
        System.out.println(zset2);
        jedis.close();
    }


    //删除元素
    @Test
    public void zrem(){
        Jedis jedis = JedisUtils.getConn();
        jedis.zrem("zset1","12");
        Double zscore = jedis.zscore("zset1", "set1");
        System.out.println(zscore);
        jedis.close();
    }
}
