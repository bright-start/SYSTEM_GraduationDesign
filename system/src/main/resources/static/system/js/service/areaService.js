app.service("areaService", function ($http) {
    // 添加
    this.add = function (entity) {
        return $http.post(basePath+"/area/add", entity);
    };

    // 修改
    this.update = function (entity) {
        return $http.put(basePath+"/area/modify", entity);
    };

    // 根据id查询
    this.findOne = function (id) {
        return $http.get(basePath+"/area/get?id=" + id);
    };
    // 条件查询
    this.search = function (page, rows, searchEntity) {
        return $http.post(basePath+"/area/list?page=" + page + "&rows=" + rows,
            searchEntity);
    };
    this.findAreaList = function () {
        return $http.get(basePath+"/area/findAreaList");
    };
    // 删除
    this.dele = function (selectIds) {
        return $http.delete(basePath+"/area/delete?ids=" + selectIds);
    }
});