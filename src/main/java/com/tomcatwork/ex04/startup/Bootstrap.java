package com.tomcatwork.ex04.startup;

import com.tomcatwork.apache.catalina.HttpRequest;
import com.tomcatwork.apache.catalina.connector.http.HttpConnector;
import com.tomcatwork.ex04.core.SimpleContainer;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-08-04
 */
public class Bootstrap {

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        SimpleContainer container = new SimpleContainer();
        connector.setContainer(container);
        try{
            connector.initialize();
            connector.start();

            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
