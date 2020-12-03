package com.qf.DAO;

import com.qf.entity.Department;
import org.apache.ibatis.annotations.Param;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/2
 **/
public interface DepartmentDAO {

    //查询部门及其所有员工
    Department QueryDepartmentById(@Param("id") Integer id);
}
