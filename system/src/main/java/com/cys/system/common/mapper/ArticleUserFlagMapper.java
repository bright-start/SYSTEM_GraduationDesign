package com.cys.system.common.mapper;

import org.apache.ibatis.annotations.Param;

public interface ArticleUserFlagMapper {
    void addContact(@Param("articleId") Integer articleId, @Param("userId") Integer userId);

    void updateContact(@Param("articleId") Integer articleId, @Param("userId") Integer userId, @Param("islove") Integer islove);

    void deleteContact(Integer articleId);

    Integer isExistContact(@Param("articleId") Integer articleId, @Param("userId") Integer userId);
}
