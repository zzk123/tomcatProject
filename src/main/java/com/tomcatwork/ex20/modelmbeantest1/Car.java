package com.tomcatwork.ex20.modelmbeantest1;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-09-10
 */
public class Car {

    private String color = "red";

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void drive() {
        System.out.println("Baby you can drive my car.");
    }
}
