package com.JadePenG.gossip.service;

/**
 * @author Peng
 * @date 2019/5/19 21:08
 * @Description
 */
public interface IndexWriterService {

    //调用数据库，将数据发送给索引服务
    public void saveBean() throws Exception;

}
