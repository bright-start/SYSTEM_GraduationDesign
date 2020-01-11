app.controller("loginController", function ($scope, $controller, loginService) {

    $controller("baseController", {$scope: $scope});

    $scope.loadLoginUser = function () {
        loginService.loadLoginUser = function (data) {
            if(data === 200){

            }else{

            }
        }
    };
});