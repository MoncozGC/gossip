package com.JadePenG.kafka.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author Peng
 * @Description 让工厂运行起来, 不停止即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-consumer.xml")
public class TestConsumer {

    @Test
    public void run(){
        try {
            //等待键盘输入, 输入完才结束
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
