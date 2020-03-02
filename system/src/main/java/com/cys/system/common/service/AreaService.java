package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.Area;

import java.io.IOException;


public interface AreaService {
    Result listArea(Integer page, Integer rows, Area area) throws IOException;

    Result getAreaById(Integer id);

    void deleteAreaByIds(Integer[] ids);

    void insertArea(Area area);

    Result updateAreaByArea(Area area);

    Result findAreaList();
}
