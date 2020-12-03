package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/2
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Integer id;
    private String name;
    private String salary;

    //员工从属部门的信息
    private Department department;
}
