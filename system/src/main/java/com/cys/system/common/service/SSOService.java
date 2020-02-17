package com.cys.system.common.service;


import com.cys.system.common.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface SSOService {
    Map<String, Object> getUser(HttpServletRequest request);
}
