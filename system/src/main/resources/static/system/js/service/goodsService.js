//服务层
app.service('goodsService',function($http){

	//分页 
	this.findPage=function(page,rows){
		return $http.get(basePath+"/goods/findPage?page="+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get(basePath+"/goods/findOne?id="+id);
	}
	//审核
	this.changeStatus=function(id,status,examine_errorMsg){
		return $http.put(basePath+"/goods/examine?id="+id+'&status='+status+'&examineErrorMsg='+examine_errorMsg);
	}
	//批量审核
	this.changeStatusSelected=function(ids,status,examine_errorMsg){
		return $http.put(basePath+"/goods/examineSelected?ids="+ids+'&status='+status+'&examineErrorMsg='+examine_errorMsg);
	}
	//增加 
	this.add=function(entity){
		return  $http.post(basePath+"/goods/add",entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post(basePath+"/goods/update",entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get(basePath+"/goods/delete?ids="+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post(basePath+"/goods/search?page="+page+"&rows="+rows, searchEntity);
	}    	
});
