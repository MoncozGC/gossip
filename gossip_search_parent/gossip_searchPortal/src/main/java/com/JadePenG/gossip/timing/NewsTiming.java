package com.JadePenG.gossip.timing;

import com.JadePenG.gossip.service.IndexWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Peng
 * @date 2019/5/19 21:23
 * @Description   让service的方法定时的去执行
 */
//组件
@Component
public class NewsTiming {

    @Autowired
    private IndexWriterService indexWriterService;

    //月中的某一天，和星期中的某一天不能全为*  ?代表缺省值 不能全都为？
    //30s执行一次
    @Scheduled(cron="0/30 * * ? * *")
    public void save(){
        System.out.println(new Date().toLocaleString());
        try {
            indexWriterService.saveBean();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
