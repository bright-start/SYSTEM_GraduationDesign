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
import com.cys.system.common.util.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SSOService ssoService;

    @Autowired
    private UserService userService;

    /**
     * @param page    当前页
     * @param rows    每页的行数
     * @param article 条件查询
     *                status
     *                status==-1 查询所有
     *                status==0 查询已发布的文章
     *                status==1 查询未发布的文章
     *                name 按文章标题模糊查找
     * @return
     * @throws InvalidRequestException
     * @throws IOException
     */
    @PostMapping("/list")
    public Result listArticle(Integer page, Integer rows, @RequestBody Article article) throws InvalidRequestException, IOException {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        if (article.getStatus() == -1) {
            article.setStatus(null);
        }
        if (rows == 10 || rows == 20 || rows == 30 || rows == 40 || rows == 50) {
            return articleService.listArticle(page, rows, article);
        } else {
            throw new InvalidRequestException();
        }
    }

    @GetMapping("/loadArticleList")
    public Result loadArticleList(){
        return articleService.loadArticleList();
    }

    @GetMapping("/get")
    public Result getArticleById(Integer id) {
        return articleService.getArticleById(id);
    }

    @PostMapping("/add")
    public Result addArticle(@RequestBody Article article) {
        return articleService.addArticle(article);
    }

    @DeleteMapping("/delete")
    public Result deleteArticleById(Integer[] ids) {
        return articleService.deleteArticleById(ids);
    }

    /**
     * @return
     */
    @GetMapping("/increaseBrowse")
    public Result increaseBrowseNum(Integer userId,Integer articleId) throws UnauthorizedException {
//        Map<String, Object> userMap = ssoService.getUser(request);
//        if (userMap == null && userMap.isEmpty()) {
//            throw new UnauthorizedException();
//        }
        return articleService.increaseBrowseNum(articleId, userId);
    }

    /**
     * @return
     */
    @PutMapping("/increaseLoveNum")
    public Result increaseLoveNum(Integer userId, Integer articleId, Integer islove) throws UnauthorizedException {
//        Map<String, Object> userMap = ssoService.getUser(request);
//        if (userMap == null && userMap.isEmpty()) {
//            throw new UnauthorizedException();
//        }
        return articleService.increaseLoveNum(articleId, userId, islove);
    }

    /**
     * @return
     */
    @PostMapping("/command/commit")
    public Result commitCommand(@RequestBody Command command) throws Exception {
//        Map<String, Object> userMap = ssoService.getUser(request);
//        if (userMap == null && userMap.isEmpty()) {
//            throw new UnauthorizedException();
//        }
//        command.setUserId(userId);
        command.setCreateTime(TimeConverter.getInstance().DateToString(new Date(), TimeFormat.Y_M_D_H_M_S));
        return articleService.commitCommand(command);
    }

    @DeleteMapping("/command/delete")
    public Result deleteCommand(Integer userId, Integer commandId) throws UnauthorizedException {
//        Map<String, Object> userMap = ssoService.getUser(request);
//        if (userMap == null && userMap.isEmpty()) {
//            throw new UnauthorizedException();
//        }
        return articleService.deleteCommand(commandId, userId);
    }

}
