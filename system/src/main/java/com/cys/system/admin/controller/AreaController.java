package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.InvalidRequestException;
import com.cys.system.common.pojo.Area;
import com.cys.system.common.pojo.Brand;
import com.cys.system.common.service.AreaService;
import com.cys.system.common.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @PostMapping("/list")
    public Result list(Integer page, Integer rows, @RequestBody(required = false) Area area) throws InvalidRequestException, IOException {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        if (rows == 10 || rows == 20 || rows == 30 || rows == 40 || rows == 50) {
            return areaService.listArea(page, rows, area);
        } else {
            throw new InvalidRequestException();
        }
    }

    @GetMapping("/get")
    public Result get(Integer id) {
        return areaService.getAreaById(id);
    }

    @DeleteMapping("/delete")
    public Result delete(Integer[] ids) {
        areaService.deleteAreaByIds(ids);
        return new Result().success("删除成功");
    }

    @PostMapping("/add")
    public Result add(@RequestBody(required = true) Area area) {
        areaService.insertArea(area);
        return new Result().success("新增成功");
    }

    @PutMapping("/modify")
    public Result modify(@RequestBody Area area) {
        return areaService.updateAreaByArea(area);
    }
}