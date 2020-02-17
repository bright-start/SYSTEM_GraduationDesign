app.controller("articleController", function ($scope, $controller, $location, articleService, uploadService, commentService) {

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
        console.log("查询文章id:" + id);
        if(typeof(id) === "undefined"){
            return;
        }
        // 发送请求
        articleService.findOne(id).success(function (data) {
            console.log(data.data);
            $scope.entity = data.data;

            $scope.browse(id);
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

    $scope.browse = function (articleId) {
        console.log("浏览");
        articleService.browse(articleId).success(function (data) {
            if (data.code === 200) {
                if (data.data) {
                    $scope.entity.article.browseNum = $scope.entity.article.browseNum + 1;
                }
            }
        });
    };

    $scope.islove = 0;
    $scope.love = function (articleId) {
        console.log("喜欢");
        if ($scope.islove === 0) {
            $scope.islove = 1;
        } else {
            $scope.islove = 0;
        }
        articleService.love(articleId, $scope.islove).success(function (data) {
            if (data.code === 200) {
                if(data.data) {
                    $scope.entity.article.loveNum = $scope.entity.article.loveNum + 1;
                }else {
                    $scope.entity.article.loveNum = $scope.entity.article.loveNum - 1;
                }
            }
        });
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
    };

    // 评论
    // 添加函数
    $scope.addCommand = function (articleId) {
        console.log("新增评论:articleId" + articleId);
        $scope.command.articleId = articleId;
        if ($scope.command.content === "") {
            return;
        }
        commentService.addCommand($scope.command).success(function (data) {
            // 判断
            if (data.code === 200) {
                // 刷新
                $scope.entity.commandContentList.unshift(data.data);
                $scope.command.content = "";
            } else {
                alert(data.message);
            }
        })

    };

    // 初始化对象
    $scope.command = {};


    $scope.deleteCommand = function (index, commandId) {
        // 发送删除请求
        commentService.deleteCommand(commandId).success(function (data) {
            // 判断是否删除成功
            if (data.code === 200) {
                // 刷新列表
                $scope.entity.commandContentList.splice(index, 1);
            } else {
                alert("删除失败");
            }
        })
    };
});