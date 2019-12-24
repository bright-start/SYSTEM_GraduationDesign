package com.cys.system.admin.controller;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.InvalidRequestException;
import com.cys.system.common.exception.UnauthorizedException;
import com.cys.system.common.pojo.Article;
import com.cys.system.common.pojo.Command;
import com.cys.system.common.pojo.User;
import com.cys.system.common.service.ArticleService;
import com.cys.system.common.service.SSOService;
import com.cys.system.common.service.UserService;
import com.cys.system.common.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SSOService ssoService;

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    public Result listArticle(Integer page, Integer rows, @RequestBody Article article) throws InvalidRequestException, IOException {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        if(rows == 10 || rows == 20 || rows == 30 || rows == 40|| rows == 50){
            return articleService.listArticle(page,rows,article);
        }else {
            throw new InvalidRequestException();
        }
    }

    @GetMapping("/{articleId}")
    public Result getArticleById(@PathVariable(value = "articleId") Integer id){
        return articleService.getArticleById(id);
    }

    @PostMapping("/add")
    public Result addArticle(@RequestBody Article article){
        return articleService.addArticle(article);
    }

    @DeleteMapping("/delete")
    public Result deleteArticleById(Integer[] ids){
        return articleService.deleteArticleById(ids);
    }

    /**
     *
     * @param token 用户标识信息
     * @return
     */
    @PutMapping("/increaseBrowse")
    public Result increaseBrowseNum(String token,Integer articleId) throws UnauthorizedException {
        User user = ssoService.getUserByToken(token);
        if(user == null){
            throw new UnauthorizedException();
        }
        return articleService.increaseBrowseNum(articleId,user.getId());
    }

    /**
     *
     * @param token 用户标识信息
     * @return
     */
    @PostMapping("/increaseLoveNum")
    public Result increaseLoveNum(String token,Integer articleId,Integer islove) throws UnauthorizedException {
        User user = ssoService.getUserByToken(token);
        if(user == null){
            throw new UnauthorizedException();
        }
        return articleService.increaseLoveNum(articleId,user.getId(),islove);
    }

    /**
     *
     * @param token 用户标识信息
     * @return
     */
    @PostMapping("/command/commit")
    public Result commitCommand(String token,@RequestBody Command command) throws UnauthorizedException {
        User user = ssoService.getUserByToken(token);
        if(user == null){
            throw new UnauthorizedException();
        }
        command.setUserId(user.getId());
        command.setCreateTime(TimeConverter.DateToString(new Date()));
        return articleService.commitCommand(command);
    }

    @DeleteMapping("/command/delete")
    private Result deleteCommand(String token,Integer commandId) throws UnauthorizedException {
        User user = ssoService.getUserByToken(token);
        if(user == null){
            throw new UnauthorizedException();
        }
        return articleService.deleteCommand(commandId,user.getId());
    }

}
