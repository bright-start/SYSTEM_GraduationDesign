package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Article;
import com.cys.system.common.pojo.TimeTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    List<Article> listArticle(Article article);

    Article getArticleById(Integer id);

    long count(Article article);

    void addArticle(Article article);

    void deleteArticleById(Integer id);

    void increaseBrowseNum(Integer id);

    void increaseLoveNum(Integer id);

    void updateStatusById(@Param("id") Integer id, @Param("releaseTime") String releaseTime);

    List<TimeTask> listTimeTask();

    void decreaseLoveNum(Integer id);

    List<Article> loadArticleList();
}
