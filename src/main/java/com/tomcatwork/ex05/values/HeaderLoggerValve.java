package com.tomcatwork.ex05.values;

import com.tomcatwork.apache.catalina.Contained;
import com.tomcatwork.apache.catalina.Container;
import com.tomcatwork.apache.catalina.Request;
import com.tomcatwork.apache.catalina.Response;
import com.tomcatwork.apache.catalina.Valve;
import com.tomcatwork.apache.catalina.ValveContext;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @program: testProject
 * @description: 阀，将请求头信息输出到控制台
 * @author: zzk
 * @create: 2020-08-06
 */
public class HeaderLoggerValve implements Valve, Contained {

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

        valveContext.invokeNext(request, response);

        System.out.println("Header Logger");
        ServletRequest sreq = request.getRequest();
        if(sreq instanceof HttpServletRequest){
            HttpServletRequest hreq = (HttpServletRequest) sreq;
            Enumeration headerNames = hreq.getHeaderNames();
            while (headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement().toString();
                String headerValue = hreq.getHeader(headerName);
                System.out.println(headerName + ":" + headerValue);
            }
        }else {
            System.out.println("Not an HTTP Request");
            System.out.println("------------------------------------");
        }
    }
}
