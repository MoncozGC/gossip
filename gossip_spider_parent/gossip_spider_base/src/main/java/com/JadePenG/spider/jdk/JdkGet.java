package com.JadePenG.spider.jdk;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JadePenG on 2019/5/10 14:27
 *   原生JDK, Get方法请求
 */
public class JdkGet {
    public static void main(String[] args) throws IOException {
        //1. 确定访问路径
        String indexUrl = "https://www.imooc.com";

        //2. 发送请求, 获取数据
        //2.1 将String类型的url转换为对象
        URL url = new URL(indexUrl);
        //2.2 获取连接方式
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        //2.3 设置请求方式
        urlConnection.setRequestMethod("GET");
        //2.4 获取数据
        InputStream in = urlConnection.getInputStream();
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = in.read(bytes)) != -1 ){
            System.out.println(new String(bytes, 0, len));
        }

        //3. 关闭资源
        in.close();
    }
}
