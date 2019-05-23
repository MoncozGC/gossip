package com.JadePenG.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Map;
import java.util.Properties;

/**
 * @author Peng
 * @Description 模拟生产者
 */
public class OrderProducerTest {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 1. 连接集群, 通过配置文件的方式
         * 2. 发送数据--topic:order, value
        * */

        Properties props = new Properties();
        props.put("bootstrap.servers", "node01:9092");
        //acks确认码
        props.put("acks", "all");
        props.put("retries", "0");
        props.put("batch.size", 16384);
        // 1s
        props.put("linger.ms", 1);
        // 缓冲区, 减少确认信息的次数, 提高效率, 减少网络带宽
        props.put("buffer.memory", 33554432);
        // 我们的数据发布到kafka时需要先序列化, 变成二进制的流, kafka才认识
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        //1.创建kafka生产者对象 需要配置参数   <key（数据的标识，默认可以是给null）,value（发送的数据）>
        KafkaProducer<String, String > kafkaProducer = new KafkaProducer<String, String>(props);
        //2.生产者生产数据，发送消息       ProducerRecord是有多个参数的（1：topic 2:partition ,3 key ,4value）
//        ProducerRecord<String, String> record = new ProducerRecord<String, String>("order", "订单2");
//        //发送
//        kafkaProducer.send(record);
//        //刷新才能同步
//        kafkaProducer.flush();
//        kafkaProducer.close();

        //
        for (int i=0;i<1000;i++){
            kafkaProducer.send(new ProducerRecord<String, String>("order", "订单信息"+i));
            //睡眠1s
            //Thread.sleep(10);
            kafkaProducer.flush();

        }
    }
}
