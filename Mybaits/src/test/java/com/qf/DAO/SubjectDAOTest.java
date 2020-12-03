package com.qf.DAO;

import com.qf.entity.Subject;
import com.qf.util.MybaitsUtil;
import org.junit.jupiter.api.Test;


/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/3
 **/
class SubjectDAOTest {

    @Test
    void querySubjectById() {
        SubjectDAO subjectDAO = MybaitsUtil.getMapper(SubjectDAO.class);
        Subject subject = subjectDAO.querySubjectById(1001);
        System.out.println(subject);
    }
}