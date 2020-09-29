package com.tomcatwork.ex06.core;

import com.tomcatwork.apache.catalina.Container;
import com.tomcatwork.apache.catalina.HttpRequest;
import com.tomcatwork.apache.catalina.Mapper;
import com.tomcatwork.apache.catalina.Request;
import com.tomcatwork.apache.catalina.Wrapper;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-08-21
 */
public class SimpleContextMapper implements Mapper {

    private SimpleContext context = null;


    @Override
    public Container getContainer() {
        return (context);
    }

    @Override
    public void setContainer(Container container) {
        if(!(container instanceof SimpleContext))
            throw new IllegalArgumentException
                    ("Illegal type of container");
        context = (SimpleContext) container;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public void setProtocol(String protocol) {

    }

    @Override
    public Container map(Request request, boolean update) {
        if(update && (request.getWrapper() != null)){
            return (request.getWrapper());
        }
        String contextPath = ((HttpServletRequest) request.getRequest()).getContextPath();
        String requestURI = ((HttpRequest) request).getDecodedRequestURI();
        String relativeURI = requestURI.substring(contextPath.length());

        Wrapper wrapper = null;
        String servletPath = relativeURI;
        String pathInfo = null;
        String name = context.findServletMapping(relativeURI);
        if(name != null){
            wrapper = (Wrapper) context.findChild(name);
        }
        if(update){
            request.setWrapper(wrapper);
            ((HttpRequest) request).setServletPath(servletPath);
            ((HttpRequest) request).setPathInfo(pathInfo);
        }
        return (wrapper);
    }
}
