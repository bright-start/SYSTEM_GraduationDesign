package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.Brand;
import com.cys.system.common.pojo.TypeTemplate;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService {

    Result add(TypeTemplate typeTemplate);

    Result update(TypeTemplate typeTemplate);

    Result findOne(Integer id);

    Result delete(Long[] ids);

    Result listTypeTemplate(TypeTemplate typeTemplate, Integer page, Integer rows);

    List<Map> selectOptionList(Integer shopId);

    List<Map> selectBrandList();
}
