package com.ayguigu.spring5;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/5/19
 **/
public class Orders {

    private String name;

    private String address;

    public Orders(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
