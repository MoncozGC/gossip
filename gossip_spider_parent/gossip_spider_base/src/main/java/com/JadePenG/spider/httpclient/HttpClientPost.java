package com.JadePenG.spider.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JadePenG on 2019/5/11 10:35
 */
public class HttpClientPost {

    public static void main(String[] args) throws IOException {
        System.out.println("开始访问");
        //1. 确定访问路径
        String indexUrl = "https://www.qidian.com/";
        //2. 发送请求, 获取数据
        //2.1 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.2 设置请求方式:
        HttpPost httpPost = new HttpPost(indexUrl);
        //2.3 设置请求头 和 请求参数
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");

        List<NameValuePair> list = new ArrayList<>();
        NameValuePair nameValuePair1 = new BasicNameValuePair("username", "zhangsan");
        NameValuePair nameValuePair2 = new BasicNameValuePair("age", "19");
        list.add(nameValuePair1);
        list.add(nameValuePair2);
        HttpEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
        httpPost.setEntity(entity);

        //2.4 发送请求, 获取响应对象
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //2.5 获取数据: 响应码
        int code = response.getStatusLine().getStatusCode();
        System.out.println(code);
        if(code == 200){
            String html = EntityUtils.toString(response.getEntity());
            System.out.println(html);
/*            Document document3 = Jsoup.connect(indexUrl).get();
            Elements select = document3.select("dl.cf > dd:nth-child(14) > a:nth-child(1) > cite:nth-child(1) > span:nth-child(2) > i:nth-child(1)");
            for (Element li : select) {
                System.out.println(li.text());
            }*/
        }
        httpClient.close();
    }

}
