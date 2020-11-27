## 载入器
servlet容器需要实现一个自定义的载入器，原因是：
- servlet应该只允许载入WEB-INFO/classess目录及其子目录下的类，和从部署的库到WEB-INF/lib目录载入类
- 为了提供重载的功能，即当WEB-INFO/classess目录或WEB-INF/lib目录下的类发生变化时，Web应用程序会重新载入这些类

## Java的类载入器
JVM使用3种类载入器来载入所需要的类，分别是引导类载入器（bootstrap class loader）、扩展类载入器（extension class loader）
和系统类载入器（system class loader）。这3种类载入器之间是父子继承关系，其中引导类载入器位于层次结构的最上层，系统类载入
器位于最下层
- 引导类载入器用于引导启动java虚拟机。当调用java.exe程序时，就会启动引导类载入。引导类载入器是使用本地代码来实现的，因为它用来运行JVM所需
的类，以及所有的Java核心类，例如java.lang包和java.io包下的类
- 扩展类载入器负责载入标准扩展目录下的类。这有利于程序开发，因为程序员只需要将JAR文件复制到扩展目录中就可以被类载入器搜索到。Sum公司的JVM的
标准扩展目录是/jdk/jre/lib/ext
- 系统类载入器是默认的类载入器，他会搜索在环境变量CLASSPATH中指明的路径和JVR文件

Tomcat是使用自定义类载入器的原因有3条
- 为了在载入类中指定某些规则
- 为了缓存已经载入的类
- 为了实现类的预载入，方便使用
