package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.UnauthorizedException;
import com.cys.system.common.pojo.Specification;

import java.io.IOException;


public interface SpecificationService {
    Result listSpecification(Integer page, Integer rows, Specification specification) throws IOException;

    Result getSpecificationById(Integer id);

    void deleteSpecificationByIds(Integer[] ids,Integer shopId);

    void insertSpecification(Specification specification);

    Result updateSpecificationBySpecification(Specification specification,Integer shopId) throws UnauthorizedException;

    Result findSpecificationList(Integer shopId);
}
