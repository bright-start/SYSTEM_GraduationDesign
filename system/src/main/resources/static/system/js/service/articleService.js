app.service("articleService", function ($http) {
    // 添加
    this.add = function (entity) {
        return $http.post("http://www.cys.com:9200/system/article/add", entity);
    };

    // 根据id查询
    this.findOne = function (id) {
        return $http.get("http://www.cys.com:9200/system/article/get?id=" + id);
    };

    this.updateStatus = function (id) {
        return $http.get("http://www.cys.com:9200/system/article/updateStatus?id=" + id);
    };

    // 条件查询
    this.search = function (page, rows, searchEntity) {
        return $http.post("http://www.cys.com:9200/system/article/list?page=" + page + "&rows=" + rows,
            searchEntity);
    };
    // 删除
    this.dele = function (selectIds) {
        return $http.delete("http://www.cys.com:9200/system/article/delete?ids=" + selectIds);
    };
    // 浏览
    this.browse = function (articleId) {
        return $http.get("http://www.cys.com:9200/system/article/increaseBrowse?articleId=" + articleId);
    };
    // 喜欢
    this.love = function (articleId, islove) {
        return $http.put("http://www.cys.com:9200/system/article/increaseLoveNum?articleId=" + articleId + "&islove=" + islove);
    };

});