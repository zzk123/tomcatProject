一个功能齐全的servlet容器有以下几种事可以做：
 - 当第一次调用某个servlet时，要载入该servlet类，并调用其init()方法（仅此一次）
 - 针对每个request请求，创建一个javax.servlet.ServletRequest实例和一个javax.servlet.ServletResponse实例
 - 调用该servlet的service()方法，将servletRequest对象和servletResponse对象作为参数传入；
 - 当关闭该该servlet类时，调用其destroy()方法，并卸载该servlet类