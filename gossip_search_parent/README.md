## gossip_search_parent

### 运行条件
	```
	1. 三台虚拟机安装了zookeeper, 并且zookeeper集群开启
	2. solrCloud集群开启
	3. redis开启
	4. mysql开启
	5. dubbox-admin(可选)
	```
	使用tomcat启动 gossip_search_service工程(搜索服务) --> gossip_searchPortal(门户系统)

### 具体说明
	```
	gossip_pojo: 封装数据的pojo类
	gossip_search_interface: 用于编写接口
	gossip_search_sesrvice: 搜索服务的实现, 用于从solr索引库添加数据查询数据
	gossip_searchPortal: 门户工程
	```