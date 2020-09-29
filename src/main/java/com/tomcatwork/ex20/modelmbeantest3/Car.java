package com.tomcatwork.ex20.modelmbeantest3;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-09-14
 */
public class Car implements CarMBean {

    private String color = "red";

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void drive() {
        System.out.println("Baby you can drive my car.");
    }
}
