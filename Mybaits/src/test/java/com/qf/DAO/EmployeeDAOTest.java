package com.qf.DAO;

import com.qf.entity.Employee;
import com.qf.util.MybaitsUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/3
 **/
class EmployeeDAOTest {

    @Test
    void queryEmployeeById() {
        EmployeeDAO employeeDAO = MybaitsUtil.getMapper(EmployeeDAO.class);
        Employee employee = employeeDAO.queryEmployeeById(2);
        System.out.println(employee);
    }
}