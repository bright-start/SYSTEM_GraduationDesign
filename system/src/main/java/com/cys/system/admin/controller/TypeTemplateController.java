package com.cys.system.admin.controller;

import java.util.List;
import java.util.Map;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.exception.UnauthorizedException;
import com.cys.system.common.pojo.Brand;
import com.cys.system.common.pojo.TypeTemplate;
import com.cys.system.common.service.BrandService;
import com.cys.system.common.service.SSOService;
import com.cys.system.common.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
	
	@Autowired
	private TypeTemplateService typeTemplateService;
	
	@Autowired
	private BrandService brandService;

	@Autowired
	private SSOService ssoService;

	@PostMapping("/add")
	public Result add(@RequestBody(required = true) TypeTemplate typeTemplate) {
		if(typeTemplate.getCustomAttributeItem() == null){
			typeTemplate.setCustomAttributeItem("[]");
		}
		Result result = typeTemplateService.add(typeTemplate);
		return result;
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody TypeTemplate typeTemplate) {
		Result result = typeTemplateService.update(typeTemplate);
		return result;
	}
	
	@GetMapping("/findOne")
	public Result findOne(Integer id) {
		return typeTemplateService.findOne(id);
	}
	
	@DeleteMapping("/delete")
	public Result dele(Long [] ids) {
		Result result = typeTemplateService.delete(ids);
		return result;
	}
	
	@PostMapping("/search")
	public Result search(@RequestBody TypeTemplate typeTemplate, Integer page, Integer rows) {
		Result result = typeTemplateService.listTypeTemplate(typeTemplate, page, rows);
		return result;
	}

	@GetMapping("/selectBrandList")
	public List<Map> selectBrandList(){
		List<Map> selectBrandList = typeTemplateService.selectBrandList();
		return selectBrandList;
	}
	
	@GetMapping("/selectOptionList")
	public List<Map> selectOptionList(HttpServletRequest request) throws UnauthorizedException {
		Map<String, Object> userMap = ssoService.getUser(request);
		if (userMap == null && userMap.isEmpty()) {
			throw new UnauthorizedException();
		}
		Integer shopId = (Integer) userMap.get("shopId");
		List<Map> selectOptionList = typeTemplateService.selectOptionList(shopId);
		return selectOptionList;
	}
}
