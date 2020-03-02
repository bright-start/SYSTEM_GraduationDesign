package com.cys.system.common.service.impl;

import com.cys.system.common.mapper.AuthUrlMapper;
import com.cys.system.common.pojo.AuthUrl;
import com.cys.system.common.service.AuthUrlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AuthUrlServiceImpl implements AuthUrlService {

    @Resource
    private AuthUrlMapper authUrlMapper;

    @Override
    public List<AuthUrl> getAllAuthUrl() {
        return authUrlMapper.getAllAuthUrl();
    }
}
