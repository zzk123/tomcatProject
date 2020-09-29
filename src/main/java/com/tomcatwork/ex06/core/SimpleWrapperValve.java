package com.tomcatwork.ex06.core;

import com.tomcatwork.apache.catalina.Contained;
import com.tomcatwork.apache.catalina.Container;
import com.tomcatwork.apache.catalina.Request;
import com.tomcatwork.apache.catalina.Response;
import com.tomcatwork.apache.catalina.Valve;
import com.tomcatwork.apache.catalina.ValveContext;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-08-24
 */
public class SimpleWrapperValve implements Valve, Contained {

    protected  Container container;


    @Override
    public void invoke(Request request, Response response, ValveContext context)
            throws IOException, ServletException {

        SimpleWrapper wrapper = (SimpleWrapper) getContainer();
        ServletRequest sreq = request.getRequest();
        ServletResponse sres = response.getResponse();
        Servlet servlet = null;
        HttpServletRequest hreq = null;
        if(sreq instanceof HttpServletRequest){
            hreq = (HttpServletRequest) sreq;
        }
        HttpServletResponse hres = null;
        if(sres instanceof HttpServletResponse){
            hres = (HttpServletResponse) sres;
        }

        try{
            servlet = wrapper.allocate();
            if(hres != null && hreq != null){
                servlet.service(hreq, hres);
            }else{
                servlet.service(sreq, sres);
            }
        }catch (ServletException e){

        }
    }


    public String getInfo() {
        return null;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

}
