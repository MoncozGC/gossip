package com.JadePenG.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author Peng
 * @Description 监听器  监听9092端口,消费消息
 */
@Component
public class SpiderKafkaConsumer implements MessageListener<Integer, String> {
    //只要9092端口有数据, 那么onMessage就会触发
    //ConsumerRecord<Integer, String> data --> 消费到的数据
    @Override
    public void onMessage(ConsumerRecord<Integer, String> data) {
        System.out.println(data.value());
    }
}
