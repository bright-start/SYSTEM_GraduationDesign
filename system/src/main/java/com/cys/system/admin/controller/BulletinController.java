package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.InvalidRequestException;
import com.cys.system.common.pojo.Bulletin;
import com.cys.system.common.service.BulletinService;
import com.cys.system.common.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/bulletin")
public class BulletinController {

    @Autowired
    private BulletinService bulletinService;

    @PostMapping("/list")
    public Result list(Integer page, Integer rows, @RequestBody(required = false) Bulletin bulletin) throws InvalidRequestException {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        if (rows == 10 || rows == 20 || rows == 30 || rows == 40 || rows == 50) {
            return bulletinService.listBulletin(page, rows, bulletin);
        } else {
            throw new InvalidRequestException();
        }
    }

    @GetMapping("/loadBulletinList")
    public Result loadBulletinList(){
        return bulletinService.loadBulletinList();
    }

    @GetMapping("/get")
    public Result get(Integer id) {
        return bulletinService.getBulletinById(id);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody(required = true) Integer[] ids) {
        bulletinService.deleteBulletinById(ids);
        return new Result().success("删除成功");
    }

    @PostMapping("/add")
    public Result add(@RequestBody(required = true) Bulletin bulletin) {
        bulletin.setCreateTime(TimeConverter.DateToString(new Date()));
        bulletin.setUpdateTime(bulletin.getCreateTime());
        bulletinService.insertBulletin(bulletin);
        return new Result().success("新增成功");
    }

    @PutMapping("/modify")
    public Result modify(@RequestBody Bulletin bulletin) {
        return bulletinService.updateBulletinByBulletin(bulletin);
    }
}