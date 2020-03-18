app.service("goodsService", function ($http) {

    this.findGoodGoods = function () {
        return $http.get(basePath+"/index/findGoodGoods");
    };
    this.search = function(searchEntity){
        return $http.post(basePath+"/_search",searchEntity);
    }
    this.loadGoods = function(id){
        return $http.get(basePath+ "/get?id="+id);
    }
    this.recomment = function(id,isRecomment){
        return $http.get(basePath+ "/recomment?id="+id+"&status="+isRecomment);
    }
});