app.controller("ipinfoController", function ($scope, $controller, ipinfoService) {

    $controller("baseController", {$scope: $scope});

    $scope.findIPLogByDate = function (ipAddr, date, startDate) {
        if ("null" === date) {
            var d = new Date();
            var time = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
            $("#datetime").val(time);
            date = time;

            $scope.findEntity.ipAddr = ipAddr;
            $scope.findEntity.date = startDate.split(" ")[0];
            console.log($scope.findEntity.ipAddr);
            console.log($scope.findEntity.date);
        }
        console.log("查询" + date + "的数据");

        // 发送请求
        ipinfoService.findIPLogByDate(ipAddr, date).success(function (data) {
            console.log(data);
            if (data.code === 200) {
                if (data.data) {
                    $scope.loglist = data.data;
                    $("#responseTable").css({
                        "background-image": ""
                    });
                    $("#responseTable thead").css({
                        "visibility": "visible"
                    });
                } else {
                    $scope.loglist = null;
                    $("#responseTable").css({
                        "background-image": "url('http://www.cys.com:9200/system/system/images/nomore.png')"
                    });
                    $("#responseTable thead").css({
                        "visibility": "hidden"
                    });
                }
            } else {
                //
            }
        });
    };

    // 初始化对象
    $scope.searchEntity = {};

    // 条件查询方法
    $scope.search = function (page, rows) {
        // 发送条件查询请求
        ipinfoService.search(page, rows, $scope.searchEntity).success(function (data) {
            if (data.code === 200) {
                $scope.list = data.data.list;
                $scope.paginationConf.totalItems = data.data.total;
            } else {
                // 全局异常页面
            }
        })
    };


    $scope.buildtable = function () {
        ipinfoService.buildtable($scope.selectIds).success(function (data) {
            if (data.code === 200) {
                alert("ip日志成成成功");
            } else {
                alert("ip日志记录生成失败");
            }
        })
    };

    $scope.findEntity = {ipAddr: "", date: ""};

    $scope.findLog = function (ipAddr, startDate) {
        console.log("onblur执行");
        var datetime = $("#datetime").val();
        var time1 = datetime.split("-");
        var time2 = startDate.split("-");
        if (time1[0] >= time2[0]) {
            if (time1[1] >= time2[1]) {
                if (time1[2] >= time2[2]) {
                    $scope.findIPLogByDate(ipAddr, datetime, startDate);
                    return;
                }
            }
        }
        $scope.loglist = null;

        console.log("没有" + startDate + "之前的数据");
    }
});