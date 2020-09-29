package com.tomcatwork.ex05.core;

import com.tomcatwork.apache.catalina.Contained;
import com.tomcatwork.apache.catalina.Container;
import com.tomcatwork.apache.catalina.Context;
import com.tomcatwork.apache.catalina.HttpRequest;
import com.tomcatwork.apache.catalina.Request;
import com.tomcatwork.apache.catalina.Response;
import com.tomcatwork.apache.catalina.Valve;
import com.tomcatwork.apache.catalina.ValveContext;
import com.tomcatwork.apache.catalina.Wrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-08-05
 */
public class SimpleContextValve implements Valve, Contained {

    protected Container container;

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) request.getRequest();
        String contextPath = hreq.getContextPath();
        String requestURI = ((HttpRequest) request).getDecodedRequestURI();
        String relativeURL = requestURI.substring(contextPath.length()).toUpperCase();

        Context context = (Context) getContainer();
        Wrapper wrapper = null;
        try{
            wrapper = (Wrapper) context.map(request, true);
        }catch (IllegalArgumentException e){
            badRequest(relativeURL, (HttpServletResponse) response.getResponse());
            return;
        }
        if(wrapper == null){
            notFound(requestURI, (HttpServletResponse) response.getResponse());
            return;
        }

        response.setContext(context);
        wrapper.invoke(request, response);
    }

    private void badRequest(String requestURL, HttpServletResponse response){
        try{
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, requestURL);
        }catch (IllegalStateException e){

        }catch (IOException e){

        }
    }

    private void notFound(String requestURI, HttpServletResponse response){
        try{
            response.sendError(HttpServletResponse.SC_NOT_FOUND, requestURI);
        }catch (IllegalStateException e){

        }catch (IOException e){

        }
    }
}
