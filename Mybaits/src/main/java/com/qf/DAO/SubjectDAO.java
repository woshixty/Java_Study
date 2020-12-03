package com.qf.DAO;

import com.qf.entity.Subject;
import org.apache.ibatis.annotations.Param;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/3
 * 多对多关系查询
 **/
public interface SubjectDAO {
    Subject querySubjectById(@Param("id") Integer id);
}
