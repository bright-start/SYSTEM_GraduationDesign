app.service("areaService", function ($http) {

    this.findAreaList = function () {
        return $http.get(basePath+"/index/findAreaList");
    };
});