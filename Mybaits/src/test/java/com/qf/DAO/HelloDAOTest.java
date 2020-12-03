package com.qf.DAO;

import com.qf.util.MybaitsUtil;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/22
 **/

class HelloDAOTest {

    @org.junit.jupiter.api.Test
    void selectNum() {
        HelloDAO helloDAO = MybaitsUtil.getMapper(HelloDAO.class);
        System.out.println(helloDAO.selectNum());
    }
}