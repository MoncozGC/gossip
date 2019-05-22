### 说明

##### controller: 控制层

​	IndexSearcherController.java: 展示
​	TopKeySearcherController.java: 热点新闻

##### mapper: dao层

​	NewsMapper.java: 与mapper配置文件进行新闻数据查询

##### service: service层

​	IndexSearcherService.java: 门户系统搜索接口,调用索引服务(service工程), 查询数据
​	IndexWriterService.java: 调用数据库，将数据发送给索引服务
​	TopKeySearcherService.java: 热点新闻

##### timing: 定时器

​	NewsTiming.java: 让service方法定时执行

