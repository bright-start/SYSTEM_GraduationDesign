package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.Article;
import com.cys.system.common.pojo.Command;
import com.cys.system.common.pojo.TimeTask;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.IOException;
import java.util.List;

public interface ArticleService {
    Result listArticle(Integer page, Integer rows, Article article) throws IOException;

    Result getArticleById(Integer id);

    Result addArticle(Article article);

    Result deleteArticleById(Integer[] ids);

    Result increaseBrowseNum(Integer articleId, Integer userId);

    Result increaseLoveNum(Integer articleId, Integer userId, Integer islove);

    Result commitCommand(Command command) throws Exception;

    Result deleteCommand(Integer commandId, Integer userId);

    void updateStatusById(Integer id);

    List<TimeTask> listTimeTask();

    Result loadArticleList();
}