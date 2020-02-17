app.controller("specificationController", function ($scope,$controller, specificationService) {

    $controller("baseController", {$scope: $scope});

    // 添加函数
    $scope.save = function () {
        // 初始化返回对象
        var obj = null;
        console.log($scope.entity.specificationDesc.join(","));
        $scope.entity.specificationDesc = $scope.entity.specificationDesc.join(",");
        // 判断是否是添加
        if ($scope.entity.id != null) {
            obj = specificationService.update($scope.entity);
        } else {
            obj = specificationService.add($scope.entity);
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
        specificationService.findOne(id).success(function (data) {
            $scope.entity = data.data;
            $scope.entity.specificationDesc = $scope.entity.specificationDesc.split(",");
        })
    };

    // 初始化对象
    $scope.searchEntity = {};

    // 条件查询方法
    $scope.search = function (page, rows) {
        // 发送条件查询请求
        specificationService.search(page, rows, $scope.searchEntity).success(function (data) {
            if (data.code === 200) {
                $scope.list = data.data.list;
                for(var i = 0;i < $scope.list.length;i++){
                    $scope.list[i].specificationDesc = $scope.list[i].specificationDesc.split(",");
                }
                $scope.paginationConf.totalItems = data.data.total;// 更新总记录数
            } else {
                // 全局异常页面
            }
        })
    };


    $scope.dele = function () {
        // 发送删除请求
        specificationService.dele($scope.selectIds).success(function (data) {
            // 判断是否删除成功
            if (data.code === 200) {
                // 刷新列表
                $scope.reloadList();
            } else {
                alert("删除失败");
            }
        })
    };

    $scope.desc = '';
    $scope.addDesc = function(){
        $scope.entity.specificationDesc.push($scope.desc);
        $scope.desc = '';
    }

    $scope.delDesc = function(index){
        $scope.entity.specificationDesc.splice(index, 1);
    }
});