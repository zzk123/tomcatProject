package com.tomcatwork.ex05.core;

import com.tomcatwork.apache.catalina.Cluster;
import com.tomcatwork.apache.catalina.Container;
import com.tomcatwork.apache.catalina.ContainerListener;
import com.tomcatwork.apache.catalina.InstanceListener;
import com.tomcatwork.apache.catalina.Loader;
import com.tomcatwork.apache.catalina.Logger;
import com.tomcatwork.apache.catalina.Manager;
import com.tomcatwork.apache.catalina.Mapper;
import com.tomcatwork.apache.catalina.Pipeline;
import com.tomcatwork.apache.catalina.Realm;
import com.tomcatwork.apache.catalina.Request;
import com.tomcatwork.apache.catalina.Response;
import com.tomcatwork.apache.catalina.Valve;
import com.tomcatwork.apache.catalina.Wrapper;

import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-08-05
 */
public class SimpleWrapper implements Wrapper, Pipeline {

    // the servlet instance
    private Servlet instance = null;
    private String servletClass;

    // loader变量指明了载入servlet类要使用的载入器
    private Loader loader;
    //表明了该Wrapper实例的父容器
    protected Container parent = null;
    private String name;
    private SimplePipeline pipeline = new SimplePipeline(this);


    public SimpleWrapper() {
        pipeline.setBasic(new SimpleWrapperValve());
    }

    public synchronized void addValve(Valve valve) {
        pipeline.addValve(valve);
    }

    public Servlet allocate() throws ServletException {
        // Load and initialize our instance if necessary
        if (instance==null) {
            try {
                instance = loadServlet();
            }
            catch (ServletException e) {
                throw e;
            }
            catch (Throwable e) {
                throw new ServletException("Cannot allocate a servlet instance", e);
            }
        }
        return instance;
    }

    private Servlet loadServlet() throws ServletException {
        if (instance!=null)
            return instance;

        Servlet servlet = null;
        String actualClass = servletClass;
        if (actualClass == null) {
            throw new ServletException("servlet class has not been specified");
        }

        Loader loader = getLoader();
        // Acquire an instance of the class loader to be used
        if (loader==null) {
            throw new ServletException("No loader.");
        }
        ClassLoader classLoader = loader.getClassLoader();

        // Load the specified servlet class from the appropriate class loader
        Class classClass = null;
        try {
            if (classLoader!=null) {
                classClass = classLoader.loadClass(actualClass);
            }
        }
        catch (ClassNotFoundException e) {
            throw new ServletException("Servlet class not found");
        }
        // Instantiate and initialize an instance of the servlet class itself
        try {
            servlet = (Servlet) classClass.newInstance();
        }
        catch (Throwable e) {
            throw new ServletException("Failed to instantiate servlet");
        }

        // Call the initialization method of this servlet
        try {
            servlet.init(null);
        }
        catch (Throwable f) {
            throw new ServletException("Failed initialize servlet.");
        }
        return servlet;
    }

    public String getInfo() {
        return null;
    }

    //如果关联了一个载入器就返回该载入器，没有就返回父容器的载入器
    public Loader getLoader() {
        if (loader != null)
            return (loader);
        if (parent != null)
            return (parent.getLoader());
        return (null);
    }

    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    public Logger getLogger() {
        return null;
    }

    public void setLogger(Logger logger) {
    }

    public Manager getManager() {
        return null;
    }

    public void setManager(Manager manager) {
    }

    public Cluster getCluster() {
        return null;
    }

    public void setCluster(Cluster cluster) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Container getParent() {
        return parent;
    }

    public void setParent(Container container) {
        parent = container;
    }

    public ClassLoader getParentClassLoader() {
        return null;
    }

    public void setParentClassLoader(ClassLoader parent) {
    }

    public Realm getRealm() {
        return null;
    }

    public void setRealm(Realm realm) {
    }

    public DirContext getResources() {
        return null;
    }

    public void setResources(DirContext resources) {
    }

    public long getAvailable() {
        return 0;
    }

    public void setAvailable(long available) {
    }

    public String getJspFile() {
        return null;
    }

    public void setJspFile(String jspFile) {
    }

    public int getLoadOnStartup() {
        return 0;
    }

    public void setLoadOnStartup(int value) {
    }

    public String getRunAs() {
        return null;
    }

    public void setRunAs(String runAs) {
    }

    public String getServletClass() {
        return null;
    }

    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    public void addChild(Container child) {
    }

    public void addContainerListener(ContainerListener listener) {
    }

    public void addMapper(Mapper mapper) {
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
    }

    public Container findChild(String name) {
        return null;
    }

    public Container[] findChildren() {
        return null;
    }

    public ContainerListener[] findContainerListeners() {
        return null;
    }

    public void addInitParameter(String name, String value) {
    }

    public void addInstanceListener(InstanceListener listener) {
    }

    public void addSecurityReference(String name, String link) {
    }

    public void deallocate(Servlet servlet) throws ServletException {
    }

    public String findInitParameter(String name) {
        return null;
    }

    public String[] findInitParameters() {
        return null;
    }

    public String findSecurityReference(String name) {
        return null;
    }

    public String[] findSecurityReferences() {
        return null;
    }

    public Mapper findMapper(String protocol) {
        return null;
    }

    public Mapper[] findMappers() {
        return null;
    }

    public void invoke(Request request, Response response)
            throws IOException, ServletException {
        pipeline.invoke(request, response);
    }

    public boolean isUnavailable() {
        return false;
    }

    public void load() throws ServletException {
        instance = loadServlet();
    }

    public Container map(Request request, boolean update) {
        return null;
    }

    public void removeChild(Container child) {
    }

    public void removeContainerListener(ContainerListener listener) {
    }

    public void removeMapper(Mapper mapper) {
    }

    public void removeInitParameter(String name) {
    }

    public void removeInstanceListener(InstanceListener listener) {
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
    }

    public void removeSecurityReference(String name) {
    }

    public void unavailable(UnavailableException unavailable) {
    }

    public void unload() throws ServletException {
    }

    // method implementations of Pipeline
    public Valve getBasic() {
        return pipeline.getBasic();
    }

    public void setBasic(Valve valve) {
        pipeline.setBasic(valve);
    }

    public Valve[] getValves() {
        return pipeline.getValves();
    }

    public void removeValve(Valve valve) {
        pipeline.removeValve(valve);
    }

}
