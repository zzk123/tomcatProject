package com.tomcatwork.ex20.modelmbeantest3;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-09-14
 */
public class StandardAgent {

    private MBeanServer mBeanServer = null;

    public StandardAgent(){
        mBeanServer = MBeanServerFactory.createMBeanServer();
    }

    public MBeanServer getMBeanServer(){
        return mBeanServer;
    }

    public ObjectName creatObjectName(String name){
        ObjectName objectName = null;
        try{
            objectName = new ObjectName(name);
        }catch (Exception e){

        }
        return objectName;
    }

    private void createStandardBean(ObjectName objectName, String managedResourceClassName){
        try{
            mBeanServer.createMBean(managedResourceClassName, objectName);
        }catch (Exception e){

        }
    }

    public static void main(String[] args) {
        StandardAgent agent = new StandardAgent();
        MBeanServer mBeanServer = agent.getMBeanServer();
        String domain = mBeanServer.getDefaultDomain();
        String managedResourceClassName = "com.tomcatwork.ex20.modelmbeantest3.Car";
        ObjectName objectName = agent.creatObjectName(domain + ":type=" + managedResourceClassName);
        agent.createStandardBean(objectName, managedResourceClassName);

        try{
            Attribute colorAttribute = new Attribute("Color", "blue");
            mBeanServer.setAttribute(objectName, colorAttribute);
            System.out.println(mBeanServer.getAttribute(objectName, "Color"));
            mBeanServer.invoke(objectName, "drive", null, null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
