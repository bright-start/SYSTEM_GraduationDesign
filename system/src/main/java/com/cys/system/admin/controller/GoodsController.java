package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.InvalidRequestException;
import com.cys.system.common.exception.UnauthorizedException;
import com.cys.system.common.pojo.Goods;
import com.cys.system.common.service.GoodsService;
import com.cys.system.common.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SSOService ssoService;

    @PostMapping("/search")
    public Result search(Integer page, Integer rows, @RequestBody Goods goods) throws InvalidRequestException {

        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        if (rows == 10 || rows == 20 || rows == 30 || rows == 40 || rows == 50) {
            return goodsService.listGoods(page, rows, goods);
        } else {
            throw new InvalidRequestException();
        }
    }

    @GetMapping("/findOne")
    public Result getGoodsById(@RequestParam(required = true) Integer id) {
        return goodsService.findGoodsById(id);
    }

    @PutMapping("/examine")
    public Result examine(@RequestParam(required = true) Integer id, @RequestParam(required = true) Integer status
            , @RequestParam(required = false) String examineErrorMsg, HttpServletRequest request) throws UnauthorizedException {

        Map<String, Object> userMap = ssoService.getUser(request);
        if (userMap != null && !userMap.isEmpty()) {
            if (status == 2 || status == 3) {
                List list = (List) userMap.get("authorities");
                if (list != null && !list.isEmpty()) {
                    Map map = (Map) list.get(0);
                    if (map != null && !map.isEmpty()) {
                        String role = (String) map.get("role");
                        if (role != null && role.contains("ADMIN")) {
                            return goodsService.examine(id, status);
                        }
                    }
                }
            } else {
                return goodsService.examine(id, status);
            }
        }
        throw new UnauthorizedException();
    }

    @PutMapping("/examineSelected")
    public Result examine(@RequestParam(required = true) Integer[] ids, @RequestParam(required = true) Integer status
            , @RequestParam(required = false) String examineErrorMsg, HttpServletRequest request) throws UnauthorizedException {

        Map<String, Object> userMap = ssoService.getUser(request);
        if (userMap != null && !userMap.isEmpty()) {
            if (status == 2 || status == 3) {
                List list = (List) userMap.get("authorities");
                if (list != null && !list.isEmpty()) {
                    Map map = (Map) list.get(0);
                    if (map != null && !map.isEmpty()) {
                        String role = (String) map.get("role");
                        if (role != null && role.contains("ADMIN")) {
                            return goodsService.examineSelected(ids, status);
                        }
                    }
                }
            } else {
                return goodsService.examineSelected(ids, status);
            }
        }
        throw new UnauthorizedException();
    }
}
