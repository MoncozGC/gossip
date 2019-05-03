package com.JadePenG.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by JadePenG on 2019/5/9 13:41
 *
 *  redis : Hash类型操作  未使用工具类
 *      Hash类型结构 :  key-value(map={name='zhangsan',age=19})
 *
 */
public class HashTest {


    //赋值
    @Test
    public void hset(){
        //1. 创建jedis对象
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        //2. 操作jedis
        jedis.hset("person","Hash1", "23_1");
        jedis.hset("person","Hash2", "23_2");
        jedis.hset("person","Hash3", "23_3");
        jedis.hset("person","Hash4", "23_4");
        //3. 关闭资源
        jedis.close();
    }

    //获取值 取值
    @Test
    public void hget(){
        //1. 创建jedis对象
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        //2. 操作
            //2.1 获取指定key的field
            String hget = jedis.hget("person", "Hash1");
            System.out.println("获取指定key : "+hget);

            //2.2 获取指定key的多个field值  [ 使用少 ]
            List<String> hmget = jedis.hmget("person", "Hash1", "Hash2", "Hash3");
            System.out.println("获取指定key的多个field值 : "+hmget);

            //2.3 获取指定key中的所有的field与value的值:  hgetall key
            Map<String, String> map = jedis.hgetAll("person");
            System.out.println("获取指定key中的所有的field与value的值 : "+map);

            //2.4 获取指定key中map的所有的field
            Set<String> set = jedis.hkeys("person");
            System.out.println("获取指定key中map的所有的field : "+set);

            //2.5 获取指定key中map的所有的value
            List<String> list = jedis.hvals("person");
            System.out.println("取指定key中map的所有的value : "+list);

        //3. 关闭资源
            jedis.close();
    }

    //删除
    @Test
    public void hdel(){
        //1. 创建Jedis对象
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        //2. 操作
        //2.1 删除一个或多个字段, 返回值是被删除的字段个数
        Long along = jedis.hdel("person", "Hash2", "Hash3");
        System.out.println(along);
        //2.2 删除整个内容
        Long person = jedis.hdel("person");
        System.out.println(person);
        //3. 关闭资源
        jedis.close();
    }

    //增加数字
    @Test
    public void hincrby(){
        //1. 创建Jedis对象
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        //2. 操作
        //2.1 为某个key的某个属性增加值
        Long aLong = jedis.hincrBy("person", "Hash2", 10);
        System.out.println(aLong);
        //3. 关闭资源
        jedis.close();
    }

    //判断某个key中的filed是否存在
    @Test
    public void hexiSts(){
        //1. 创建Jedis对象
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        //2. 操作
        //2.1 断某个key中的filed是否存在, 返回 0表示没有,  返回1 表示有
        Boolean hexists = jedis.hexists("person", "Hash2");
        System.out.println(hexists);
        //3. 关闭资源
        jedis.close();
    }


    //获取key中所包含的field的数量
    @Test
    public void hlen (){
        //1. 创建Jedis对象
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        //2. 操作
        //2.1 获取key中所包含的field的数量
        Long person = jedis.hlen("person");
        System.out.println(person);
        //3. 关闭资源
        jedis.close();
    }
}
