package com.JadePenG.cache.listener;

import com.JadePenG.cache.service.CacheService;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Peng
 * @Description   监听器消费topic：keywords  获取关键字数据，根据条件查询索引库,然后缓存数据
 */
@Component
public class keywordsConsumer implements MessageListener<String, String> {

    @Autowired
    private CacheService cacheService;
    private Gson gson = new Gson();

    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        //明白清楚数据的结构[{'topKeywords':'娱乐','score':40000},{'topKeywords':'xxx','score':xxx}]
        String dataJsonString = data.value();

        List<Map> mapList = JSON.parseArray(dataJsonString, Map.class);

        for (Map<String, Object> map : mapList) {
            String topKeywords = (String) map.get("topKeywords");
            cacheService.cacheNews(topKeywords);

        }


    }
}
