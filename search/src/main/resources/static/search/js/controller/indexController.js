app.controller("indexController", function ($scope,$controller,$location,areaService,bulletinService,articleService,loginService,goodsService,cartService) {

    $controller("baseController", {$scope: $scope});

    $scope.findAreaList = function(){
        areaService.findAreaList().success(function(data){
            if(data.code === 200){
                $scope.areaList = data.data;
            }
        });
    };

    $scope.loadBulletinList =function(){
        bulletinService.loadBulletinList().success(function(data){
            if(data.code === 200){
                $scope.bulletinList = data.data;
            }
        })
    }

    $scope.loadArticleList =function(){
        articleService.loadArticleList().success(function(data){
            if(data.code === 200){
                $scope.articleList = data.data;
            }
        })
    }

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

    $scope.findGoodGoods = function(){
        goodsService.findGoodGoods().success(function(data){
            if(data.code === 200){
                if(data.data != null){
                    $scope.goodGoodsList = data.data;
                    for(var i=0;i<$scope.goodGoodsList.length;i++){
                        $scope.goodGoodsList[i].goods_images = $scope.goodGoodsList[i].goods_images.split(',')[0];
                    }
                    console.log($scope.goodGoodsList);
                }
            }
        });
    };

    $scope.searchEntity = {};

    $scope.search1 = function(){
        var goodsName = $location.search()['goodsName'];
        var areaName = $location.search()['areaName'];
        if(typeof(goodsName) != "undefined" || goodsName != null){
            $scope.searchEntity.goodsName = goodsName;
            $scope.goodsName = "";
        }else if(typeof(areaName) != "undefined" || areaId != null){
            $scope.searchEntity.areaName = areaName;
        }else{
            return;
        }
        $scope.search();
    }

    $scope.search2 = function(){
        if($scope.searchEntity.goodsName == null){
            return;
        }else{
            window.location.href="http://www.cys.com:9200/search/search/html/search.html#?goodsName="+$scope.searchEntity.goodsName;
        }
    }

    $scope.search3 = function(areaName){
        $scope.searchEntity.areaName = areaName;
        $scope.search();
    }

    $scope.search = function(){
        console.log($scope.searchEntity);
        goodsService.search($scope.searchEntity).success(function(data){
            if(data.code === 200){
                $scope.searchGoodsInfo = data.data;
            }
        });
    };

    $scope.findGoodsByArea = function(areaName){
        window.location.href="http://www.cys.com:9200/search/search/html/search.html#?areaName="+areaName;
    }

    $scope.specList = [];
    $scope.loadGoods = function(){
        var id = $location.search()['goodsId'];
        console.log(id);
        goodsService.loadGoods(id).success(function(data){
            if(data.code === 200){
                $scope.goods = data.data;
                $scope.goods.goodsImages = $scope.goods.goodsImages.split(",");
                for(var i = 0; i< $scope.goods.productList.length;i++){
                    $scope.goods.productList[i].spec = JSON.parse($scope.goods.productList[i].spec);
                    $scope.goods.productList[i].spec = $scope._jsonToMap($scope.goods.productList[i].spec);
                    $scope.goods.productList[i].spec.forEach(function(value,key){
                        for(var j = 0;j < $scope.specList.length;j++){
//                            console.log($scope.spec[j].specName + "-"+key)
//                            console.log($scope.spec[j].specName == key);
                            if($scope.specList[j].specName == key){
                                if($scope.specList[j].specDesc.indexOf(value) == -1){
                                    $scope.specList[j].specDesc.push(value);
                                }
                                return;
                            }
                        }
                        $scope.specList.push({specName:key,specDesc:[value]})
　　　　　　　　　　  });
                }
                console.log($scope.goods);
                console.log($scope.specList);
                $scope.array = new Array($scope.specList.length);
            }
        });
    }


    $scope._objToStrMap = function(obj){
      let strMap = new Map();
      for (let k of Object.keys(obj)) {
        strMap.set(k,obj[k]);
      }
      return strMap;
    }
     /**
      *json转换为map
      */
     $scope._jsonToMap = function(jsonStr){
        return $scope._objToStrMap(JSON.parse(jsonStr));
     }

     $scope.array=[];

     $scope.selectProductId = function(){
         for(var i=0;i<$scope.specList.length;i++){
             var specDesc = $scope.specList[i].specDesc[$scope.indexMap.get(i)];
             $scope.array[i]= specDesc;
         }
         for(var i = 0;i<$scope.goods.productList.length;i++){
            $scope.flag = 0;
            for(var j=0;j<$scope.specList.length;j++){
                 console.log($scope.goods.productList[i].spec.get($scope.specList[j].specName) == $scope.array[j]);
                 if($scope.goods.productList[i].spec.get($scope.specList[j].specName) == $scope.array[j]){
                    $scope.flag=$scope.flag+1;
                 }
             }
             if($scope.flag == $scope.specList.length){
                $scope.productId = $scope.goods.productList[i].id;
                return $scope.productId;
             }
         }
     }

     $scope.indexMap = new Map();

     $scope.productId = null;
     $scope.addCart = function(num){
         for(var i=0;i<$scope.specList.length;i++){
              $scope.indexMap.set(i,0);
         }
        if(map.size != 0){
            map.forEach(function(value,key){
　　　　　　　　　$scope.indexMap.set(key,value);
　　　　　　　});
        }
        $scope.productId = $scope.selectProductId();
        cartService.addCart($scope.productId,num).success(function(data){
            if(data.code === 200){
                console.log(data.message);
                alert(data.message);
            }
        });
     }

     $scope.lookCart = function(){
        cartService.lookCart().success(function(data){
             if(data.code === 200){
                 $scope.cartGoods=data.data;
                 console.log(data.data);
             }else if(data.code == 401){
                  window.location.href="http://www.cys.com:9200/sso/sso/html/login.html?remoteAddress=http://www.cys.com:9200/search/search/html/cart.html";
             }else{
                  window.location.href="http://www.cys.com:9200/search/search/html/500.html";
             }
         });
     }

     $scope.add = function(cartId,cartItemId,num){
        console.log(cartId+"-"+cartItemId+"-"+num);
        cartService.addNum(cartId,cartItemId,num).success(function(data){
            if(data.code === 200){
                 console.log(data.data);
                 window.location.reload()
            }
        })
     }

     $scope.deleteCartItem =function(cartId){
        console.log($scope.selectCartIds.length);
         if($scope.selectCartIds.length == 0){
            alert("请勾选你要删除的商品");
            return;
         }
         cartService.deleteCartItem(cartId,$scope.selectCartIds).success(function(data){
              if(data.code === 200){
                  console.log(data.data);
                  window.location.reload();
              }
         });
     }

     $scope.clearCart = function(){
         cartService.clearCart().success(function(data){
               if(data.code === 200){
                   console.log(data.message);
                   window.location.reload();
               }
          });
     }

     $scope.isRecomment = -1;

     $scope.recomment = function(id){
        if($scope.isRecomment == -1){
            $scope.isRecomment =1;
        }else{
            $scope.isRecomment=-1
        }
        console.log("recomment:"+$scope.isRecomment);
        goodsService.recomment(id,$scope.isRecomment).success(function(data){
            if(data.code === 200){
                if(data.data != null){
                    $scope.goods.recomment = $scope.goods.recomment + data.data;
                }
            }
        });
     }

     $scope.totalPrice = 0;
     $scope.selectCartIds = [];
     $scope.choose = function ($event, tip,cartId) {

         if ($event.target.checked) {
             $scope.totalPrice = $scope.totalPrice+tip;
             $scope.selectCartIds.push(cartId);
         } else {
             $scope.totalPrice = $scope.totalPrice-tip;
             $scope.selectCartIds.splice($scope.selectIds.indexOf(cartId), 1);
         }

     };

     $scope.buildOrder = function(){
        cartService.buildOrder($scope.selectCartIds).success(function(data){
            if(data.code === 200){
                if(data.message="success"){
                    window.location.href="pay.html";
                }
            }else{
                 window.location.href="http://www.cys.com:9200/search/search/html/500.html";
            }
        });
     }

     $scope.lookNoPayOrder = function(){
        cartService.lookNoPayOrder().success(function(data){
            if(data.code === 200){
                if(data.data == null){
                    window.location.href="http://www.cys.com:9200/search/search/html/cart.html";
                }else{
                    $scope.noPayOrderList = data.data;
                    if($scope.noPayOrderList != null){
                        var orderPayOrder = $scope.noPayOrderList;
                        orderPayOrder=$scope._objToStrMap(orderPayOrder);
                        orderPayOrder.forEach(function(value,key){
                            var orderMap = $scope._objToStrMap(value);
                            orderMap.forEach(function(val,k){
                                $scope.totalPrice = $scope.totalPrice + val[0][0].totalPrice;
                            });
                        });
                        console.log($scope.noPayOrderList);
                    }
                }
            }else{
                 window.location.href="http://www.cys.com:9200/search/search/html/500.html";
            }
        });
     }
     $scope.order = {};
     $scope.order.payType="货到付款";
     $scope.payType =function(type){
        $scope.order.payType = type;
     }

     $scope.pay = function(){
         console.log($scope.order);
         cartService.pay($scope.order).success(function(data){
             if(data.code === 200){
                alert(data.message);
                if(data.message == "付款成功") {
                 window.location.href="pay_success.html";
                }
                if(data.data != null || typeof(data.data) != "undefined"){
                    var noProductList = [];
                    for(var i =0;i<data.data.length;i++){
                        noProductList.push(data.data[i])
                    }
                    console.log(noProductList.join(',')+"商品库存不足");
                }
             }else{
                  window.location.href="http://www.cys.com:9200/search/search/html/500.html";
             }
         });
      }

     $scope.lookPayOrder = function(status){
          cartService.lookPayOrder(status).success(function(data){
               if(data.code === 200){
                   $scope.payOrderList = data.data;
                   console.log($scope.payOrderList);
               }else{
                    window.location.href="http://www.cys.com:9200/search/search/html/500.html";
               }
          });
     }
     $scope.deleteOrder = function(payCode){
        cartService.deleteOrder(payCode).success(function(data){
            if(data.code === 200){
                console.log("删除成功");
            }
        });
     }
});