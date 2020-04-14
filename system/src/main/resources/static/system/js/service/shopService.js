app.service("shopService", function ($http) {
    // 条件查询
    this.search = function (page, rows, searchEntity) {
        return $http.post(basePath + "/shop/apply/list?page=" + page + "&rows=" + rows,
            searchEntity);
    };
    this.apply = function (ids,status) {
        return $http.get(basePath + "/shop/examine?ids="+ids +"&status=" + status);
    };
});