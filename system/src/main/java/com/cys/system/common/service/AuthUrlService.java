package com.cys.system.common.service;


import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.AuthUrl;

import java.util.List;
import java.util.Map;

public interface AuthUrlService {
    Map<String,List<AuthUrl>> listAllRoleAuthUrl();

    Result getAllRole();

    Result loadHaveAuth(Integer roleId);

    Result loadNoHaveAuth(Integer roleId);

    Result addAuth(Integer roleId, Integer authId);

    Result removeAuth(Integer roleId, Integer authId);
}
