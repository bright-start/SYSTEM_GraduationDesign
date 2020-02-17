package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Area;
import com.cys.system.common.pojo.Brand;

import java.util.List;

public interface AreaMapper {
    List<Area> listArea(Area area);

    Area getAreaById(Integer id);

    long count(Area area);

    void deleteAreaById(Integer id);

    void updateAreaByArea(Area area);

    void insertArea(Area area);
}
