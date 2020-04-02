package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Article;
import com.cys.system.common.pojo.TimeTask;

import java.util.List;

public interface ArticleMapper {
    List<Article> listArticle(Article article);

    Article getArticleById(Integer id);

    long count(Article article);

    void addArticle(Article article);

    void deleteArticleById(Integer id);

    void increaseBrowseNum(Integer id);

    void increaseLoveNum(Integer id);

    void updateStatusById(Integer id);

    List<TimeTask> listTimeTask();

    void decreaseLoveNum(Integer id);

    List<Article> loadArticleList();
}
