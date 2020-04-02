package com.cys.system.common.service;


import com.cys.system.common.pojo.AuthUrl;

import java.util.List;
import java.util.Map;

public interface AuthUrlService {
    Map<String,List<AuthUrl>> listAllRoleAuthUrl();
}
