package com.JadePenG.spider.fiction;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

/**
 * @author Peng
 * @date 2019/5/14 15:00
 */
public class FictionFull {
    public static void main(String[] args) throws IOException {
        //1. 确定url路径
        //String indexUrl = "https://book.qidian.com/info/1012912018#Catalog";
        String indexUrl = "https://book.qidian.com/info/35583#Catalog";

        //2. 发送请求, 获取数据
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(indexUrl);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");

        CloseableHttpResponse response = httpClient.execute(httpGet);
        int code = response.getStatusLine().getStatusCode();
        //设置状态码
        int status = 200;
        //响应成功进入循环.
        if (code == status) {
            FileWriter fileWriter = new FileWriter(new File("H:\\地球非人类联盟.txt"));

            String html = EntityUtils.toString(response.getEntity());
            Document document = Jsoup.parse(html);
            Elements div = document.select("#j-catalogWrap > div.volume-wrap > div:nth-child(2)");
            for (Element li : div) {
                Elements lis = div.select("ul li a");
                for (Element element : lis) {
                    String href = element.attr("href");
                    String url = "http:" + href;
                    //System.out.println(url);
                    Document main_Body = Jsoup.connect(url).get();
                    String title = main_Body.select(".j_chapterName").text();
                    System.out.println(title);
                    Elements text = main_Body.select("[class='read-content j_readContent'] p");
                    fileWriter.write(title+"\r\n");
                    for (Element element1 : text) {
                        fileWriter.write(element1.text() + "\r\n");
                        System.out.println(element1.text());
                    }
                }
            }
        }
        httpClient.close();
    }
}
