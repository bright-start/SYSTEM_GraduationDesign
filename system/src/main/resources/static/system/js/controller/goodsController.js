 //控制层 
app.controller('goodsController' ,function($scope,$controller,$location,goodsService,areaService,brandService,specificationService,uploadService){
	
	$controller('baseController',{$scope:$scope});//继承

	
	//查询实体 
	$scope.findOne_=function(){
	    id = $location.search()["id"];
		if(id == null){
            return ;
        }
        goodsService.findOne(id).success(function(data) {
            if(data.code === 200){
                $scope.entity = data.data;
                //回显描述信息
                editor.html($scope.entity.introduction);
                //回显图片属性
                var imageList = $scope.entity.goodsImages.split(",");
                for(var i =0;i < imageList.length;i++){
                    $scope.entity.goodsImages.push(imageList[i]);
                }
                //回显扩展属性
    //        	$scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.entity.goodsDesc.customAttributeItems);
                $scope.entity.isEnableSpec = 1;
                //回显规格属性选项组合sku
                for(var i = 0;i<$scope.entity.productList.length;i++){
                    $scope.entity.productList[i].specificationDesc=JSON.parse($scope.entity.productList[i].specificationDesc);
                }
        	}
        });
	}

	$scope.findOne =function(id){
        goodsService.findOne(id).success(function(data) {
            if(data.code === 200){
                $scope.entity = data.data;
            }
        });
	}

	$scope.findAreaList = function(){
	    areaService.findAreaList().success(function(data){
	        if(data.code === 200){
	            $scope.areaList = data.data;
	        }
	    });
	}

	$scope.findBrandList = function(){
	    brandService.findBrandList().success(function(data){
	        if(data.code === 200){
	            $scope.brandList = data.data;
	        }
	    });
	}

	$scope.findSpecificationList = function(){
	    specificationService.findSpecificationList().success(function(data){
	        console.log(data);
	        if(data.code === 200){
	            $scope.specificationList = data.data;
	            var len = $scope.specificationList.length;
	            for(var i =0;i<len;i++){
	                $scope.specificationList[i].specificationDesc = $scope.specificationList[i].specificationDesc.split(',');
	            }
	        }
	    });
	}

    $scope.examine_errorMsg='';

    //批量检测字段
    $scope.flag = true;
    $scope.isBatch=function(flag){
        $scope.flag=flag;
    }

	//审核
	$scope.changeStatus=function(id,status){
	    console.log("isBatch:"+$scope.flag);
		var changeStatus;
		if($scope.flag){
			changeStatus = goodsService.changeStatusSelected($scope.selectIds,status,$scope.examine_errorMsg);
		}else{
		    changeStatus = goodsService.changeStatus(id,status,$scope.examine_errorMsg);
		}
		changeStatus.success(function(data){
			if(data.code === 200){
				$scope.reloadList();
			}else{
				alert(data.message);
			}
		});
	}
	
	//保存 
	$scope.save=function(){
//	    $scope.entity.goodsDesc.customAttributeItems = JSON.stringify($scope.entity.goodsDesc.customAttributeItems);
    	$scope.entity.goodsImages = $scope.entity.goodsImages.join(',');
    	for(var i = 0; i<$scope.entity.productList.length;i++){
    		$scope.entity.productList[i].spec = JSON.stringify($scope.entity.productList[i].spec);
    	}

		var serviceObject;// 服务层对象
        if ($scope.entity.id != null) {// 如果有ID
        	serviceObject = goodsService.update($scope.entity); // 修改
        } else {
        	$scope.entity.goodsIntroduce = editor.html();
            console.log($scope.entity);
        	serviceObject = goodsService.add($scope.entity);// 增加
        }
        serviceObject.success(function(data) {
        	if (data.code === 200) {
                $scope.isEnableSpec = 0;
                $scope.entity = {};
                editor.html('');
        	} else if(data.code === 401){
        	    $scope.isEnableSpec = 0;
                $scope.entity = {};
                editor.html('');
                alert(data.message);
        	}else{

        	}
        });
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){
	    $scope.paginationConf.totalItems=0;
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(data){
			    if(data.code === 200){
				    $scope.list=data.data.list;
				    for(var i = 0;i < $scope.list.length;i++){
				        $scope.list[i].goodsImages = $scope.list[i].goodsImages.split(",")[0];
				    }
				    $scope.paginationConf.totalItems=data.data.total;//更新总记录数
				}
			}			
		);
	}

	$scope.importGoods = function(){
	    console.log("importGoods");
	    goodsService.importGoods().success(function(data){
	        if(data.code === 200){
	            console.log("初始化完成");
	        }
	    });
	}

    //商品状态改变
	$scope.updateStatus = function(id,status) {
	    console.log(id);
	    if(id != -1){
	        $scope.selectIds.push(id);
	    }
	    if($scope.selectIds==[]){
	        return;
	    }
	    console.log("-"+$scope.selectIds+"-"+status);
		// 获取选中的复选框
		goodsService.updateStatus($scope.selectIds,status).success(function(data) {
			if (data.code === 200) {
				$scope.reloadList();// 刷新列表
				$scope.selectIds = [];
			}
		});
	}

	$scope.uploadPhoto = function(){
    	uploadService.uploadPhoto().success(function(data){
    		if(data.code === 200){
    			$scope.imageEntity.url=data.data;
    		}else{
    			alert("上传失败");
    		}
    	})
    }

    $scope.entity = {
    	goodsImages:[],
    	productList:[]
    };

    $scope.add_image_entity = function(){
        if(typeof($scope.imageEntity.url) === "undefined" || $scope.imageEntity.url===null){
            alert("上传图片不能未空");
            return;
        }
    	$scope.entity.goodsImages.push($scope.imageEntity.url);
    	$scope.imageEntity = {};
    }

    $scope.removeImageEntity=function(index){
    	$scope.entity.goodsImages.splice(index,1);
    }

    $scope.clear = function($event){
         if ($event.target.checked) {
            $scope.entity.productList=[];
         }else{
         $scope.entity.productList
            $scope.specList=[];
            $scope.entity.productList=[{spec:{},price:9999999,stockCount:0,status:'0',isDefault:'0'}];
         }
    }

    $scope.specList = [];

    $scope.updateSpecAttribute=function($event,text,name){
    	var specList = $scope.specList;
    	//循环规格选项值
    	for(var i = 0; i<specList.length;i++){
    		//判断选择的是那个属性
    		if(specList[i].attributeName==text){
    			//判断是否选中事件
    			if($event.target.checked){
    				specList[i].attributeValue.push(name);
    			}else{
    				specList[i].attributeValue.splice(specList[i].attributeValue.indexOf(name),1);
    			}
    			return;
    		}
    	}
    	//如果商品描述中规格属性没值，吧选择中的值推送到集合中
    	specList.push({attributeName:text,attributeValue:[name]});
    	console.log(specList);
    }

    //定义函数，封装规格选项组合成商品最小销售单元
    $scope.createSkuItemList=function(){
    	//初始化规格数据组合
    	$scope.entity.productList=[{spec:{},price:9999999,stockCount:0,status:'0',isDefault:'0'}]
    	var specList = $scope.specList;
    	for(var i=0;i<specList.length;i++){
    		$scope.entity.productList = addColumn($scope.entity.productList,specList[i].attributeName,specList[i].attributeValue);
    	}
    };

    addColumn=function(list,name,columnValues){
    	var newList = [];
    	//循环list集合
    	for(var i=0;i<list.length;i++){
    		var oldRow = list[i];
    		for(var j=0;j<columnValues.length;j++){
    			//深克隆操作，新创建一行数据
    			var newRow = JSON.parse(JSON.stringify(oldRow));
    			newRow.spec[name] = columnValues[j];
    			newList.push(newRow);
    		}
    	}
    	return newList;
    };

    //定义检测规格选项是否选中的函数
    //选中 true 为选中 false
//    $scope.isAttributeChecked = function(specName,value){
//    	var specList = $scope.entity.productList.specificationItems;
//    	for(var i =0; i<specList.length;i++){
//    		if(specList[i].attributeName == specName){
//    			if(specList[i].attributeValue.indexOf(value)>=0){
//    				return true;
//    			}
//    		}
//    		return false;
//    	}
//    };
    
});	
