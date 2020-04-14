app.controller("authController", function ($scope, $controller, authService) {

    $controller("baseController", {$scope: $scope});

    $scope.loadAllRole = function(){
        authService.loadAllRole().success(function(data){
            if(data.code === 200){
                $scope.roleList = data.data;
                console.log($scope.roleList);
            }
        })
    }

    $scope.$watch('roleId',function(newValue,oldValue){
        if(newValue == null || newValue == "undefined"){
            return;
        }
        authService.loadHaveAuth(newValue).success(function(data){
            if(data.code === 200){
                $scope.haveAuthList = data.data;
                console.log($scope.haveAuthList);
            }
        })
        authService.loadNoHaveAuth(newValue).success(function(data){
            if(data.code === 200){
                $scope.noHaveAuthList = data.data;
                console.log($scope.noHaveAuthList);
            }
        })
    })
});