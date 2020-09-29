package com.tomcatwork.ex09.core;

import com.tomcatwork.apache.catalina.Contained;
import com.tomcatwork.apache.catalina.Container;
import com.tomcatwork.apache.catalina.Lifecycle;
import com.tomcatwork.apache.catalina.LifecycleException;
import com.tomcatwork.apache.catalina.LifecycleListener;
import com.tomcatwork.apache.catalina.Pipeline;
import com.tomcatwork.apache.catalina.Request;
import com.tomcatwork.apache.catalina.Response;
import com.tomcatwork.apache.catalina.Valve;
import com.tomcatwork.apache.catalina.ValveContext;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-08-27
 */
public class SimplePipeline implements Pipeline, Lifecycle {

    public SimplePipeline(Container container){
        setContainer(container);
    }

    protected Valve basic = null;

    protected Container container = null;

    protected Valve valves[] = new Valve[0];

    public void setContainer(Container container) {
        this.container = container;
    }


    @Override
    public void addLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public void start() throws LifecycleException {

    }

    @Override
    public void stop() throws LifecycleException {

    }

    @Override
    public Valve getBasic() {
        return basic;
    }

    @Override
    public void setBasic(Valve valve) {
        this.basic = valve;
        ((Contained) valve).setContainer(container);
    }

    @Override
    public void addValve(Valve valve) {
        if(valve instanceof  Contained){
            ((Contained) valve).setContainer(this.container);
        }

        synchronized (valves){
            Valve results[] = new Valve[valves.length + 1];
            System.arraycopy(valves, 0, results, 0, valves.length);
            results[valves.length] = valve;
            valves = results;
        }
    }

    @Override
    public Valve[] getValves() {
        return valves;
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        (new StandardPipelineValveContext()).invokeNext(request, response);
    }

    @Override
    public void removeValve(Valve valve) {

    }

    protected class StandardPipelineValveContext implements ValveContext{

        protected int stage = 0;

        @Override
        public String getInfo() {
            return null;
        }

        @Override
        public void invokeNext(Request request, Response response) throws IOException, ServletException {
            int subscript = stage;
            stage = stage + 1;
            if(subscript < valves.length){
                valves[subscript].invoke(request, response, this);
            }
            else if((subscript == valves.length) && (basic != null)){
                basic.invoke(request, response, this);
            }
            else{
                throw new ServletException("No valve");
            }
        }

    }
}
