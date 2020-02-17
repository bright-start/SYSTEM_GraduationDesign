//服务层
app.service('goodsService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../goods/findAll');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../goods/findPage?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../goods/findOne?id='+id);
	}
	//审核
	this.changeStatus=function(id,status){
		return $http.get('../goods/update?id='+id+'&status='+status);
	}
	//审核
	this.changeStatusSelected=function(ids,status){
		return $http.get('../goods/updateSelected?ids='+ids+'&status='+status);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../goods/add',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../goods/update',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../goods/delete?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../goods/search?page='+page+"&rows="+rows, searchEntity);
	}    	
});
