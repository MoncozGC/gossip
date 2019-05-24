package com.JadePenG.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Peng
 * @Description  注入模板对象发送数据到kafka
 */
@Component
public class SpiderTestProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    //生产者发送数据
    public void sendMessage(String data){
        kafkaTemplate.send("spider-test", data);
    }
}
