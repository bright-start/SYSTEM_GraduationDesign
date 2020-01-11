//定义模块
var app = angular.module("system", []);
app.constant("basePath", "http://127.0.0.1:8080/system");
//定义过滤器
//此过滤器的作用，吧文本格式数据变成html
app.filter("trustHtml",["$sce",function($sce){
    return function(data) {
        //吧文本数据转换成html识别
        return $sce.trustAsHtml(data);
    }
}]);