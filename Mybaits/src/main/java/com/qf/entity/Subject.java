package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/3
 * 多对多关系
 * 课程表
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    //课程基本信息
    private Integer id;
    private String name;
    private Integer grade;
    //储存学生信息
    private List<Student> students;
}
