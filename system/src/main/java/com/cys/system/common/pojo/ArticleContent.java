package com.cys.system.common.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 文章内容展示
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleContent {
    private Article article;
    private List<CommandContent> commandContentList;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<CommandContent> getCommandContentList() {
        return commandContentList;
    }

    public void setCommandContentList(List<CommandContent> commandContentList) {
        this.commandContentList = commandContentList;
    }

    public static class CommandContent {
        private Command command;
        private User commandUser;
        private User responseUser;

        public Command getCommand() {
            return command;
        }

        public void setCommand(Command command) {
            this.command = command;
        }

        public User getCommandUser() {
            return commandUser;
        }

        public void setCommandUser(User commandUser) {
            this.commandUser = commandUser;
        }

        public User getResponseUser() {
            return responseUser;
        }

        public void setResponseUser(User responseUser) {
            this.responseUser = responseUser;
        }
    }

}

