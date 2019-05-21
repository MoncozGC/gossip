package com.JadePenG.gossip.controller;

import com.JadePenG.gossip.pojo.PageBean;
import com.JadePenG.gossip.pojo.ResultBean;
import com.JadePenG.gossip.service.IndexSearcherService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Peng
 * @Description
 * @RestController 可以代替 @Controller和@ResponseBody注解
 */
@RestController
public class IndexSearcherController {

    @Autowired
    private IndexSearcherService indexSearcherService;

    @RequestMapping("/s")
    public ResultBean findByKeyWords(ResultBean resultBean) {
        //会把ResultBean转化成一个JSON返回给前端

        if (resultBean == null) {
            return null;
        }

        //判断是否非法
        if (StringUtils.isEmpty(resultBean.getKeywords())) {
            //传递条件为空 非法条件
            return null;
        }


        if(resultBean.getPageBean()==null){
            //如果前端传递的参数包含分页的内容，会封装一个pageBean对象，但是现在是没有分页参数的，就没有pageBean对象
            resultBean.setPageBean(new PageBean());
        }

        try {//在web层无法继续抛异常只能抓异常
            return indexSearcherService.findByKeywords(resultBean);
        } catch (Exception e) {
            e.printStackTrace();
            //如果出现异常,那么就是没有数据返回
            return null;
        }

    }

}
