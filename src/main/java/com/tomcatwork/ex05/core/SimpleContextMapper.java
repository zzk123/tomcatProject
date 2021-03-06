package com.tomcatwork.ex05.core;

import com.tomcatwork.apache.catalina.Container;
import com.tomcatwork.apache.catalina.HttpRequest;
import com.tomcatwork.apache.catalina.Mapper;
import com.tomcatwork.apache.catalina.Request;
import com.tomcatwork.apache.catalina.Wrapper;
import com.tomcatwork.ex04.core.SimpleContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-08-06
 */

public class SimpleContextMapper implements Mapper {

    /**
     * The Container with which this Mapper is associated.
     */
    private SimpleContext context = null;

    public Container getContainer() {
        return (context);
    }

    public void setContainer(Container container) {
        if (!(container instanceof SimpleContext))
            throw new IllegalArgumentException
                    ("Illegal type of container");
        context = (SimpleContext) container;
    }

    public String getProtocol() {
        return null;
    }

    public void setProtocol(String protocol) {
    }


    /**
     * Return the child Container that should be used to process this Request,
     * based upon its characteristics.  If no such child Container can be
     * identified, return <code>null</code> instead.
     *
     * @param request Request being processed
     * @param update Update the Request to reflect the mapping selection?
     *
     * @exception IllegalArgumentException if the relative portion of the
     *  path cannot be URL decoded
     */
    public Container map(Request request, boolean update) {
        // Identify the context-relative URI to be mapped
        String contextPath =
                ((HttpServletRequest) request.getRequest()).getContextPath();
        String requestURI = ((HttpRequest) request).getDecodedRequestURI();
        String relativeURI = requestURI.substring(contextPath.length());
        // Apply the standard request URI mapping rules from the specification
        Wrapper wrapper = null;
        String servletPath = relativeURI;
        String pathInfo = null;
        String name = context.findServletMapping(relativeURI);
        if (name != null)
            wrapper = (Wrapper) context.findChild(name);
        return (wrapper);
    }
}