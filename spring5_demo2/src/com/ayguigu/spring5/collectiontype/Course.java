package com.ayguigu.spring5.collectiontype;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/5/24
 **/
public class Course {
    private String cname;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
    @Override
    public String toString() {
        return "Course{" +
                "cname='" + cname + '\'' +
                '}';
    }
}
