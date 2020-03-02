package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.InvalidRequestException;
import com.cys.system.common.pojo.Brand;
import com.cys.system.common.pojo.Bulletin;
import com.cys.system.common.service.BrandService;
import com.cys.system.common.service.BulletinService;
import com.cys.system.common.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/list")
    public Result list(Integer page, Integer rows, @RequestBody(required = false) Brand brand) throws InvalidRequestException, IOException {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        if (rows == 10 || rows == 20 || rows == 30 || rows == 40 || rows == 50) {
            return brandService.listBrand(page, rows, brand);
        } else {
            throw new InvalidRequestException();
        }
    }

    @GetMapping("/findBrandList")
    public Result findBrandList(){
        return brandService.findBrandList();
    }

    @GetMapping("/get")
    public Result get(Integer id) {
        return brandService.getBrandById(id);
    }

    @DeleteMapping("/delete")
    public Result delete(Integer[] ids) {
        brandService.deleteBrandByIds(ids);
        return new Result().success("删除成功");
    }

    @PostMapping("/add")
    public Result add(@RequestBody(required = true) Brand brand) {
        brandService.insertBrand(brand);
        return new Result().success("新增成功");
    }

    @PutMapping("/modify")
    public Result modify(@RequestBody Brand brand) {
        return brandService.updateBrandByBrand(brand);
    }
}