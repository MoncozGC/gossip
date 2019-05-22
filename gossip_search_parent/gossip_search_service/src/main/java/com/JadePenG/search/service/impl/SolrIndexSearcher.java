package com.JadePenG.search.service.impl;

import com.JadePenG.gossip.pojo.News;
import com.JadePenG.gossip.pojo.ResultBean;
import com.JadePenG.search.service.IndexSearcher;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Peng
 * @Description 查询 封装数据
 */
@Service
public class SolrIndexSearcher implements IndexSearcher {

    @Autowired
    private CloudSolrServer cloudSolrServer;

    @Override
    public ResultBean findByKeywords(ResultBean resultBean) throws SolrServerException, ParseException {

        List<News> newsList = new ArrayList<>();

        //1. 封装条件  基础查询  参与打分
        //SolrQuery solrQuery = new SolrQuery("text:" + keywords);
        SolrQuery solrQuery = new SolrQuery("text:" + resultBean.getKeywords());

        //2. 过滤查询 不参与打分 在基础查询的基础上进行过滤  也就是如果基础查询查了1000条数据 过虑在这1000条中去查 而简单查询可能是在100000条数据中找到的1000条数据
        //判断source中是否有数据, 不为空才进行过滤查询  isEmpty是为空的意思
        if (!StringUtils.isEmpty(resultBean.getSource())) {
            solrQuery.addFilterQuery("source :" + resultBean.getSource());
        }
        //editor过滤
        if (!StringUtils.isEmpty(resultBean.getEditor())) {
            solrQuery.addFilterQuery("editor:" + resultBean.getEditor());
        }


        //3. 时间解析
        String startDate = resultBean.getStartDate();
        String endDate = resultBean.getEndDate();
        //判断是否进行了时间查询的过滤查询, 如果有则进入
        if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
            //当前日期格式是MM/dd/yyyy HH:mm:ss 要转化为solr中的日志格式
            SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            //字符串转换为日期类型
            Date dateStart = format1.parse(startDate);
            Date dateEnd = format1.parse(endDate);
            startDate = format2.format(dateStart);
            endDate = format2.format(dateEnd);
            //范围查询  从开始时间  TO  到 结束时间
            solrQuery.addFilterQuery("time:[" + startDate + " TO " + endDate + "]");

        }
        //排序 根据时间进行降序  排序之后，打分就失效了  nan
        solrQuery.setSort(new SolrQuery.SortClause("time", SolrQuery.ORDER.desc));

        //4. 分页查询
        Integer page = resultBean.getPageBean().getPage();
        Integer pageSize = resultBean.getPageBean().getPageSize();
        //没有设置当前页码
        if (page == null) {
            page = 1;
        }
        //没有设置显示条数
        if (pageSize == null) {
            pageSize = 15;
        }
        //设置
        solrQuery.setStart((page - 1) * pageSize);
        solrQuery.setRows(pageSize);

        //5. 开启高亮
        solrQuery.setHighlight(true);
        //添加高亮字段
        solrQuery.addHighlightField("title");
        solrQuery.addHighlightField("content");
        //高亮字段设置为红色
        solrQuery.setHighlightSimplePre("<font style='color:red'>");
        solrQuery.setHighlightSimplePost("</font>");

        //执行查询
        QueryResponse response = cloudSolrServer.query(solrQuery);

        //获取高亮字段
        Map<String, Map<String, List<String>>> map = response.getHighlighting();

        SolrDocumentList documentList = response.getResults();

        //不是高亮的部分   getBeans无法将数据封装过去, 因为时间类型是字符串,
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //6. 遍历数据, 保存到List<News>中
        for (SolrDocument solrDocument : documentList) {
            String id = (String) solrDocument.get("id");
            String title = (String) solrDocument.get("title");
            String content = (String) solrDocument.get("content");

            //根据id去高亮mao中获取内容
            //有高亮的东西
            if (map != null) {
                Map<String, List<String>> fieldMap = map.get(id);
                //获取到高亮的内容
                if (fieldMap != null) {
                    List<String> titleList = fieldMap.get("title");
                    //title是否有内容
                    if (titleList != null && titleList.size() > 0) {
                        //将高亮的内容替换原来的
                        title = titleList.get(0);
                    }
                    //content是否有内容
                    List<String> contentList = fieldMap.get("content");
                    if (contentList != null && contentList.size() > 0) {
                        //将高亮的内容替换原来的
                        content = contentList.get(0);
                    }
                }

            }
            //如果content内容过长，可以切割之后再发送给前端，前端不需要展示特别多的内容
            if (content.length() > 200) {
                /**
                 *设置高亮后, 因为内容长度的关系可能会切割有问题
                 * <font style='color:red'>幂...   会切成这样, 所以编辑和来源两个参数也会高亮
                 * 在... 后面拼接一个</font>  直接关闭元素
                 */
                content = content.substring(0, 197) + "...</font>";
            }

            //time在solr中是一个Date类型, 但是在News中是一个字符串类型
            Date time = (Date) solrDocument.get("time");
            //将日期格式化为日期/时间字符串。
            //solr中时间是按0时区去存储的，我们是东8区 时间会多八个小时，要减去   时间是毫秒值
            time.setTime(time.getTime() - 1000 * 60 * 60 * 8);
            String newTime = format.format(time);
            String source = (String) solrDocument.get("source");
            String editor = (String) solrDocument.get("editor");
            String docurl = (String) solrDocument.get("docurl");

            //将数据封装到对象中
            News news = new News();
            news.setId(id);
            news.setTitle(title);
            news.setContent(content);
            news.setDocurl(docurl);
            news.setTime(newTime);
            news.setSource(source);
            news.setEditor(editor);

            newsList.add(news);
        }
        //PageBean对象可能在条件中不存在   在PageBean为空ResultBean使用它可能会报空指针异常

        //ResultBean中有PageBean, PageBean中有NewsList, 然后将newsList设置到PageBean中
        //那么PageBean有了newsList数据, ResultBean也有了newsList数据
        resultBean.getPageBean().setNewsList(newsList);

        //return newsList;

        //总记录数
        long pageCount = documentList.getNumFound();
        resultBean.getPageBean().setPageCount((int) pageCount);
        //总页数   ceil向上取整  floor向上取整
        double pageNum = Math.ceil(pageCount / pageSize);
        resultBean.getPageBean().setPageNum((int) pageNum);

        return resultBean;
    }
}
