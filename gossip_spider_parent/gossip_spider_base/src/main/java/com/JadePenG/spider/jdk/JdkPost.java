package com.JadePenG.spider.jdk;

import org.apache.http.HttpConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by JadePenG on 2019/5/10 17:04
 */
public class JdkPost {
    public static void main(String[] args) throws IOException {
        //1. 设置路径
        String indexUrl = "https://www.imooc.com";

        //2. 发送请求, 获取数据
        //2.1 将String类型的url转成url对象
        URL url = new URL(indexUrl);
        //2.2 获取连接
        HttpURLConnection UrlConnection = (HttpURLConnection)url.openConnection();
        //使用url作为输出就需要设置为true, 默认为false
        UrlConnection.setDoOutput(true);
        //2.3 设置请求方式
        UrlConnection.setRequestMethod("POST");
        //得到一个输出流, 向输出流中写入数据
        OutputStream outputStream = UrlConnection.getOutputStream();
        //添加参数
        outputStream.write("username='zhangsan'&age=18".getBytes());

        //获取数据
        InputStream in = UrlConnection.getInputStream();
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len=in.read(bytes)) != -1){
            System.out.println(new String(bytes,0,len));
        }
        in.close();
        outputStream.close();
    }

}
