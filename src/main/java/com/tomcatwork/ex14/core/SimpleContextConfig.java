package com.tomcatwork.ex14.core;

import com.tomcatwork.apache.catalina.Context;
import com.tomcatwork.apache.catalina.Lifecycle;
import com.tomcatwork.apache.catalina.LifecycleEvent;
import com.tomcatwork.apache.catalina.LifecycleListener;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-09-09
 */
public class SimpleContextConfig implements LifecycleListener {
    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        if(Lifecycle.START_EVENT.equals(event.getType())){
            Context context = (Context) event.getLifecycle();
            context.setConfigured(true);
        }
    }
}
