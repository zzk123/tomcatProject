## 应用模块介绍
### Bootstrap （启动类）

### HttpConnector（tomcat默认连接器）
 - 实现 org.apache.catalina.Connector接口
 - 负责创建实现了 org.apache.catalina.Request接口的request对象以及 org.apache.catalina.Response接口的response对象
 - 调用 org.apache.catalina.Container接口的invoke()方法
 - 创建服务嵌套字，维护HttpProcessor实例（有一个HttpProcessor对象池，每个实例都运行在自己的线程中，同时处理多个HTTP请求）
### HttpProcessor
 - 负责解析填充HTTP请求行和请求头
### SimpleContainer
 - 处理请求servlet资源，为Web客户端填充response对象
 - 载入相应的servlet类，调用其service()方法，管理session对象，记录错误消息等操作