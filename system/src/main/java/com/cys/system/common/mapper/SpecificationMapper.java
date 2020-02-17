package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Brand;
import com.cys.system.common.pojo.Specification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpecificationMapper {
    List<Specification> listSpecification(Specification specification);

    Specification getSpecificationById(Integer id);

    long count(Specification specification);

    void deleteSpecificationById(@Param("id") Integer id, @Param("shopId") Integer shopId);

    void updateSpecificationBySpecification(Specification specification);

    void insertSpecification(Specification specification);

}
