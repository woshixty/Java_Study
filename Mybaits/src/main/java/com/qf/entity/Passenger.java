package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/30
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    private Integer id;
    private String name;
    private String sex;
    private Date birthday;

    // 存储旅客的护照信息
    private Passport passport;
}
