package com.qf.entity;

import java.util.Date;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/7/11
 **/
public class User {

    private Integer id;
    private String username;
    private String password;
    private Boolean gender;
    private Date regist_Time;

    public User() {
    }

    public User(Integer id, String username, String password, Boolean gender, Date registTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.regist_Time = registTime;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getRegistTime() {
        return regist_Time;
    }

    public void setRegistTime(Date registTime) {
        this.regist_Time = registTime;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", registTime=" + regist_Time +
                '}';
    }
}
