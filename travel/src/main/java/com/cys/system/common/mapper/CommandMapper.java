package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Command;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommandMapper {
    Integer addCommand(Command command);
    void deleteCommand(@Param("commandId") Integer commandId,@Param("userId") Integer userId);
    void deleteCommandByArticleId(Integer articleId);
    Command getCommandByCommandId(Integer commandId);
    List<Command> getCommandByArticleId(@Param("articleId") Integer articleId);
}
