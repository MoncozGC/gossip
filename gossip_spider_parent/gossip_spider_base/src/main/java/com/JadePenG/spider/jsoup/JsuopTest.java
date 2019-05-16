package com.JadePenG.spider.jsoup;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by JadePenG on 2019/5/10 11:14
 */
public class JsuopTest {

    public static void main(String[] args) throws IOException {
        String indexUrl = "http://192.168.190.100/";

        //第一种方式解析
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>获取document的方式一</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        //Document document = Jsoup.parse(html);
        //String title = document.title();
        //System.out.println(html + "\t\n\t\n" + title);

        //第二种方式解析  不常用
        //Document document2 = Jsoup.parse(new File("G:\\index.html"), "UTF-8");
        //System.out.println(document2);

        //第三种方式  直接发送请求获取数据，得到dom对象  这种方式少用容易被封
        // 通常用httpclient去模拟浏览器
        Document document3 = Jsoup.connect(indexUrl).get();



        //第四种方式
//        String  html2= "<a>hello world</a>";
//        Document document4 = Jsoup.parseBodyFragment(html2);
//        System.out.println(document4.text());


        //css选择器
        Elements elements = document3.select(".nav > li:nth-child(2)");
        for (Element li : elements) {
            System.out.println(li.text());
        }
    }
}
