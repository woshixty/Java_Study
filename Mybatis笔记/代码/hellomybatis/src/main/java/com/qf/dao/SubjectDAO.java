package com.qf.dao;

import com.qf.entity.Subject;
import org.apache.ibatis.annotations.Param;

public interface SubjectDAO {

    Subject querySubjectById(@Param("id") Integer id);
}
