package com.tomcatwork.ex07.startup;

import com.tomcatwork.apache.catalina.Connector;
import com.tomcatwork.apache.catalina.Context;
import com.tomcatwork.apache.catalina.Lifecycle;
import com.tomcatwork.apache.catalina.LifecycleListener;
import com.tomcatwork.apache.catalina.Loader;
import com.tomcatwork.apache.catalina.Mapper;
import com.tomcatwork.apache.catalina.Wrapper;
import com.tomcatwork.apache.catalina.connector.http.HttpConnector;
import com.tomcatwork.apache.catalina.logger.FileLogger;
import com.tomcatwork.ex07.core.SimpleContext;
import com.tomcatwork.ex07.core.SimpleContextLifecycleListener;
import com.tomcatwork.ex07.core.SimpleContextMapper;
import com.tomcatwork.ex07.core.SimpleLoader;
import com.tomcatwork.ex07.core.SimpleWrapper;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-08-24
 */
public class Bootstrap {

    public static void main(String[] args) {
        Connector connector = new HttpConnector();
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        Context context = new SimpleContext();
        context.addChild(wrapper1);
        context.addChild(wrapper2);

        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("http");
        LifecycleListener lifecycleListener = new SimpleContextLifecycleListener();
        ((Lifecycle) context).addLifecycleListener(lifecycleListener);
        context.addMapper(mapper);
        Loader loader = new SimpleLoader();
        context.setLoader(loader);

        context.addServletMapping("/Primitive", "Primitive");
        context.addServletMapping("/Modern", "Modern");
        connector.setContainer(context);

        // ------ add logger --------
        System.setProperty("catalina.base", System.getProperty("user.dir"));
        FileLogger logger = new FileLogger();
        logger.setPrefix("FileLog_");
        logger.setSuffix(".txt");
        logger.setTimestamp(true);
        logger.setDirectory("webroot");
        context.setLogger(logger);

        //---------------------------

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
