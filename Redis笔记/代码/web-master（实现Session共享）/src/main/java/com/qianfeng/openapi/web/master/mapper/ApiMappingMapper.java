package com.qianfeng.openapi.web.master.mapper;

import com.qianfeng.openapi.web.master.pojo.ApiMapping;

import java.util.List;

public interface ApiMappingMapper {

    void addApiMapping(ApiMapping mapping);

    void updateApiMapping(ApiMapping mapping);

    List<ApiMapping> getMappingList(ApiMapping criteria);

    ApiMapping getMappingById(int id);

}
