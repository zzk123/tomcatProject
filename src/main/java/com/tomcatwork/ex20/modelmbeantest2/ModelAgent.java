package com.tomcatwork.ex20.modelmbeantest2;

import org.apache.commons.modeler.ManagedBean;
import org.apache.commons.modeler.Registry;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;
import java.io.InputStream;
import java.net.URL;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-09-10
 */
public class ModelAgent {

    private Registry registry;

    private MBeanServer mBeanServer;

    public ModelAgent(){
        registry = createRegistry();
        try{
            mBeanServer = Registry.getServer();
        }catch (Throwable t){
            t.printStackTrace(System.out);
            System.exit(1);
        }
    }

    public MBeanServer getmBeanServer() {
        return mBeanServer;
    }

    public Registry createRegistry(){
        Registry registry = null;
        try{
            URL url = ModelAgent.class.getResource("/com/tomcatwork/ex20/modelmbeantest2/car-mbean-descriptor.xml");
            InputStream stream = url.openStream();
            Registry.loadRegistry(stream);
            stream.close();
            registry = Registry.getRegistry();
        }catch (Throwable t){
            System.out.println(t.toString());
        }
        return registry;
    }

    private ObjectName createObjectName(){
        ObjectName objectName = null;
        String domain = mBeanServer.getDefaultDomain();
        try{
            objectName = new ObjectName(domain + ":type=MyCar");
        }catch (MalformedObjectNameException e){
            e.printStackTrace();
        }
        return objectName;
    }

    public ModelMBean createModelMBean(String mBeanName) throws Exception{
        ManagedBean managedBean = registry.findManagedBean(mBeanName);
        if(managedBean == null){
            System.out.println("ManagedBean null");
            return null;
        }
        ModelMBean mBean = managedBean.createMBean();
        ObjectName objectName = createObjectName();
        return mBean;
    }

    public static void main(String[] args) {
        ModelAgent agent = new ModelAgent();
        MBeanServer mBeanServer = agent.getmBeanServer();
        Car car = new Car();
        System.out.println("Creating ObjectName");
        ObjectName objectName = agent.createObjectName();
        try{
            ModelMBean modelMBean = agent.createModelMBean("myMBean");
            modelMBean.setManagedResource(car, "ObjectReference");
            mBeanServer.registerMBean(modelMBean, objectName);
        }catch (Exception e){
            System.out.println(e.toString());
        }

        try{
            Attribute attribute = new Attribute("Color", "green");
            mBeanServer.setAttribute(objectName, attribute);
            String color = (String) mBeanServer.getAttribute(objectName, "Color");
            System.out.println("Color:" + color);

            attribute = new Attribute("Color", "blue");
            mBeanServer.setAttribute(objectName, attribute);
            color = (String) mBeanServer.getAttribute(objectName, "Color");
            System.out.println("Color:" + color);
            mBeanServer.invoke(objectName, "drive", null, null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
