app.service("ipinfoService", function ($http) {

    this.findIPLogByDate = function (ipAddr, date) {
        return $http.get("http://127.0.0.1:8080/system/ip/get?ipAddr=" + ipAddr + "&date=" + date);
    };
    // 条件查询
    this.search = function (page, rows, searchEntity) {
        return $http.post("http://127.0.0.1:8080/system/ip/list?page=" + page + "&rows=" + rows,
            searchEntity);
    };
    this.buildtable = function () {
        return $http.get("http://127.0.0.1:8080/system/ip/log/download");
    };
});