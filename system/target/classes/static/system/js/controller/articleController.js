app.controller("articleController", function ($scope, $controller, $location, articleService, uploadService) {

    $controller("baseController", {$scope: $scope});

    // 添加函数
    $scope.save = function () {

        $scope.entity.content = editor.html();
        var obj = articleService.add($scope.entity);

        obj.success(function (data) {
            // 判断
            if (data.code === 200) {
                // 刷新
                window.location.href = "article.html"
            } else {
                alert(data.message);
            }
        })

    };


    $scope.findOne = function () {
        var id = $location.search()["id"];
        // 发送请求
        articleService.findOne(id).success(function (data) {
            $scope.entity = data.data;
        })
    };

    // 初始化对象
    $scope.searchEntity = {status: -1};
    $scope.statusName = "全部";

    // 条件查询方法
    $scope.search = function (page, rows) {
        console.log("文章查询");
        // 发送条件查询请求
        articleService.search(page, rows, $scope.searchEntity).success(function (data) {
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
        articleService.dele($scope.selectIds).success(function (data) {
            // 判断是否删除成功
            if (data.code === 200) {
                // 刷新列表
                $scope.reloadList();
            } else {
                alert("删除失败");
            }
        })
    };

    $scope.read_file = function () {
        console.log("上传word文件");
        uploadService.parseFile().success(function (data) {
            if (data.code === 200) {
                editor.html(data.data);
            } else {

            }
        })
    };
    $scope.changeStatus = function (statusName, status) {
        $scope.statusName = statusName;
        $scope.searchEntity.status = status;
    }
});