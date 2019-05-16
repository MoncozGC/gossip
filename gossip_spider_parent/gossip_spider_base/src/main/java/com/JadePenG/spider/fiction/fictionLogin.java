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

import java.io.IOException;

/**
 * Created by JadePenG on 2019/5/11 12:25
 *
 * 获取起点网免费小说,
 * 从网站首页开始, 之后进入小说目录 --> 进入第一章正文
 */
public class fictionLogin {
    public static void main(String[] args) throws IOException {
        //1. 确定url路径
        String indexUrl = "https://www.qidian.com/";

        //2. 发送请求, 获取数据
        //2.1 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.2 定义请求方式
        HttpGet httpGet = new HttpGet(indexUrl);
        //设置请求头
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");

        //请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //获取状态码
        int code = response.getStatusLine().getStatusCode();
        System.out.println("code"+code);
        if(code == 200){
            //封装了响应数据
            String html = EntityUtils.toString(response.getEntity());
            //获取dom对象
            Document document = Jsoup.parse(html);
            //利用css选择器解析数据
            Elements elements = document.select("#rank-list-row > div.rank-list.mr0 > div > ul > li.unfold > div > div.book-info.fl > h4 > a");
            //获取第一个href 也就是连接
            String href = elements.get(0).attr("href");
            //拼接连接  https://book.qidian.com/info/1013562540
            String bookUrl = "https:" + href;
            System.out.println(bookUrl);
            //继续发送请求
            //可以覆盖上面的请求, 当时HttpGet HttpPost 不能覆盖之前,因为前一个HttpGet中有参数
            httpClient = HttpClients.createDefault();
            //定义请求方式
            HttpGet httpGet2 = new HttpGet(bookUrl);
            //发送请求
            CloseableHttpResponse response2 = httpClient.execute(httpGet2);
            //获取响应码
            int code2 = response2.getStatusLine().getStatusCode();
            System.out.println("code2 : "+code2);
            //当响应码=200(成功)
            if(code2 == 200){
                //获取html
                String bookHtml = EntityUtils.toString(response2.getEntity());
                //解析数据
                Document document2 = Jsoup.parse(bookHtml);
                Elements elements1 = document2.select("#readBtn");
                String firstUrl = elements1.get(0).attr("href");
                //拼接地址链接  https://read.qidian.com/chapter/mtNeHjAWIutH9vdK3C5yvw2/uDT3st-qPD7M5j8_3RRvhw2
                firstUrl = "https:" + firstUrl;
                System.out.println(firstUrl);

                //利用Jsoup发送请求获取数据(html)
                Document document3 = Jsoup.connect(firstUrl).get();
                //解析数据
                Elements elements2 = document3.select(".j_chapterName");
                String title = elements2.get(0).text();
                System.out.println(title);
                Elements elements3 = document3.select("[class=read-content j_readContent] p");
                //遍历获取小说正文.
                for (Element p : elements3) {
                    System.out.println(p.text());
                }
            }
        }
        //关闭资源
        httpClient.close();
    }
}
