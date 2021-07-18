package com.ayguigu.spring5.collectiontype;

import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/5/24
 * 八集合注入部分提取出来
 **/
public class Book {
    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
