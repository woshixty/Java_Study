package com.qf.DAO;

import com.qf.entity.Passenger;
import org.apache.ibatis.annotations.Param;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/1
 **/
public interface PassengerDAO {

    // 通过旅客id 查询旅客信息及其护照信息 关联查询 级联查询
    Passenger queryPassengerById(@Param("id") Integer id);

}
