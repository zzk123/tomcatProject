package com.tomcatwork.ex05.values;

import com.tomcatwork.apache.catalina.Contained;
import com.tomcatwork.apache.catalina.Container;
import com.tomcatwork.apache.catalina.Request;
import com.tomcatwork.apache.catalina.Response;
import com.tomcatwork.apache.catalina.Valve;
import com.tomcatwork.apache.catalina.ValveContext;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import java.io.IOException;

/**
 * @program: testProject
 * @description: 阀，用于将客户端的IP地址输出到客户端上
 * @author: zzk
 * @create: 2020-08-06
 */
public class ClientIPLoggerValve implements Valve, Contained {

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
        System.out.println("Client IP Logger Valve");
        ServletRequest sreq = request.getRequest();
        System.out.println(sreq.getRemoteAddr());
        System.out.println("------------------------");
    }
}
