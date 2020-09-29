package com.tomcatwork.ex02;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * @program: testProject
 * @description: 处理目录下的class文件
 * @author: zzk
 * @create: 2020-07-01
 */
public class ServletProcessor1 {

    /**
     * 加载执行对应的class文件
     * @param request
     * @param response
     */
    public void process(Request request, Response response){

        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;
        //类加载器加载目录下的对应的class对象
        try{
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(Constant.WEB_ROOT);
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
        }catch (IOException e){
            System.out.println(e.toString());
        }
        //加载请求的类
        Class myClass = null;
        try{
            myClass = loader.loadClass(servletName);
        }catch (ClassNotFoundException e){
            System.out.println(e.toString());
        }
        //执行对应的方法
        Servlet servlet = null;
        try{
            servlet = (Servlet) myClass.newInstance();
            servlet.service((ServletRequest)request, (ServletResponse) response);
        }catch (Exception e){
            System.out.println(e.toString());
        }catch (Throwable e){
            System.out.println(e.toString());
        }

    }
}
