package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/3
 * 多对多关系映射
 * 学生
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student2 {
    //学生基本信息
    private Integer id;
    private String name;
    private String sex;

    //存储学生们
    private List<Subject> subjects;
}
