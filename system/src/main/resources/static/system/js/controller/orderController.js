app.controller("orderController", function ($scope, $controller, orderService) {

    $controller("baseController", {$scope: $scope});


    $scope.statusName = "待受理";
    $scope.changeStatus = function (statusName, status) {
        $scope.statusName = statusName;
        if(status != -1){
            $scope.searchEntity.status = status;
        }
    };

    $scope.searchEntity = {status:1};

    $scope.search = function (page, rows) {
        // 发送条件查询请求
        orderService.search(page, rows, $scope.searchEntity).success(function (data) {
            if (data.code === 200) {
                console.log(data.data);
                if(typeof(data.data) != "undefined" && data.data != null){
                    $scope.list = data.data.list;
                    $scope.paginationConf.totalItems = data.data.total;// 更新总记录数
                }else{
                    $scope.list = null;
                    $scope.paginationConf.totalItems = 0;
                }
            } else {
                // 全局异常页面
            }
        })
    };

    $scope.findOrderItemByOrderId = function(orderId){
        orderService.findOrderItemByOrderId($scope.searchEntity).success(function(data){
            if(data.code === 200){
                $scope.entity = data.data;
            }
        });
    }

    $scope.updateStatus = function(status,id){
        if(id != -1){
            $scope.selectIds.push(id);
        }
        orderService.updateStatus(status,$scope.selectIds).success(function(data){
            if(data.code === 200){
                window.location.reload();
            }
        });
    }
});