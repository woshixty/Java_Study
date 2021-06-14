package com.xty.demo.entity;

import lombok.Data;
import javax.persistence.Id;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/7
 **/
@Data
public class User {
    @Id
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String passwd;
}
