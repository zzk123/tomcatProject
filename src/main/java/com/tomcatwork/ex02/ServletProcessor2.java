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
 * @description:
 * @author: zzk
 * @create: 2020-07-01
 */
public class ServletProcessor2 {

    public void process(Request request, Response response){

        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;

        try{
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(Constant.WEB_ROOT);

            String responsity = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();

            urls[0] = new URL(null, responsity, streamHandler);
            loader = new URLClassLoader(urls);
        }catch (IOException e){
            System.out.println(e.toString());
        }

        Class myClass = null;
        try{
            myClass = loader.loadClass(servletName);
        }catch (ClassNotFoundException e){
            System.out.println(e.toString());
        }

        Servlet servlet = null;
        //采用外观类来进行转换，可以屏蔽request，response添加的公共方法调用
        RequestFacade requestFacade = new RequestFacade(request);
        ResponseFacade responseFacade = new ResponseFacade(response);
        try{
            servlet = (Servlet) myClass.newInstance();
            servlet.service((ServletRequest)requestFacade, (ServletResponse) requestFacade);
        }catch (Exception e){
            System.out.println(e.toString());
        }catch (Throwable e){
            System.out.println(e.toString());
        }

    }
}
