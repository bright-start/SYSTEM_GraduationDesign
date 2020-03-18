app.controller("loginController", function ($scope, $controller, loginService) {

    $controller("baseController", {$scope: $scope});

    $scope.loadLoginUser = function () {
        loginService.loadLoginUser().success(function (data) {
            if(data.code === 200){
                console.log(data.data);
                if(typeof(data.data) === "undefined"){
                    $scope.loginUserInfo.username = "undefined";
                }else {
                    $scope.loginUserInfo = data.data;
                }
            }else{

            }
        });
    };

    $scope.logout = function(){
        loginService.logout().success(function(data){
            if(data.code === 200){
                $scope.loginUserInfo={};
                console.log(data.message);
            }
        });
    };
});