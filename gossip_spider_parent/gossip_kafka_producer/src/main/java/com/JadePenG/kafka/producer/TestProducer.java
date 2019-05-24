package com.JadePenG.kafka.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Peng
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-producer.xml")
public class TestProducer {

    @Autowired
    private SpiderTestProducer spiderTestProducer;

    @Test
    public void send() {
        for (int i = 1; i < 100; i++) {

            try {
                spiderTestProducer.sendMessage("发送给kafka的第" + i + "条消息");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
