package com.tomcatwork.ex05.core;

import com.tomcatwork.apache.catalina.Container;
import com.tomcatwork.apache.catalina.DefaultContext;
import com.tomcatwork.apache.catalina.Loader;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * @program: testProject
 * @description: 载入相关的servlet类
 * @author: zzk
 * @create: 2020-08-05
 */
public class SimpleLoader implements Loader {

    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator  + "webroot";

    ClassLoader classLoader = null;
    Container container = null;

    public SimpleLoader(){
        try{
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(WEB_ROOT);
            String reponsitory = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
            urls[0] = new URL(null, reponsitory, streamHandler);
            classLoader = new URLClassLoader(urls);
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public DefaultContext getDefaultContext() {
        return null;
    }

    public void setDefaultContext(DefaultContext defaultContext) {
    }

    public boolean getDelegate() {
        return false;
    }

    public void setDelegate(boolean delegate) {
    }

    public String getInfo() {
        return "A simple loader";
    }

    public boolean getReloadable() {
        return false;
    }

    public void setReloadable(boolean reloadable) {
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
    }

    public void addRepository(String repository) {
    }

    public String[] findRepositories() {
        return null;
    }

    public boolean modified() {
        return false;
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
    }

}
