package com.JadePenG.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author Peng
 * @Description  消费者
 */
public class OrderConsumerTest {
    public static void main(String[] args) {
        Properties props = new Properties();
        //设置连接参数
        props.put("bootstrap.servers", "node01:9092");
        //组名称
        props.put("group.id", "test");
        // 是否自动向kafka集群中提交偏移量
        props.put("enable.auto.commit", "true");
        // 多长时间提交一次  可以保证消费者不丢失数据, 但是可能会有重复数据
        props.put("auto.commit.interval.ms", "1000");
        // 序列化
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        //1. 创建kafka消费者对象
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(props);
        //2. 消费数据
        //2.1 订阅topic集合     Arrays.asList: 创建一个ArrayList然后add一个元素
        kafkaConsumer.subscribe(Arrays.asList("order"));
        while (true){
            //拉取数据间隔
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : consumerRecords){
                System.out.println(record.value());
            }
        }
    }
}
