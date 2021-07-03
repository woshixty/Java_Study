package com.ayguigu.spring5.bean;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/5/21
 **/
public class Dept {
    private String dname;
    //一个部门多个员工
//    private

    @Override
    public String toString() {
        return "Dept{" +
                "dname='" + dname + '\'' +
                '}';
    }

    public Dept(String dname) {
        this.dname = dname;
    }

    public Dept() {
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }
}
