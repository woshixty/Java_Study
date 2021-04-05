package com.qianfeng.openapi.web.master.mapper;

import com.qianfeng.openapi.web.master.pojo.Recharge;

import java.util.List;
/**
*  @author author
*/
public interface RechargeMapper {

    int insertRecharge(Recharge object);

    int updateRecharge(Recharge object);

    int update(Recharge.UpdateBuilder object);

    List<Recharge> queryRecharge(Recharge object);

    Recharge queryRechargeLimit1(Recharge object);

    Recharge getRechargeMapById(int id);

    List<Recharge> getAllRecharges();
}