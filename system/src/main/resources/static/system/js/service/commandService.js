app.service("bulletinService", function ($http) {
    // 添加
    this.add = function (command) {
        return $http.post("http://127.0.0.1:8080/system/article/command/commit", command);
    };
    // 删除
    this.dele = function (id) {
        return $http.delete("http://127.0.0.1:8080/system/article/command/delete=" + id);
    }
});