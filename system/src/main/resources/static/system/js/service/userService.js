app.service("userService", function ($http) {
    // 条件查询
    this.search = function (page, rows, searchEntity) {
        return $http.post(basePath + "/user/list?page=" + page + "&rows=" + rows,
            searchEntity);
    };
    // 禁用
    this.disable = function (selectIds) {
        return $http.delete(basePath + "/user/nouse?ids=" + selectIds);
    };

    //查询当前用户资料
    this.loadUserInfo = function () {
        return $http.get(basePath + "/user/load");
    };

    this.modifyPassword = function (info) {
        return $http.post(basePath + "/user/modifyPassword",info);
    };
});