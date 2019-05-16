package com.JadePenG.dao;

import com.JadePenG.pojo.News;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.beans.PropertyVetoException;

/**
 * Created by JadePenG on 2019/5/12 20:52
 */
public class NewsDao extends JdbcTemplate{

    //获取连接池
    private static ComboPooledDataSource dataSource;

    //设置数据库
    static {
        try {
            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://192.168.190.100:3306/gossip?characterEncoding=utf-8");
            dataSource.setUser("root");
            dataSource.setPassword("0712");
        }catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public NewsDao(){
        super.setDataSource(dataSource);
    }

    public void saveNews(News news){
        String sql = "insert into news (id,title, source, time, editor, content, docurl) values(?,?,?,?,?,?,?)";
        update(sql,news.getId(),news.getTitle(),news.getSource(),news.getTime(),news.getEditor(),news.getContent(),news.getDocurl());
    }

}
