app.service("commentService", function ($http) {
    // 添加
    this.addCommand = function (command) {
        return $http.post("http://www.cys.com:9200/system/article/command/commit", command);
    };
    // 删除
    this.deleteCommand = function (commandId) {
        return $http.delete("http://www.cys.com:9200/system/article/command/delete?commandId=" + commandId);
    }
});