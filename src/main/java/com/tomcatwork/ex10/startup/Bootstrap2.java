package com.tomcatwork.ex10.startup;

import com.tomcatwork.apache.catalina.Connector;
import com.tomcatwork.apache.catalina.Context;
import com.tomcatwork.apache.catalina.Lifecycle;
import com.tomcatwork.apache.catalina.LifecycleListener;
import com.tomcatwork.apache.catalina.Loader;
import com.tomcatwork.apache.catalina.Realm;
import com.tomcatwork.apache.catalina.Wrapper;
import com.tomcatwork.apache.catalina.connector.http.HttpConnector;
import com.tomcatwork.apache.catalina.core.StandardContext;
import com.tomcatwork.apache.catalina.deploy.LoginConfig;
import com.tomcatwork.apache.catalina.deploy.SecurityCollection;
import com.tomcatwork.apache.catalina.deploy.SecurityConstraint;
import com.tomcatwork.apache.catalina.loader.WebappLoader;
import com.tomcatwork.ex10.core.SimpleContextConfig;
import com.tomcatwork.ex10.core.SimpleWrapper;
import com.tomcatwork.ex10.realm.SimpleUserDatabaseRealm;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-09-08
 */
public class Bootstrap2 {

    public static void main(String[] args) {

        System.setProperty("catalina.base", System.getProperty("user.dir"));
        Connector connector = new HttpConnector();
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        Context context = new StandardContext();
        // StandardContext's start method adds a default mapper
        context.setPath("/myApp");
        context.setDocBase("myApp");
        LifecycleListener listener = new SimpleContextConfig();
        ((Lifecycle) context).addLifecycleListener(listener);

        context.addChild(wrapper1);
        context.addChild(wrapper2);
        // for simplicity, we don't add a valve, but you can add
        // valves to context or wrapper just as you did in Chapter 6

        Loader loader = new WebappLoader();
        context.setLoader(loader);
        // context.addServletMapping(pattern, name);
        context.addServletMapping("/Primitive", "Primitive");
        context.addServletMapping("/Modern", "Modern");
        // add ContextConfig. This listener is important because it configures
        // StandardContext (sets configured to true), otherwise StandardContext
        // won't start

        // add constraint
        SecurityCollection securityCollection = new SecurityCollection();
        securityCollection.addPattern("/");
        securityCollection.addMethod("GET");

        SecurityConstraint constraint = new SecurityConstraint();
        constraint.addCollection(securityCollection);
        constraint.addAuthRole("manager");
        LoginConfig loginConfig = new LoginConfig();
        loginConfig.setRealmName("Simple User Database Realm");
        // add realm
        Realm realm = new SimpleUserDatabaseRealm();
        ((SimpleUserDatabaseRealm) realm).createDatabase("conf/tomcat-users.xml");
        context.setRealm(realm);
        context.addConstraint(constraint);
        context.setLoginConfig(loginConfig);

        connector.setContainer(context);

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
