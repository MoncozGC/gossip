package com.JadePenG.producer;

import com.JadePenG.pojo.News;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author Peng
 * @Description     新闻数据的消息生产者
 */
public class NewsProducer {

    private static KafkaProducer<String,String> kafkaProducer;

    //当类创建对象时, 生产者就应该被初始化, 当调用方法的时候就可以用生产者发送数据
    static {
        //配置生产者参数
        Properties props = new Properties();
        props.put("bootstrap.servers", "node01:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        //创建生产者对象
        kafkaProducer = new KafkaProducer<String, String>(props);
    }


    public void sendNewsToKafka(String news){
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("news", news);
        kafkaProducer.send(record);
        kafkaProducer.flush();

    }
}
