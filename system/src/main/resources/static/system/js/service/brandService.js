app.service("brandService", function ($http) {
    // 添加
    this.add = function (entity) {
        return $http.post(basePath+"/brand/add", entity);
    };

    // 修改
    this.update = function (entity) {
        return $http.put(basePath+"/brand/modify", entity);
    };

    // 根据id查询
    this.findOne = function (id) {
        return $http.get(basePath+"/brand/get?id=" + id);
    };
    // 条件查询
    this.search = function (page, rows, searchEntity) {
        return $http.post(basePath+"/brand/list?page=" + page + "&rows=" + rows,
            searchEntity);
    };
    this.findBrandList = function(){
        return $http.get(basePath + "/brand/findBrandList");
    }
    // 删除
    this.dele = function (selectIds) {
        return $http.delete(basePath+"/brand/delete?ids=" + selectIds);
    }
});