app.service("userService", function ($http) {
    // 条件查询
    this.search = function (page, rows, searchEntity) {
        return $http.post(basePath + "/user/list?page=" + page + "&rows=" + rows,
            searchEntity);
    };
    // 禁用
    this.disable = function (selectIds) {
        return $http.delete(basePath + "/user/nouse?ids=" + selectIds);
    }
});