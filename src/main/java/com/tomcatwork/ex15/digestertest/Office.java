package com.tomcatwork.ex15.digestertest;

/**
 * @program: testProject
 * @description:
 * @author: zzk
 * @create: 2020-09-09
 */
public class Office {
    private Address address;
    private String description;
    public Office() {
        System.out.println("..Creating Office");
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        System.out.println("..Setting office description : " + description);
        this.description = description;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        System.out.println("..Setting office address : " + address);
        this.address = address;
    }
}
