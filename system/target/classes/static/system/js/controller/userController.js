app.controller("userController", function ($scope, $controller, userService) {

    $controller("baseController", {$scope: $scope});

    // 初始化对象
    $scope.searchEntity = {role_id: -1, status: 0};
    $scope.statusName = '全部';
    $scope.roleName = '全部';

    // 条件查询方法
    $scope.search = function (page, rows) {
        console.log($scope.searchEntity.username + " " + $scope.searchEntity.status);
        // 发送条件查询请求
        userService.search(page, rows, $scope.searchEntity).success(function (data) {
            if (data.code === 200) {
                $scope.list = data.data.list;
                $scope.paginationConf.totalItems = data.data.total;// 更新总记录数
            } else {
                // 全局异常页面
            }
        })
    };


    $scope.disable = function () {
        // 限制账号
        userService.disable($scope.selectIds).success(function (data) {
            // 判断是否请求成功
            if (data.code === 200) {
                // 刷新列表
                $scope.reloadList();
            } else {
                alert("限制失败");
            }
        })
    };

    $scope.changeStatus = function (statusName, status) {
        $scope.statusName = statusName;
        $scope.searchEntity.status = status;
    };

    $scope.switchRole = function (roleName, role_id) {
        $scope.roleName = roleName;
        $scope.searchEntity.role_id = role_id;
    }
});