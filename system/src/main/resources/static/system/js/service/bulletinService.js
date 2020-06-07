app.service("bulletinService", function ($http) {
    // 添加
    this.add = function (entity) {
        return $http.post("http://www.cys.com:9200/system/bulletin/add", entity);
    };

    // 修改
    this.update = function (entity) {
        return $http.put("http://www.cys.com:9200/system/bulletin/modify", entity);
    };
    this.updateStatus = function (id) {
        return $http.put("http://www.cys.com:9200/system/bulletin/updateStatus?id="+id);
    };

    // 根据id查询
    this.findOne = function (id) {
        return $http.get("http://www.cys.com:9200/system/bulletin/get?id=" + id);
    };
    // 条件查询
    this.search = function (page, rows, searchEntity) {
        return $http.post("http://www.cys.com:9200/system/bulletin/list?page=" + page + "&rows=" + rows,
            searchEntity);
    };
    // 删除
    this.dele = function (selectIds) {
        return $http.delete("http://www.cys.com:9200/system/bulletin/delete?ids=" + selectIds);
    }
});