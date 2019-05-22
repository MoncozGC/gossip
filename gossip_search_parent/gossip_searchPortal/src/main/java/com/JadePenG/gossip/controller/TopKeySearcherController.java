package com.JadePenG.gossip.controller;

import com.JadePenG.gossip.service.TopKeySearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Peng
 * @Description
 */
@RestController
public class TopKeySearcherController {

    @Autowired
    private TopKeySearcherService topKeySearcherService;

    @RequestMapping("/top")
    public List<Map<String, Object>> topKeyByNum(Integer num) {
        if (num == null || num == 0) {
            num = 10;
        }
        return topKeySearcherService.topkeyFindByNum(num);
    }

}
