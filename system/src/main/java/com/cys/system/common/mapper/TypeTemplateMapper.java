package com.cys.system.common.mapper;

import com.cys.system.common.pojo.TypeTemplate;

import java.util.List;

public interface TypeTemplateMapper {
    long count(TypeTemplate typeTemplate);

    List<TypeTemplate> listTypeTemplate(TypeTemplate typeTemplate);

    void insertTypeTemplate(TypeTemplate typeTemplate);

    void updateTypeTemplateByTemplate(TypeTemplate typeTemplate);

    TypeTemplate findTypeTemplateById(Integer id);

    void deleteTypeTemplateById(Long id);
}
