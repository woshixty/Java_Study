package com.atguigu.spring5.entity;

/**
 * @author qyyzxty@icloud.com
 * 2021/6/12
 **/
public class Book {
    private Integer userId;
    private String username;
    private String ustatus;
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUstatus() {
        return ustatus;
    }

    public void setUstatus(String ustatus) {
        this.ustatus = ustatus;
    }
}
