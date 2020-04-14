package com.cys.search.controller;

import com.cys.search.feign.SystemInterface;
import com.cys.search.pojo.SearchEntity;
import com.cys.search.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SearchController {

    @Resource
    private SystemInterface systemInterface;

    @PostMapping("/_search")
    public Result search(@RequestBody SearchEntity searchEntity) {
        if(searchEntity.getPage() == null){
            searchEntity.setPage(1);
        }
        if(searchEntity.getSize() == null){
            searchEntity.setSize(12);
        }
        return systemInterface.search(searchEntity);
    }
}

