app.service("cartService", function ($http) {
    this.addCart = function (productId,num) {
        return $http.get(basePath+"/cart/add?productId="+productId+"&num="+num);
    };
    this.lookCart = function () {
        return $http.get(basePath+"/cart/look");
    };
    this.addNum = function(cartId,cartItemId,num){
        return $http.get(basePath+"/cart/addNum?cartId="+cartId+"&cartItemId="+cartItemId + "&num="+num);
    }
    this.deleteCartItem = function (cartId,ids) {
        return $http.delete(basePath+"/cart/delete?cartId="+cartId+"&ids="+ids);
    };
    this.clearCart = function () {
        return $http.get(basePath+"/cart/clear");
    };
    this.buildOrder = function(ids){
        return $http.post(basePath+"/order/build",ids);
    };
    this.pay = function(order){
        return $http.post(basePath+"/order/pay",order);
    };
    this.lookPayOrder = function(){
        return $http.get(basePath+"/order/lookPay");
    };
    this.lookNoPayOrder = function(status){
        return $http.get(basePath+"/order/lookNoPay?status="+status);
    };
    this.deleteOrder = function(orderId){
        return $http.get(basePath+"/order/delete?orderId="+orderId);
    }
});