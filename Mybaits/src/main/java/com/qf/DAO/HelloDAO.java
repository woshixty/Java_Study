package com.qf.DAO;

import com.qf.entity.Hello;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/21
 **/

public interface HelloDAO {

    Integer selectNum();

    Hello selectByName(@Param("name") String name);

    /**
     * 主键回填
     * 添加一个东西之后将主键返回
     */
    Integer insert(Hello hello);
}
