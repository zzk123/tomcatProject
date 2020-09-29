package com.tomcatwork.ex07.core;

import com.tomcatwork.apache.catalina.Lifecycle;
import com.tomcatwork.apache.catalina.LifecycleEvent;
import com.tomcatwork.apache.catalina.LifecycleListener;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-08-21
 */
public class SimpleContextLifecycleListener implements LifecycleListener {
    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        Lifecycle lifecycle = event.getLifecycle();
        System.out.println("SimpleContextLifecycleListener's event " + event.getType().toString());
        if(Lifecycle.START_EVENT.equals(event.getType())){
            System.out.println("Starting context.");
        }
        else if(Lifecycle.STOP_EVENT.equals(event.getType())){
            System.out.println("Stopping context.");
        }

    }
}
