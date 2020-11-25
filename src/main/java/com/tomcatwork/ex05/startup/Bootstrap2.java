package com.tomcatwork.ex05.startup;

import com.tomcatwork.apache.catalina.Context;
import com.tomcatwork.apache.catalina.HttpRequest;
import com.tomcatwork.apache.catalina.Loader;
import com.tomcatwork.apache.catalina.Mapper;
import com.tomcatwork.apache.catalina.Pipeline;
import com.tomcatwork.apache.catalina.Valve;
import com.tomcatwork.apache.catalina.Wrapper;
import com.tomcatwork.apache.catalina.connector.http.HttpConnector;
import com.tomcatwork.ex05.core.SimpleContext;
import com.tomcatwork.ex05.core.SimpleContextMapper;
import com.tomcatwork.ex05.core.SimpleLoader;
import com.tomcatwork.ex05.core.SimpleWrapper;
import com.tomcatwork.ex05.values.ClientIPLoggerValve;
import com.tomcatwork.ex05.values.HeaderLoggerValve;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-08-06
 */
public class Bootstrap2 {

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");
        //添加两个实例
        Context context = new SimpleContext();
        context.addChild(wrapper1);
        context.addChild(wrapper2);

        Valve valve1 = new HeaderLoggerValve();
        Valve valve2 = new ClientIPLoggerValve();

        ((Pipeline) context).addValve(valve1);
        ((Pipeline) context).addValve(valve2);
        //创建映射器对象，将其添加到SimpleContext实例中。负责查找Context实例中的子容器来处理HTTP请求
        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("http");
        context.addMapper(mapper);

        Loader loader = new SimpleLoader();
        context.setLoader(loader);
        //为两个实例添加两种模式
        context.addServletMapping("/Primitive", "Primitive");
        context.addServletMapping("/Modern", "Modern");
        //将Context与连接器相连，初始化连接器，调用其start()方法
        connector.setContainer(context);
        try {
            connector.initialize();
            connector.start();

            // make the application wait until we press a key.
            System.in.read();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
