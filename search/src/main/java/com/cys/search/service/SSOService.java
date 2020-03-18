package com.cys.search.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface SSOService {
    Map<String, Object> getUser(HttpServletRequest request);
}
