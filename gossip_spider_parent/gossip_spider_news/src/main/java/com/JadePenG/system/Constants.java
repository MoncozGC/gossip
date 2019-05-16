package com.JadePenG.system;

/**
 * @ClassName Constants 系统常用的参数
 * @Description
 */
public class Constants {

    //用于163新闻去重的set的key
    public static final String NEWS_163_URL="JadePenG:gossip:news163:docurl";

    //用于腾讯新闻去重的set的key
    public static final String NEWS_TENCENT_URL="JadePenG:gossip:newstencent:docurl";

    //用于存放爬取到的url列表list的key
    public static final String NEWS_LIST_URL="JadePenG:gossip:list:docurl";

    //用于存放解析到的news的json字符串list的key
    public static final String NEWS_LIST_NEWSJSON="JadePenG:gossip:list:newsjson";

    //用于163和腾讯新闻去重的set的key
    public static final String NEWS_SET_URL="JadePenG:gossip:set:docurl";
}
