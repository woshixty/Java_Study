package com.qf.DAO;

import com.qf.entity.Department;
import com.qf.entity.Employee;
import com.qf.util.MybaitsUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/2
 **/
class DepartmentDAOTest {

    @Test
    void queryDepartmentById() {
        DepartmentDAO departmentDAO = MybaitsUtil.getMapper(DepartmentDAO.class);
        Department department = departmentDAO.QueryDepartmentById(1);
        System.out.println(department);
        List<Employee> employees = department.getEmployees();
        employees.forEach(System.out::println);
    }
}