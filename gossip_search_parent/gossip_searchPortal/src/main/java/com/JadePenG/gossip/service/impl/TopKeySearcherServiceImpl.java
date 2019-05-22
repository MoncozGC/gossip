package com.JadePenG.gossip.service.impl;

import com.JadePenG.gossip.service.TopKeySearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * @author Peng
 * @Description
 */
@Service
public class TopKeySearcherServiceImpl implements TopKeySearcherService {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public List<Map<String, Object>> topkeyFindByNum(Integer num) {

        List<Map<String, Object>> topKeyList = new ArrayList<>();

        Jedis jedis = jedisPool.getResource();
        Set<Tuple> tuples = jedis.zrevrangeWithScores("bigData:gossip:topKey", 0, num - 1);
        for (Tuple tuple : tuples) {
            Map<String, Object> map = new HashMap<>();
            //得到热词
            String topKey = tuple.getElement();
            double score = tuple.getScore();
            map.put("topKey", topKey);
            map.put("score", score);
            topKeyList.add(map);
        }
        return topKeyList;
    }
}
