//服务层
app.service('typeTemplateService',function($http){
	//查询实体
	this.findOne=function(id){
		return $http.get(basePath+"/typeTemplate/findOne?id="+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post(basePath+"/typeTemplate/add",entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.put(basePath+"/typeTemplate/update",entity );
	}
	//删除
	this.dele=function(ids){
		return $http.delete(basePath+"/typeTemplate/delete?ids="+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post(basePath+"/typeTemplate/search?page="+page+"&rows="+rows, searchEntity);
	}
    //读取列表数据绑定到表单中
    this.selectBrandList=function(){
        return $http.get(basePath+"/typeTemplate/selectBrandList");
    }
	this.selectOptionList=function(){
		return $http.get(basePath+"/typeTemplate/selectOptionList");
	}
});
