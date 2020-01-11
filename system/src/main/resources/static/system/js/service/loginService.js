app.service("loginService", function ($http) {
    this.loadLoginUser = function (entity) {
        return $http.post("http://127.0.0.1:8080/system/user/add", entity);
    };
});