app.service("articleService", function ($http) {

    // 根据id查询
    this.findOne = function (id) {
        return $http.get(basePath+"/article/get?id=" + id);
    };
    //文章列表
    this.loadArticleList = function (id) {
        return $http.get(basePath+"/index/loadArticleList");
    };

    // 浏览
    this.browse = function (articleId) {
        return $http.get(basePath+"/article/increaseBrowse?articleId=" + articleId);
    };
    // 喜欢
    this.love = function (articleId, islove) {
        return $http.put(basePath+"/article/increaseLoveNum?articleId=" + articleId + "&islove=" + islove);
    };
    // 添加评论
    this.addCommand = function (command) {
        return $http.post(basePath+"/article/command/commit", command);
    };
    // 删除评论
    this.deleteCommand = function (commandId) {
        return $http.delete(basePath+"/article/command/delete?commandId=" + commandId);
    }
});