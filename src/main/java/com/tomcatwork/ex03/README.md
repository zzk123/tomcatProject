启动器只有一个类（Bootstrap），后者负责启动应用程序。连接器模块中的类可以分为以下几种类型
- 连接器及其支持类（HttpConnector和HttpProcessor）
- 表示HTTP请求的类（HttpRequest）以及其支持类
- 表示HTTP响应的类（HttpResponse）以及其支持类
- 外观类（HttpRequestFacade和HttpResponseFacade）
- 常量类