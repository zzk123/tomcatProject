package com.tomcatwork.ex14.startup;

import com.tomcatwork.apache.catalina.Connector;
import com.tomcatwork.apache.catalina.Context;
import com.tomcatwork.apache.catalina.Engine;
import com.tomcatwork.apache.catalina.Host;
import com.tomcatwork.apache.catalina.Lifecycle;
import com.tomcatwork.apache.catalina.LifecycleException;
import com.tomcatwork.apache.catalina.LifecycleListener;
import com.tomcatwork.apache.catalina.Loader;
import com.tomcatwork.apache.catalina.Server;
import com.tomcatwork.apache.catalina.Service;
import com.tomcatwork.apache.catalina.Wrapper;
import com.tomcatwork.apache.catalina.connector.http.HttpConnector;
import com.tomcatwork.apache.catalina.core.StandardContext;
import com.tomcatwork.apache.catalina.core.StandardEngine;
import com.tomcatwork.apache.catalina.core.StandardHost;
import com.tomcatwork.apache.catalina.core.StandardServer;
import com.tomcatwork.apache.catalina.core.StandardService;
import com.tomcatwork.apache.catalina.core.StandardWrapper;
import com.tomcatwork.apache.catalina.loader.WebappLoader;
import com.tomcatwork.ex14.core.SimpleContextConfig;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-09-09
 */
public class Bootstrap {

    public static void main(String[] args) {
        System.setProperty("catalina.base", System.getProperty("user.dir"));
        Connector connector = new HttpConnector();

        Wrapper wrapper1 = new StandardWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new StandardWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        Context context = new StandardContext();
        context.setPath("/app1");
        context.setDocBase("app1");

        context.addChild(wrapper1);
        context.addChild(wrapper2);

        LifecycleListener listener = new SimpleContextConfig();
        ((Lifecycle) context).addLifecycleListener(listener);

        Host host = new StandardHost();
        host.addChild(context);
        host.setName("localhost");
        host.setAppBase("webapps");

        Loader loader = new WebappLoader();
        context.setLoader(loader);
        context.addServletMapping("/Primitive", "Primitive");
        context.addServletMapping("/Modern", "Modern");

        Engine engine = new StandardEngine();
        engine.addChild(host);
        engine.setDefaultHost("localhost");

        Service service = new StandardService();
        service.setName("Stand-alone Service");
        Server server = new StandardServer();
        server.addService(service);
        service.addConnector(connector);

        service.setContainer(engine);

        if(server instanceof Lifecycle){
            try{
                server.initialize();
                ((Lifecycle) server).start();
                server.await();
            }catch (LifecycleException e){
                e.printStackTrace(System.out);
            }
        }

        if(server instanceof Lifecycle){
            try{
                ((Lifecycle) server).stop();
            }catch (LifecycleException e){
                e.printStackTrace(System.out);
            }
        }
    }
}
