package com.qianfeng.openapi.web.master.mapper;

import com.qianfeng.openapi.web.master.pojo.ApiSystemparam;

import java.util.List;
/**
*  @author author
*/
public interface ApiSystemparamMapper {

    int insertApiSystemparam(ApiSystemparam object);

    int updateApiSystemparam(ApiSystemparam object);

    int update(ApiSystemparam.UpdateBuilder object);

    List<ApiSystemparam> queryApiSystemparam(ApiSystemparam object);

    ApiSystemparam queryApiSystemparamLimit1(ApiSystemparam object);

    ApiSystemparam getMappingById(int id);
}