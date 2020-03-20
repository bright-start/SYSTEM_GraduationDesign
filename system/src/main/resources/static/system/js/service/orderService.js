app.service("orderService", function ($http) {

    // 根据id查询
    this.findOrderItemByOrderId = function (id) {
        return $http.get(basePath+"/order/findOrderItemByOrderId?id="+id);
    };
    // 条件查询
    this.search = function (page, rows, searchEntity) {
        return $http.post(basePath+"/order/list?page=" + page + "&rows=" + rows,
            searchEntity);
    };
    this.updateStatus = function (status, ids) {
        return $http.put(basePath+"/order/updateStatus?status="+status+"&ids=" + ids);
    };

});