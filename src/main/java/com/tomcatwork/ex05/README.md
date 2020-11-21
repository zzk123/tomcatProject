# servlet 容器
## 类型
在tomcat中有4种类型的容器，分别是Engine、Host、Context和Wrapper对应着不同概念的层次
- Engine：表示整个Catalina servlet引擎
- Host：表示包含一个或者多个Context容器的虚拟主机
- Context：表示一个Web应用程序。一个Context可以有多个Wrapper
- Wrapper：表示一个独立的servlet