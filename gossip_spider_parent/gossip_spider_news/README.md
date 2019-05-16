#### 目录结构


````
主程序
|--news163
|--tencent
dao: 数据库操作代码
pojo: Bean类
remould: 是在之前的基础上添加分布式部署功能
system:  redis数据库名称设置
util: 工具包
````
### 分布式爬虫

remould包:
````
1. 首先执行News163Master类中的主方法, 运行该方法会在redis中生成一个JadePenG:gossip:list:docurl的key值里面存放的是对应的url内容
        注意这里还未生成新闻内容,新闻内容在该url中,所有我们要进行解析
2. 运行News163Slave类中的主方法, 会生成一个JadePenG:gossip:list:newsjson的key值里面
        存放的是对应的新闻内容以及我们需要保存的url信息
3. 运行PublicDaoNode类的主方法, 会生成一个JadePenG:gossip:set:docurl的key值里面存放的就是我们需要保存的url,
        这些url以及我们想要获取的内容(比如:标题, 时间, 来源, 编辑)经过dao成的数据库操作保存到了mysql中

1. NewsTencentMaster类是另一个方法, 执行完该主方法就可以直接执行PublicDaoNode类中的主方法
2. 它也会生成url到redis中, 具体的信息保存在mysql中

````