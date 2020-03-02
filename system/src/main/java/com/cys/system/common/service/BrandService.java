package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.Brand;
import com.cys.system.common.pojo.Bulletin;

import java.io.IOException;


public interface BrandService {
    Result listBrand(Integer page, Integer rows, Brand brand) throws IOException;

    Result getBrandById(Integer id);

    void deleteBrandByIds(Integer[] ids);

    void insertBrand(Brand brand);

    Result updateBrandByBrand(Brand brand);

    Result findBrandList();
}
