package com.ayguigu.spring5.bean;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/5/21
 **/
public class Emp {
    private String ename;
    private String gender;
    //员工属于一个部门
    private Dept dept;

    public Emp(String ename, String gender) {
        this.ename = ename;
        this.gender = gender;
    }

    public Emp() {
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "ename='" + ename + '\'' +
                ", gender='" + gender + '\'' +
                ", dept=" + dept +
                '}';
    }
}
