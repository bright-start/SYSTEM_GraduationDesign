app.service("authService", function ($http) {
    this.loadAllRole = function () {
        return $http.get(basePath + "/auth/loadAllRole");
    };
    this.loadHaveAuth = function (roleId) {
        return $http.get(basePath + "/auth/loadHaveAuth?roleId="+roleId);
    };
    this.loadNoHaveAuth = function (roleId) {
        return $http.get(basePath + "/auth/loadNoHaveAuth?roleId="+roleId);
    };

});