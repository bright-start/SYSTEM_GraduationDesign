app.service("articleService",function($http){
    // 添加
    this.add = function(entity) {
        return $http.post("http://127.0.0.1:8080/system/article/add", entity);
    };

    // 修改
    this.update = function(entity) {
        return $http.put("http://127.0.0.1:8080/system/article/modify", entity);
    };

    // 根据id查询
    this.findOne = function(id) {
        return $http.get("http://127.0.0.1:8080/system/article/get?id=" + id);
    };
    // 条件查询
    this.search = function(page, rows, searchEntity) {
        return $http.post("http://127.0.0.1:8080/system/article/list?page=" + page + "&rows=" + rows,
            searchEntity);
    };
    // 删除
    this.dele = function(selectIds) {
        return $http.delete("http://127.0.0.1:8080/system/article/delete?ids=" + selectIds);
    }
});