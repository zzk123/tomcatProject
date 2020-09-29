package com.tomcatwork.ex15.startup;

import com.tomcatwork.apache.catalina.Connector;
import com.tomcatwork.apache.catalina.Container;
import com.tomcatwork.apache.catalina.Context;
import com.tomcatwork.apache.catalina.Host;
import com.tomcatwork.apache.catalina.Lifecycle;
import com.tomcatwork.apache.catalina.LifecycleListener;
import com.tomcatwork.apache.catalina.Loader;
import com.tomcatwork.apache.catalina.connector.http.HttpConnector;
import com.tomcatwork.apache.catalina.core.StandardContext;
import com.tomcatwork.apache.catalina.core.StandardHost;
import com.tomcatwork.apache.catalina.loader.WebappLoader;
import com.tomcatwork.apache.catalina.startup.ContextConfig;

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

        Context context = new StandardContext();
        context.setPath("/app1");
        context.setDocBase("app1");
        LifecycleListener listener = new ContextConfig();
        ((Lifecycle) context).addLifecycleListener(listener);

        Host host = new StandardHost();
        host.addChild(context);
        host.setName("localhost");
        host.setAppBase("webapps");

        Loader loader = new WebappLoader();
        context.setLoader(loader);
        connector.setContainer(host);

        try{
            connector.initialize();
            ((Lifecycle) connector).start();
            ((Lifecycle) host).start();
            Container[] c = context.findChildren();
            int length = c.length;
            for(int i=0; i<length; i++){
                Container child = c[i];
                System.out.println(child.getName());
            }

            System.in.read();
            ((Lifecycle) host).stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
