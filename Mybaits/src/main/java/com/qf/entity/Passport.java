package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/30
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passport {

    private Integer id;
    private String nationality;
    private Date expire;

    // 存储旅客信息
    private Passenger passenger;
}
