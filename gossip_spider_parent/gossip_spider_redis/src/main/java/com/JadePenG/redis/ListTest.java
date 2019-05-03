package com.JadePenG.redis;

import org.junit.Test;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by JadePenG on 2019/5/9 13:41
 */
public class ListTest {

    //  List  value类型为list key-value[a,b,c]
    @Test
    public void add(){
        //1.创建连接客户端jedis对象
        Jedis jedis = new Jedis("192.168.190.100",6379);
        //执行操作前删清空集合中的数据,否则会一直往里面添加  list可重复
        jedis.del("list1");
        jedis.del("list2");
        //2.操作redis
        //左压栈  在添加的时候会比较麻烦  顺序 : 范伟 范丞丞 范冰冰
        jedis.lpush("listl","范冰冰","范丞丞","范伟");
        //jedis.lrem("listl",2,"范伟");
        jedis.linsert("listl", BinaryClient.LIST_POSITION.BEFORE,"范冰冰","黑牛");
        jedis.rpoplpush("listl","listl");
        //获取所有数据
        List<String> listl = jedis.lrange("listl", 0, -1);
        System.out.println(listl);
        //右出   右边第一个first
        String namer = jedis.rpop("listl");
        System.out.println(namer);
        //右压栈                   顺序 : 蔡徐坤 朱正廷 小鬼
        jedis.rpush("listr","蔡徐坤","朱正廷","小鬼");
        //左出, 左边第一个first
        String namel = jedis.lpop("listr");
        System.out.println(namel);
        //3.关闭资源
        jedis.close();


    }
}
