package com.JadePenG.spider.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by JadePenG on 2019/5/10 11:14
 */
public class HttpClientGet {
    public static void main(String[] args) throws IOException {
        //1. 确定访问路径
        String indexUrl = "https://www.imooc.com";
        //2. 发送请求, 响应数据
//        HttpClientBuilder builder = HttpClientBuilder.create();
//        CloseableHttpClient closeableHttpClient = builder.build();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置请求方式
        HttpGet httpGet = new HttpGet("GET");
        //设置头信息，设置的越详细，越不容易被封
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
        //发送请求，获取响应
        CloseableHttpResponse response = httpClient.execute(httpGet);

        //获取响应码
        int code = response.getStatusLine().getStatusCode();
        if (code == 200) {
            Header[] headers = response.getHeaders("Content-Type");
            for (Header header : headers) {
                System.out.println(header.getValue());
            }
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity);
            System.out.println(html);
        }

        //3. 关闭连接客户端
        httpClient.close();
    }
}
