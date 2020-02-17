package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.mapper.BrandMapper;
import com.cys.system.common.mapper.SpecificationMapper;
import com.cys.system.common.mapper.TypeTemplateMapper;
import com.cys.system.common.pojo.Brand;
import com.cys.system.common.pojo.Specification;
import com.cys.system.common.pojo.TypeTemplate;
import com.cys.system.common.service.TypeTemplateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = true, rollbackFor = {Exception.class})
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private SpecificationMapper specificationMapper;

    @Resource
    private TypeTemplateMapper typeTemplateMapper;

    @Transactional(readOnly = false)
    @Override
    public Result add(TypeTemplate typeTemplate) {
        typeTemplateMapper.insertTypeTemplate(typeTemplate);
        return new Result().success();
    }

    @Transactional(readOnly = false)
    @Override
    public Result update(TypeTemplate typeTemplate) {
        typeTemplateMapper.updateTypeTemplateByTemplate(typeTemplate);
        return new Result().success();
    }

    @Override
    public Result findOne(Integer id) {
        TypeTemplate typeTemplate = typeTemplateMapper.findTypeTemplateById(id);
        return new Result().success(typeTemplate);
    }

    @Transactional(readOnly = false)
    @Override
    public Result delete(Long[] ids) {
        for (Long id : ids) {
            typeTemplateMapper.deleteTypeTemplateById(id);
        }
        return new Result().success("删除成功");
    }

    @Override
    public Result listTypeTemplate(TypeTemplate typeTemplate, Integer page, Integer rows) {
        long totalCount = typeTemplateMapper.count(typeTemplate);

        if (totalCount > 0) {
            int pageNum = (int) Math.ceil(totalCount * 1.0 / rows);

            if (page >= pageNum) {
                page = pageNum;
            }

            Integer start = (page - 1) * rows;
            PageHelper.startPage(start, rows);
            List<TypeTemplate> typeTemplates = typeTemplateMapper.listTypeTemplate(typeTemplate);

            if (typeTemplates != null && !typeTemplates.isEmpty()) {
                PageInfo<TypeTemplate> pageInfo = new PageInfo<>(typeTemplates);
                pageInfo.setTotal(totalCount);
                pageInfo.setPageNum(pageNum);


                return new Result().success(pageInfo);
            }
        }
        return new Result().success("无数据");
    }

    @Override
    public List<Map> selectOptionList(Integer shopId) {
        Specification specification = new Specification();
        specification.setShopId(shopId);
        List<Specification> specifications = specificationMapper.listSpecification(specification);

        List<Map> list = new LinkedList<>();
        for (Specification specification1 : specifications) {
            Map map = new LinkedHashMap<>();
            map.put("id",specification1.getId());
            map.put("text",specification1.getSpecificationType());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map> selectBrandList() {
        List<Brand> brands = brandMapper.listBrand(new Brand());
        List<Map> list = new LinkedList<>();
        for (Brand brand : brands) {
            Map map = new LinkedHashMap();
            map.put("id",brand.getId());
            map.put("text",brand.getBrandName());
            list.add(map);
        }
        return list;
    }
}
