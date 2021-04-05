package com.qf.dao;

import com.qf.entity.Passport;
import org.apache.ibatis.annotations.Param;

public interface PassportDAO {


    Passport queryPassportById(@Param("id") Integer id);
}
