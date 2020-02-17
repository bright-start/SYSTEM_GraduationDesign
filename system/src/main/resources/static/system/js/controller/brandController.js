app.controller("brandController", function ($scope,$controller, brandService) {

    $controller("baseController", {$scope: $scope});

    // 添加函数
    $scope.save = function () {
        // 初始化返回对象
        var obj = null;
        // 判断是否是添加
        if ($scope.entity.id != null) {
            obj = brandService.update($scope.entity);
        } else {
            obj = brandService.add($scope.entity);
        }

        obj.success(function (data) {
            // 判断
            if (data.code === 200) {
                // 刷新
                $scope.reloadList();
            } else {
                alert(data.message);
            }
        })

    };

    $scope.findOne = function (id) {
        // 发送请求
        brandService.findOne(id).success(function (data) {
            $scope.entity = data.data;
        })
    };

    // 初始化对象
    $scope.searchEntity = {};

    // 条件查询方法
    $scope.search = function (page, rows) {
        // 发送条件查询请求
        brandService.search(page, rows, $scope.searchEntity).success(function (data) {
            if (data.code === 200) {
                $scope.list = data.data.list;
                $scope.paginationConf.totalItems = data.data.total;// 更新总记录数
            } else {
                // 全局异常页面
            }
        })
    };


    $scope.dele = function () {
        // 发送删除请求
        brandService.dele($scope.selectIds).success(function (data) {
            // 判断是否删除成功
            if (data.code === 200) {
                // 刷新列表
                $scope.reloadList();
            } else {
                alert("删除失败");
            }
        })
    };
});