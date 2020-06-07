app.controller("loginController", function ($scope, $controller,$interval, loginService) {

    $controller("baseController", {$scope: $scope});

    var task;
    $scope.loadLoginUser = function () {
        loginService.loadLoginUser().success(function (data) {
            if(data.code === 200){
                if(typeof(data.data) === "undefined"){
                    $scope.loginUserInfo.username = "undefined";
                }else {
                    $scope.loginUserInfo = data.data;
                    var role = $scope.loginUserInfo.authorities[0].role;
                    $scope.a = role.indexOf("ADMIN");
                    $scope.b = role.indexOf("SUPERADMIN");

                    task = $interval(function() {
                        $scope.loadMsg();
                    }, 10000);
                }
            }else{

            }
        });
    };

    $scope.$on('$ionicView.beforeLeave', function() {
        $interval.cancel(stop);//离开页面后停止轮询
    })

    $scope.loadMsg = function(){
        loginService.loadMsg().success(function(data){
            if(data.code === 200){
                if(data.data != null){
                    $scope.msgList = data.data;
                }
            }
        });
    }

    $scope.logout = function(){
        loginService.logout().success(function(data){
            if(data.code === 200){
                $scope.loginUserInfo={};
                console.log(data.message);
                window.location.reload();
            }
        });
    };
});