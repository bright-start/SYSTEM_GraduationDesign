package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.InvalidRequestException;
import com.cys.system.common.exception.UnauthorizedException;
import com.cys.system.common.pojo.Brand;
import com.cys.system.common.pojo.Specification;
import com.cys.system.common.service.BrandService;
import com.cys.system.common.service.SSOService;
import com.cys.system.common.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    @Autowired
    private SSOService ssoService;

    @PostMapping("/list")
    public Result list(Integer page, Integer rows, @RequestBody(required = false) Specification specification) throws InvalidRequestException, IOException {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        if (rows == 10 || rows == 20 || rows == 30 || rows == 40 || rows == 50) {
            return specificationService.listSpecification(page, rows, specification);
        } else {
            throw new InvalidRequestException();
        }
    }

    @GetMapping("/findSpecificationList")
    public Result findSpecificationList(HttpServletRequest request) throws UnauthorizedException {
        Map<String, Object> userMap = ssoService.getUser(request);
        if (userMap == null && userMap.isEmpty()) {
            throw new UnauthorizedException();
        }
        Integer shopId = (Integer) userMap.get("shopId");
        return specificationService.findSpecificationList(shopId);
    }

    @GetMapping("/get")
    public Result get(Integer id) {
        return specificationService.getSpecificationById(id);
    }

    @DeleteMapping("/delete")
    public Result delete(Integer[] ids,HttpServletRequest request) throws UnauthorizedException {
        Map<String, Object> userMap = ssoService.getUser(request);
        if (userMap == null && userMap.isEmpty()) {
            throw new UnauthorizedException();
        }
        List<Map> list = (List<Map>)userMap.get("authorities");
        String role = (String)list.get(0).get("role");
        if(role.contains("SHOP")) {
            Integer shopId = (Integer) userMap.get("shopId");
            specificationService.deleteSpecificationByIds(ids,shopId);
        }else{
            specificationService.deleteSpecificationByIds(ids,null);
        }
        return new Result().success("删除成功");
    }

    @PostMapping("/add")
    public Result add(@RequestBody(required = true) Specification specification,HttpServletRequest request) throws UnauthorizedException {
        Map<String, Object> userMap = ssoService.getUser(request);
        if (userMap == null && userMap.isEmpty()) {
            throw new UnauthorizedException();
        }
        List<Map> list = (List<Map>)userMap.get("authorities");
        String role = (String)list.get(0).get("role");
        if(role.contains("SHOP")) {
            Integer shopId = (Integer) userMap.get("shopId");
            specification.setIsCustom(1);
            specification.setShopId(shopId);
            specificationService.insertSpecification(specification);
        }else{
            specificationService.insertSpecification(specification);
        }

        return new Result().success("新增成功");
    }

    @PutMapping("/modify")
    public Result modify(@RequestBody Specification specification, HttpServletRequest request) throws UnauthorizedException {
        Map<String, Object> userMap = ssoService.getUser(request);
        if (userMap == null && userMap.isEmpty()) {
            throw new UnauthorizedException();
        }
        List<List<Map>> list = (List<List<Map>>)userMap.get("authorities");
        String role = (String)list.get(0).get(0).get("role");
        if(role.contains("SHOP")) {
            Integer shopId = (Integer) userMap.get("shopId");
            return specificationService.updateSpecificationBySpecification(specification, shopId);
        }else {
            return specificationService.updateSpecificationBySpecification(specification, null);
        }
    }
}