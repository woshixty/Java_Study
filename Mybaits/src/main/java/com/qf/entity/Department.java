package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/2
 * 一对多
 * 一个部门对应多个员工
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    private Integer id;
    private String name;
    private String location;

    //部门中所有员工的列表，多个员工
    private List<Employee> employees;

}
