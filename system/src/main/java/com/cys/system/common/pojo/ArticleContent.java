package com.cys.system.common.pojo;


import java.util.List;

/**
 * 文章内容展示
 */
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
        private User user;

        public Command getCommand() {
            return command;
        }

        public void setCommand(Command command) {
            this.command = command;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

}

