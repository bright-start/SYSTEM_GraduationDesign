app.service("bulletinService", function ($http) {

    // 查询最新的两条公告
    this.loadBulletinList = function () {
        return $http.get(basePath+"/index/loadBulletinList");
    };

});