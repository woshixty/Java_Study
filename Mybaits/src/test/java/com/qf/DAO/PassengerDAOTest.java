package com.qf.DAO;

import com.qf.entity.Passenger;
import com.qf.util.MybaitsUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/1
 **/
class PassengerDAOTest {

    @Test
    void queryPassengerById() {

        PassengerDAO passengerDAO = MybaitsUtil.getMapper(PassengerDAO.class);
        Passenger passenger = passengerDAO.queryPassengerById(1);
        System.out.println(passenger);

    }
}