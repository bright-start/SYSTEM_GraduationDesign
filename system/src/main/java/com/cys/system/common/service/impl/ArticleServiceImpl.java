package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.mapper.ArticleMapper;
import com.cys.system.common.mapper.ArticleUserFlagMapper;
import com.cys.system.common.mapper.CommandMapper;
import com.cys.system.common.mapper.UserMapper;
import com.cys.system.common.pojo.*;
import com.cys.system.common.service.ArticleService;
import com.cys.system.common.util.TimeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true, rollbackFor = {Exception.class})
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleUserFlagMapper articleUserFlagMapper;

    @Resource
    private CommandMapper commandMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Transactional(readOnly = false)
    @Override
    public void updateStatusById(Integer id) {
        articleMapper.updateStatusById(id);
    }

    @Override
    public List<TimeTask> listTimeTask() {
        List<TimeTask> timeTasks = articleMapper.listTimeTask();
        if (timeTasks != null && !timeTasks.isEmpty()) {
            return timeTasks;
        }
        return null;
    }

    @Override
    public Result listArticle(Integer page, Integer rows, Article article) throws IOException {

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        // 缓存层
        String json2 = (String) redisTemplate.opsForHash().get("article",
                "page:" + page + "-rows:" + rows + ";" + article.getStatus() + "&" + article.getEnableTimeTask() + "&" + article.getName());
        PageInfo<Article> articleList = null;
        if (json2 == null) {
            synchronized (this) {
                json2 = (String) redisTemplate.opsForHash().get("article",
                        "page:" + page + "-rows:" + rows + ";" + article.getStatus() + "&" + article.getEnableTimeTask() + "&" + article.getName());
                if (json2 == null) {
                    long totalCount = articleMapper.count(article);
                    if (totalCount > 0) {
                        int start = (page - 1) * rows;
                        PageHelper.startPage(start, rows);
                        List<Article> articles = articleMapper.listArticle(article);
                        if (articles != null && !articles.isEmpty()) {
                            PageInfo<Article> pageInfo = new PageInfo<>();
                            pageInfo.setTotal(totalCount);
                            pageInfo.setList(articles);
                            redisTemplate.opsForHash().put("article",
                                    "page:" + page + "-rows:" + rows + ";" + article.getStatus() + "&" + article.getEnableTimeTask() + "&" + article.getName(),
                                    objectMapper.writeValueAsString(pageInfo));
                            Random random = new Random();
                            int i = random.nextInt() * 10;
                            redisTemplate.expire("article", 24 * 60 * 60 + i, TimeUnit.SECONDS);
                            return new Result().success(pageInfo);
                        }
                    } else {
                        return new Result().success("无数据");
                    }
                }
            }
        }
        Random random = new Random();
        int i = random.nextInt() * 10;
        redisTemplate.expire("article", 24 * 60 * 60 + i, TimeUnit.SECONDS);

        PageInfo<Article> pageInfo = objectMapper.readValue(json2, PageInfo.class);
        return new Result().success(pageInfo);
    }

    @Override
    public Result getArticleById(Integer id) {

        Article article = articleMapper.getArticleById(id);
        if (article == null) {
            return new Result().success("无数据");
        }
        ArticleContent articleContent = new ArticleContent();
        articleContent.setArticle(article);

        List<ArticleContent.CommandContent> commandContentList = new LinkedList<>();
        List<Command> commandList = commandMapper.getCommandByArticleId(id);
        for (Command command : commandList) {

            ArticleContent.CommandContent commandContent = new ArticleContent.CommandContent();
            commandContent.setCommand(command);

            User commandUser = userMapper.getUserById(command.getUserId());
            clearSensitiveInfoOfUser(commandUser);
            commandContent.setCommandUser(commandUser);

            Integer responseUserId = command.getResponseUserId();
            if (responseUserId != null) {
                User responseUser = userMapper.getUserById(responseUserId);
                clearSensitiveInfoOfUser(responseUser);
                commandContent.setResponseUser(responseUser);
            }

            commandContentList.add(commandContent);
        }
        articleContent.setCommandContentList(commandContentList);

        return new Result().success(articleContent);
    }

    private void clearSensitiveInfoOfUser(User user) {
        user.setPassword(null);
        user.setStatus(null);
        user.setBindPhone(null);
        user.setRoleId(null);
        user.setLevel(null);
        user.setDisableTime(null);
    }

    @Transactional(readOnly = false)
    @Override
    public Result addArticle(Article article) {
        //参数校验
        try {
            checkArticle(article);
        } catch (Exception e) {
            return new Result().success(e.getMessage());
        }

        article.setCreateTime(TimeConverter.DateToString(new Date()));
        article.setBrowseNum(0);
        article.setLoveNum(0);
        article.setStatus(0);
        if (article.getEnableTimeTask() == 0) {
            article.setTimeTask("-");
        }
        articleMapper.addArticle(article);
        redisTemplate.delete("article");
        return new Result().success();
    }

    private void checkArticle(Article article) throws Exception {
        if (article.getAuthor() == null && "".equals(article.getAuthor())) {
            throw new Exception("作者不能为空");
        }
        if (article.getName() == null && "".equals(article.getName())) {
            throw new Exception("文章标题不能为空");
        }
        if (article.getContent() == null && "".equals(article.getContent())) {
            throw new Exception("文章内容不能为空");
        }
    }

    @Transactional(readOnly = false)
    @Override
    public Result deleteArticleById(Integer[] ids) {
        for (Integer id : ids) {
            articleMapper.deleteArticleById(id);
            commandMapper.deleteCommandByArticleId(id);
            articleUserFlagMapper.deleteContact(id);
        }
        return new Result().success();
    }

    @Transactional(readOnly = false)
    @Override
    public Result increaseBrowseNum(Integer articleId, Integer userId) {
        Integer isExist = articleUserFlagMapper.isExistContact(articleId, userId);
        if (isExist == null) {
            articleUserFlagMapper.addContact(articleId, userId);
            articleMapper.increaseBrowseNum(articleId);
            return new Result().success(true);
        }
        return new Result().success(false);
    }

    @Transactional(readOnly = false)
    @Override
    public Result increaseLoveNum(Integer articleId, Integer userId, Integer islove) {
        articleUserFlagMapper.updateContact(articleId, userId, islove);
        if(islove == 1) {
            articleMapper.increaseLoveNum(articleId);
        }else {
            articleMapper.decreaseLoveNum(articleId);
        }
        return new Result().success(islove == 1 ? true : false);
    }

    @Transactional(readOnly = false)
    @Override
    public Result commitCommand(Command command) throws Exception {
        commandMapper.addCommand(command);
        Command commandRes = commandMapper.getCommandByCommandId(command.getCommandId());
        if (commandRes == null) {
            throw new Exception("添加评论失败");
        }
        ArticleContent.CommandContent commandContent = new ArticleContent.CommandContent();

        commandContent.setCommand(commandRes);

        User commandUser = userMapper.getUserById(command.getUserId());
        clearSensitiveInfoOfUser(commandUser);
        commandContent.setCommandUser(commandUser);

        Integer responseUserId = command.getResponseUserId();
        if (responseUserId != null) {
            User responseUser = userMapper.getUserById(responseUserId);
            clearSensitiveInfoOfUser(responseUser);
            commandContent.setResponseUser(responseUser);
        }
        return new Result().success(commandContent);
    }

    @Transactional(readOnly = false)
    @Override
    public Result deleteCommand(Integer commandId, Integer userId) {
        commandMapper.deleteCommand(commandId, userId);
        return new Result().success();
    }
}
