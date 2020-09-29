package com.tomcatwork.ex10.core;

import com.tomcatwork.apache.catalina.Authenticator;
import com.tomcatwork.apache.catalina.Context;
import com.tomcatwork.apache.catalina.Lifecycle;
import com.tomcatwork.apache.catalina.LifecycleEvent;
import com.tomcatwork.apache.catalina.LifecycleListener;
import com.tomcatwork.apache.catalina.Pipeline;
import com.tomcatwork.apache.catalina.Valve;
import com.tomcatwork.apache.catalina.core.StandardContext;
import com.tomcatwork.apache.catalina.deploy.LoginConfig;
import com.tomcatwork.apache.catalina.deploy.SecurityConstraint;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-09-03
 */
public class SimpleContextConfig implements LifecycleListener {

    private Context context;

    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        if(Lifecycle.START_EVENT.equals(event.getType())){
            context = (Context) event.getLifecycle();

        }
    }

    private synchronized void authenticatorConfig(){
        SecurityConstraint constraints[] = context.findConstraints();
        if((constraints == null) || (constraints.length == 0)){
            return;
        }
        LoginConfig loginConfig = context.getLoginConfig();
        if(loginConfig == null){
            loginConfig = new LoginConfig("NONE", null, null, null);
            context.setLoginConfig(loginConfig);
        }

        Pipeline pipeline = ((StandardContext) context).getPipeline();
        if(pipeline != null){
            Valve basic = pipeline.getBasic();
            if((basic != null) && (basic instanceof Authenticator)){
                return;
            }
            Valve valves[] = pipeline.getValves();
            for(int i=0; i<valves.length; i++){
                if(valves[i] instanceof  Authenticator){
                    return;
                }
            }
        }else{
            return;
        }

        if(context.getRealm() == null){
            return;
        }

        String authenticatorName = "org.apache.catalina.authenticator.BasicAuthenticator";

        Valve authenticator = null;
        try{
            Class authenticatorClass = Class.forName(authenticatorName);
            authenticator = (Valve) authenticatorClass.newInstance();
            ((StandardContext) context).addValve(authenticator);
            System.out.println("Added authenticator valve to Context");
        }catch (Throwable t){

        }
    }
}
