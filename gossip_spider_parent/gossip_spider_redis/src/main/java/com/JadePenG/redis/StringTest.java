package com.JadePenG.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by JadePenG on 2019/5/9 13:40
 *   String类型操作 未使用工具类
 */
public class StringTest {

    //添加, 赋值
    @Test
    public void set(){
        //1. 创建jedis对象
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        //2. jedis操作
            //2.1 设置值  key值唯一  value可以一样
            jedis.set("name", "zhangsan");
            jedis.set("age", "22");
            jedis.set("girl", "zhangsan");
            //2.2 增加值
            Long age = jedis.incr("age");
            System.out.println(age); //23

            //3. 关闭资源
            jedis.close();
    }

    //获取, 取值
    @Test
    public void get(){
        //1. 创建jedis对象
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        //2. jedis操作
            //2.1 获取单个值
            String name = jedis.get("name");
            String age = jedis.get("age");
            String set = jedis.get("set");
            String girl = jedis.get("girl");
            System.out.println(name+","+age+","+set+","+girl);
            //3. 关闭资源
            jedis.close();
    }

    //删除
    @Test
    public void del(){
        //1. 创建jedis对象
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        //2. 操作jedis
            //2.1 删除值
            jedis.del("name");
            //3. 关闭流
            jedis.close();
    }

    //拼接字符串
    @Test
    public void append(){
        //1. 创建jedis对象
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        //2. 操作jedis
            //2.1 拼接value值 append key value
            // 有key值则拼接, 没key值就创建
            jedis.append("set", "男");
            //3. 关闭流
            jedis.close();
    }

    //设置有效时长, 为已有的key设置
    @Test
    public void seconds() throws InterruptedException {
        //1. 创建jedis对象
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        //2 操作jedis
        //2.1 为已有的key设置有效时间
        Long expire = jedis.expire("name", 5);
            //返回1 表示设置成功, 返回0 表示该key不存在
        System.out.println(expire);
        while(jedis.exists("name")) {
            //返回-1 表示永久有效, -2 key不存在
            System.out.println(jedis.ttl("name"));
            Thread.sleep(1000);
        }
        //3. 关闭资源
        jedis.close();
    }


    //设置有效时长, 为新建的key设置
    @Test
    public void seconds_02() throws InterruptedException {
        //1. 创建jedis对象
        Jedis jedis = new Jedis("192.168.190.100", 6379);
        //2 操作jedis
        //2.1 为新建的key设置有效时间
            jedis.setex("date", 10, "2018.9.15");

            while(jedis.exists("date")) {
                System.out.println(jedis.ttl("date"));
                Thread.sleep(1000);
            }
        //3. 关闭资源
        jedis.close();
    }

}
