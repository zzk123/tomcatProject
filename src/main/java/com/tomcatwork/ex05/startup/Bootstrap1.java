package com.tomcatwork.ex05.startup;

import com.tomcatwork.apache.catalina.Loader;
import com.tomcatwork.apache.catalina.Pipeline;
import com.tomcatwork.apache.catalina.Valve;
import com.tomcatwork.apache.catalina.Wrapper;
import com.tomcatwork.apache.catalina.connector.http.HttpConnector;
import com.tomcatwork.ex05.core.SimpleLoader;
import com.tomcatwork.ex05.core.SimpleWrapper;
import com.tomcatwork.ex05.values.ClientIPLoggerValve;
import com.tomcatwork.ex05.values.HeaderLoggerValve;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-08-05
 */
public class Bootstrap1 {

    public static void main(String[] args) {

        HttpConnector connector = new HttpConnector();
        Wrapper wrapper = new SimpleWrapper();
        wrapper.setServletClass("ModernServlet");
        Loader loader = new SimpleLoader();
        Valve valve1 = new HeaderLoggerValve();
        Valve valve2 = new ClientIPLoggerValve();
        //添加组件
        wrapper.setLoader(loader);
        ((Pipeline) wrapper).addValve(valve1);
        ((Pipeline) wrapper).addValve(valve2);
        //容器添加实例
        connector.setContainer(wrapper);

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
