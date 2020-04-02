package com.cys.search.controller;

import com.cys.search.feign.SystemInterface;
import com.cys.search.pojo.Command;
import com.cys.search.pojo.Result;
import com.cys.search.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private SystemInterface systemInterface;

    @Autowired
    private SSOService ssoService;

    @GetMapping("/get")
    public Result getArticleById(Integer id){
        return systemInterface.getArticleById(id);
    }

    @GetMapping("/increaseBrowse")
    public Result increaseBrowseNum(@RequestParam Integer articleId, HttpServletRequest request){
        Integer userId = ssoService.getUserIdByCookie(request);
        if(userId == null) {
            return new Result().success();
        }
        return systemInterface.increaseBrowseNum(userId,articleId);
    }

    @PutMapping("/increaseLoveNum")
    public Result increaseLoveNum(@RequestParam Integer articleId,@RequestParam Integer islove,HttpServletRequest request){
        Integer userId = ssoService.getUserIdByCookie(request);
        if(userId == null) {
            return new Result().error(401,"请登录后操作");
        }
        return systemInterface.increaseLoveNum(userId,articleId,islove);
    }
    @PostMapping("/command/commit")
    public Result commitCommand(@RequestBody Command command,HttpServletRequest request){
        Integer userId = ssoService.getUserIdByCookie(request);
        if(userId == null) {
            return new Result().error(401,"请登录后操作");
        }
        command.setUserId(userId);
        return systemInterface.commitCommand(command);
    }
    @DeleteMapping("/command/delete")
    public Result deleteCommand(@RequestParam Integer commandId,HttpServletRequest request){
        Integer userId = ssoService.getUserIdByCookie(request);
        if(userId == null) {
            return new Result().error(401,"请登录后操作");
        }
        return systemInterface.deleteCommand(userId,commandId);
    }
}
