package com.JadePenG.spider.integral;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JadePenG on 2019/5/11 12:26
 *
 * 模拟登录慢慢网, 获取登录后的积分信息
 */
public class integralLogin {
    public static void main(String[] args) throws IOException {
        //1. 确定连接
        String indexUrl = "http://home.manmanbuy.com/login.aspx";

        //2. 发送请求, 获取数据
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //定义请求方式
        HttpPost httpPost = new HttpPost(indexUrl);
        //设置参数
        List<NameValuePair> list = new ArrayList<>();
        //提供登录需要的参数 需要什么就得给什么  登录后F12 login.aspx -> Network 下的 FormData
        list.add(new BasicNameValuePair("txtUser", "itcast"));
        list.add(new BasicNameValuePair("txtPass", "www.itcast.cn"));
        list.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwULLTIwNjQ3Mzk2NDFkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQlhdXRvTG9naW4voj01ABewCkGpFHsMsZvOn9mEZg=="));
        list.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEWBQLW+t7HAwLB2tiHDgLKw6LdBQKWuuO2AgKC3IeGDJ4BlQgowBQGYQvtxzS54yrOdnbC"));
        list.add(new BasicNameValuePair("autoLogin", "on"));
        list.add(new BasicNameValuePair("btnLogin", "请求登录"));

        HttpEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
        httpPost.setEntity(entity);
        //设置请求头
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
        //设置防盗链
        httpPost.setHeader("Referer", "http://home.manmanbuy.com/login.aspx");

        //发送请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //获取状态码
        int code = response.getStatusLine().getStatusCode();
        System.out.println("状态码 : " + code);
        //设置好了登录信息, 直接登录  网站的登录状态是重定向所以code需要等于302
        if (code == 302) {
            //获取重定向后的地址
            Header[] headers = response.getHeaders("Location");
            //服务器 : Set-Cookie  浏览器 : Cookie
            Header[] set_Cookie = response.getHeaders("Set-Cookie");
            //得到Cookie, 因为Cookie不确定有几个值, 所以进行拼接
            String cookie = "";
            for (Header header : set_Cookie) {
                //得到Cookie, 之后设置到浏览器中去
                cookie += header.getValue();
            }
            //登录成功后跳转的页面, 因为时模拟登录 所以需要Cookie
            String location = "http://home.manmanbuy.com/" + headers[0].getValue();
            httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(location);

            //设置请求头
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
            //设置防盗链
            httpPost.setHeader("Referer", "http://home.manmanbuy.com/login.aspx");
            //在重定向时,将解密的cookie设置进去
            httpGet.setHeader("Cookie", cookie);
            CloseableHttpResponse response1 = httpClient.execute(httpGet);
            int code1 = response1.getStatusLine().getStatusCode();
            System.out.println("code1 : " + code1);
            if (code1 == 200) {
                String html = EntityUtils.toString(response1.getEntity());
                Document document = Jsoup.parse(html);
                Elements elements = document.select("#aspnetForm > div.udivright > div:nth-child(2) > table > tbody > tr > td:nth-child(1) > table:nth-child(2) > tbody > tr > td:nth-child(2) > div:nth-child(1) > font");
                String text = elements.get(0).text();
                System.out.println(text);
            }
        }
        //3. 关闭资源
        httpClient.close();
    }
}
