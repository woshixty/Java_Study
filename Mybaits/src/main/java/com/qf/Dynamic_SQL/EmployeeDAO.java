package com.qf.Dynamic_SQL;

import com.qf.entity.Employee;
import org.apache.ibatis.annotations.Param;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/3
 * 一对多
 * 员工信息中嵌入部门信息
 **/
public interface EmployeeDAO {

    // 查询员工信息，并且查询其对应部门的信息
    Employee queryEmployeeById(@Param("id") Integer id);

    // 动态SQL查询员工信息
    Employee queryEmployee(Employee employee);

}
