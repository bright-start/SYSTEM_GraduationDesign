package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Brand;
import com.cys.system.common.pojo.Bulletin;
import com.cys.system.common.pojo.TimeTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandMapper {
    List<Brand> listBrand(Brand brand);

    Brand getBrandById(Integer id);

    long count(Brand brand);

    void deleteBrandById(Integer id);

    void updateBrandByBrand(Brand brand);

    void insertBrand(Brand brand);

}
