package com.example.mybaitsplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/14
 **/

@Data
@TableName("user")
public class User {

    @Id
    @TableId    //指定主键
    private Long id;

    @TableField("name")    //指定表中对应字段的名字
    private String name;

    private Integer age;

    private String email;

    private Long managerId;

    private LocalDateTime createTime;

    //备注  transient表示不参与·序列化过程
    @TableField(exist = false)    //说明不是数据库中存在的数据
    private transient String remark;

}
