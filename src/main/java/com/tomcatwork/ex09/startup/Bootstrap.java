package com.tomcatwork.ex09.startup;

import com.tomcatwork.apache.catalina.Connector;
import com.tomcatwork.apache.catalina.Context;
import com.tomcatwork.apache.catalina.Lifecycle;
import com.tomcatwork.apache.catalina.LifecycleListener;
import com.tomcatwork.apache.catalina.Loader;
import com.tomcatwork.apache.catalina.Manager;
import com.tomcatwork.apache.catalina.Wrapper;
import com.tomcatwork.apache.catalina.connector.http.HttpConnector;
import com.tomcatwork.apache.catalina.core.StandardContext;
import com.tomcatwork.apache.catalina.loader.WebappLoader;
import com.tomcatwork.apache.catalina.session.StandardManager;
import com.tomcatwork.ex09.core.SimpleContextConfig;
import com.tomcatwork.ex09.core.SimpleWrapper;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-09-03
 */
public class Bootstrap {
    public static void main(String[] args) {
        //invoke: http://localhost:8080/myApp/Session

        System.setProperty("catalina.base", System.getProperty("user.dir"));
        Connector connector = new HttpConnector();
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Session");
        wrapper1.setServletClass("SessionServlet");

        Context context = new StandardContext();
        // StandardContext's start method adds a default mapper
        context.setPath("/myApp");
        context.setDocBase("myApp");

        context.addChild(wrapper1);

        // context.addServletMapping(pattern, name);
        // note that we must use /myApp/Session, not just /Session
        // because the /myApp section must be the same as the path, so the cookie will
        // be sent back.
        context.addServletMapping("/myApp/Session", "Session");
        // add ContextConfig. This listener is important because it configures
        // StandardContext (sets configured to true), otherwise StandardContext
        // won't start
        LifecycleListener listener = new SimpleContextConfig();
        ((Lifecycle) context).addLifecycleListener(listener);

        // here is our loader
        Loader loader = new WebappLoader();
        // associate the loader with the Context
        context.setLoader(loader);

        connector.setContainer(context);

        // add a Manager
        Manager manager = new StandardManager();
        context.setManager(manager);

        try {
            connector.initialize();
            ((Lifecycle) connector).start();

            ((Lifecycle) context).start();

            // make the application wait until we press a key.
            System.in.read();
            ((Lifecycle) context).stop();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
