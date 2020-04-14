app.controller("shopController", function ($scope, $controller, shopService) {

    $controller("baseController", {$scope: $scope});

    // 条件查询方法
    $scope.search = function (page, rows) {
        // 发送条件查询请求
        shopService.search(page, rows, $scope.searchEntity).success(function (data) {
            if (data.code === 200) {
                console.log(data.data.list);
                $scope.list = data.data.list;
                $scope.paginationConf.totalItems = data.data.total;// 更新总记录数
            } else {
                // 全局异常页面
            }
        })
    };
    $scope.apply = function(status){
        console.log($scope.selectIds);
        shopService.apply($scope.selectIds,status).success(function (data) {
            if (data.code === 200) {
                console.log("审核通过");
            } else {
                // 全局异常页面
            }
        })
    }
});