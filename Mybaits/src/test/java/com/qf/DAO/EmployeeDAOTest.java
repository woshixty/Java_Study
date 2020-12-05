package com.qf.DAO;

import com.qf.Dynamic_SQL.EmployeeDAO;
import com.qf.entity.Employee;
import com.qf.util.MybaitsUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/3
 **/
class EmployeeDAOTest {

    @Test
    void queryEmployeeById() {
        EmployeeDAO employeeDAO = MybaitsUtil.getMapper(EmployeeDAO.class);
        Employee employee = new Employee();
        employee.setId(2);
        employee = employeeDAO.queryEmployee(employee);
        System.out.println(employee);
    }

    @Test
    void delete() {
        EmployeeDAO employeeDAO = MybaitsUtil.getMapper(EmployeeDAO.class);
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(3);
        ids.add(4);
        System.out.println(employeeDAO.deleteManyHello(ids));;
        MybaitsUtil.commit();
    }
}