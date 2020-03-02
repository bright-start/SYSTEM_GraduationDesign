app.service("specificationService", function ($http) {
    // 添加
    this.add = function (entity) {
        return $http.post(basePath+"/specification/add", entity);
    };

    // 修改
    this.update = function (entity) {
        return $http.put(basePath+"/specification/modify", entity);
    };

    // 根据id查询
    this.findOne = function (id) {
        return $http.get(basePath+"/specification/get?id=" + id);
    };
    // 条件查询
    this.search = function (page, rows, searchEntity) {
        return $http.post(basePath+"/specification/list?page=" + page + "&rows=" + rows,
            searchEntity);
    };
    this.findSpecificationList=function(){
        return $http.get(basePath+"/specification/findSpecificationList");
    }
    // 删除
    this.dele = function (selectIds) {
        return $http.delete(basePath+"/specification/delete?ids=" + selectIds);
    }
});