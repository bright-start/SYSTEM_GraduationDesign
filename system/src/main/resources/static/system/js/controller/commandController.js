app.controller("commandController", function ($scope, $controller, commandController) {

    $controller("baseController", {$scope: $scope});

    // 添加函数
    $scope.addCommand = function () {

        commandController.add($scope.command).success(function (data) {
            // 判断
            if (data.code === 200) {
                // 刷新
                $scope.reloadList();
            } else {
                alert(data.message);
            }
        })

    };

    // 初始化对象
    $scope.command = {};


    $scope.dele = function () {
        // 发送删除请求
        commandController.dele($scope.id).success(function (data) {
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