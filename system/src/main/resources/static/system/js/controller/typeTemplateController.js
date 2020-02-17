 //控制层 
app.controller('typeTemplateController' ,function($scope,$controller,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
	//查询实体 
	$scope.findOne=function(id){				
		typeTemplateService.findOne(id).success(function(data){
			if (data.code === 200) {
				$scope.entity=data.data;
				$scope.entity.brands = JSON.parse($scope.entity.brands);
				$scope.entity.specifications = JSON.parse($scope.entity.specifications);
				$scope.entity.customAttributeItem = JSON.parse($scope.entity.customAttributeItem);
			}
		});
	}

	//保存
	$scope.save=function(){
		var serviceObject;//服务层对象
		if($scope.entity.id!=null){//如果有ID
			serviceObject=typeTemplateService.update( $scope.entity ); //修改  
		}else{
			serviceObject=typeTemplateService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(data){
				if(data.code === 200){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(data.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		typeTemplateService.dele( $scope.selectIds ).success(function(data){
            if(data.code === 200){
                $scope.reloadList();//刷新列表
                $scope.selectIds=[];
            }
		});
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		typeTemplateService.search(page,rows,$scope.searchEntity).success(function(data){
			if(data.code === 200){
				$scope.list=data.data.list;
				$scope.paginationConf.totalItems=data.data.total;//更新总记录数
			}			
		});
	};
	
	//新增规格选项行
	$scope.addTableRow = function(){
		//添加一行
		$scope.entity.customAttributeItem.push({});
	};
	//删除规格选项行
	$scope.deleTableRow = function(index){		
		$scope.entity.customAttributeItem.splice(index,1);
	}
	
	//模版表存储数据格式:
	//品牌:
	//[{"id":16,"text":"TCL"},{"id":13,"text":"长虹"},{"id":14,"text":"海尔"},{"id":19,"text":"创维"},{"id":21,"text":"康佳"},{"id":18,"text":"夏普"},{"id":17,"text":"海信"},{"id":20,"text":"东芝"},{"id":15,"text":"飞利浦"},{"id":22,"text":"LG"}]
	//规格数据格式:
	//[{"id":33,"text":"电视屏幕尺寸"}]
	//扩展属性:
	//[{"text":"内存大小"},{"text":"颜色"}]
	//品牌列表
	$scope.brandList = {data:[]};
	$scope.findBrandList = function(){
		typeTemplateService.selectBrandList().success(function(data){
			$scope.brandList = {
					data:data
			}
		})
	}
	//规格列表
	$scope.specificationList = {data:[]};
	$scope.findSpecificationList = function(){
		typeTemplateService.selectOptionList().success(function(data){
			$scope.specificationList = {
					data:data
			}
		})
	}
});	
