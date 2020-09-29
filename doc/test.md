# 下载
* github下载  
`wget https://alibaba.github.io/arthas/arthas-boot.jar`
* Gitee 下载  
`wget https://arthas.gitee.io/arthas-boot.jar`
* 打印帮助信息  
`java -jar arthas-boot.jar -h`

# 命令
* dashboard   
使用 dashboard 命令可以概览程序的 线程、内存、GC、运行环境信息。

* thread  
使用 thread查看所有线程信息，同时会列出每个线程的 CPU 使用率   
使用 thread -n [显示的线程个数] ，就可以排列出 CPU 使用率 Top N 的线程   
使用 thread | grep pool 命令查看线程池里线程信息   
使用 thread -b 命令查看直接定位到死锁信息

* jad
反编译只显示源码  
jad com.Arthas  
jad --source-only com.Arthas  
反编译某个类的某个方法  
jad --source-only com.Arthas mysql  

* sc -d -f 
命令查看类的字段信息

* sm
使用 sm 命令查看类的方法信息。

* ognl 
使用ognl 操作变量的值 
如：
   ognl '@com.Arthas.AlohasTest@hashSet'              获取AlohasTest类中hashSet的存值信息   
   ognl '@com.Arthas.AlohasTest@hashSet.size()'       获取AlohasTest类中hashSet的数量  
   ognl '@com.Arthas.AlohasTest@hashSet.add("test")'  获取AlohasTest类中hashSet添加值   
文档：https://github.com/alibaba/arthas/issues/71

