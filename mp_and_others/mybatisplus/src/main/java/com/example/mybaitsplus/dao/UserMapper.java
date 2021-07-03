package com.example.mybaitsplus.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.mybaitsplus.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author qyyzxty@icloud.com
 * 2020/10/14
 **/

@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 使用条件构造自定义 sql 语句
     *
     * 也可以将代码写在 xml yml 等配置文件中，让代码耦合度降低
     *
     */

    @Select("select * from user ${ew.customSqlSegment}")
    List<User> selectAll(@Param(Constants.WRAPPER) Wrapper<User> wrapper);


}
