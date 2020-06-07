app.service("loginService", function ($http) {
    this.loadLoginUser = function () {
        return $http.get("http://www.cys.com:9200/sso/loadUser");
    };
    this.logout = function () {
        return $http.get("http://www.cys.com:9200/sso/logoutSafe");
    };
    this.loadMsg = function(){
        return $http.get(basePath+"/msg/load");
    };
});