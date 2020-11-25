# servlet 容器
## 类型
在tomcat中有4种类型的容器，分别是Engine、Host、Context和Wrapper对应着不同概念的层次
- Engine：表示整个Catalina servlet引擎
- Host：表示包含一个或者多个Context容器的虚拟主机
- Context：表示一个Web应用程序。一个Context可以有多个Wrapper
- Wrapper：表示一个独立的servlet
每个概念层级都由org.apache.catalina内的一个接口表示。这些接口分别是Engine、Host、Context和Wrapper，他们都继承自Cantainer接口

## Pipeline接口
servlet通过调用invoke()方法来开始调用管道中的阀和基础阀；通过调用addValve()和removeValve()添加删除某个阀；
基础阀是最后调用的阀，负责处理request对象及其对应的response对象

## ValveContext接口
是作为管道的一个内部类实现，可以访问管道的所有成员

## Valve接口
阀是Valve接口的实例，要你过来处理收到的请求