package com.xty.demo.mybaitsPlus;

import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/12
 **/

@Data
public class User {

    @Id
    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Long managerId;

    private LocalDateTime createTime;

}
