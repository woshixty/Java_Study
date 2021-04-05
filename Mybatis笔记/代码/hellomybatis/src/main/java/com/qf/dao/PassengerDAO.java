package com.qf.dao;

import com.qf.entity.Passenger;
import org.apache.ibatis.annotations.Param;

public interface PassengerDAO {
    Passenger queryPassengerById(@Param("id") Integer id);
}
