package com.xty.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xty.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/7
 **/

@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user where name = #{name}")
    List<User> selectByName(@Param("name") String name);
}
