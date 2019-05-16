package com.JadePenG.remould;

import com.JadePenG.system.Constants;
import com.JadePenG.util.HttpClientUtils;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by JadePenG on 2019/5/13 11:39
 *
 *  1.确定url
    2.发送请求获取json数据  [ 保存news,新闻内容 ]
    3.解析json，获取到url列表（判断去重），将数据存放到redis的list中
 */
public class News163Master {

    private static Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        //1. 确定url
        String index163Url = "https://ent.163.com/special/000380VU/newsdata_index.js?callback=data_callback";

        //2. 发送请求, 获取数据   数据的格式 --> json
        //解析数据
        //parseNewsListJson(index163Url);

        //3. 分页
        page(index163Url);
    }

    private static String pageParse(String indexUrl) throws IOException {
        //发送请求获取列表数据
        String jsonString = HttpClientUtils.doGet(indexUrl);

        return jsonString;

    }

    /**
     * 分页
     *
     * @param index163Url
     */
    private static void page(String index163Url) throws IOException {

        Integer page = 2;
        while (true) {
            String index = "";
            String json = pageParse(index163Url);
            if (json == null || "".equals(json)) {
                break;
            }
            //如果不跳出循环, 解析数据
            parseNewsListJson(json);

            //如果页面小于10, 那么应该是0x(根据具体的地址而定)  大于10就让它等于page
            if (page < 10) {
                index = "_0" + page;
            } else {
                index = "_" + page;
            }

            //拼接无数次  如果有数据，我就拼接，如果没数据，跳出循环
            index163Url = "https://ent.163.com/special/000380VU/newsdata_index" + index + ".js?callback=data_callback";
            System.out.println(index163Url);
            page++;
        }
    }


    /**
     * @param jsonString
     * @Description 解析数据Json
     */
    private static void parseNewsListJson(String jsonString) throws IOException {

        //String jsonString = HttpClientUtils.doGet(index163Url);

        //源码中不是完整的Json格式数据, 它前面还有 data_callback([ ])  data_callback( 和 ) 不需要, []里面是数组形式的Json
        //进行截取  ( 左括号以前前面的数据不需要  ) 右括号不需要
        int first = jsonString.indexOf("(");//左半括号的下标
        int last = jsonString.lastIndexOf(")");//右半括号的下标
        String newsListJson = jsonString.substring(first + 1, last);
        //得到每一条新闻的url
        List<Map<String, String>> list = gson.fromJson(newsListJson, List.class);
        //循环新闻对象列表，获取每条新闻url
        for (Map<String, String> map : list) {
            String docurl = map.get("docurl");
            //过滤图片新闻和视频新闻
            if (!docurl.contains("ent.163.com") || docurl.contains("photoview")) {
                //如果是,就跳过不爬取
                continue;
            }
            System.out.println(docurl);

            //判断url是否爬取过，如果爬取过，就不在爬取
            Jedis jedis = new Jedis("192.168.190.100", 6379);
            Boolean sismember = jedis.sismember(Constants.NEWS_SET_URL, docurl);
            jedis.close();
            if(sismember){
                //跳过
                continue;
            }
            /*//获取新闻列表, 遍历获取每天新闻详情内容
            parseNewsJson(docurl);*/

            //将url保存到redis的一个list（url）集合中
            jedis = new Jedis("192.168.190.100", 6379);
            jedis.lpush(Constants.NEWS_LIST_URL, docurl);
            jedis.close();
        }
    }
}
