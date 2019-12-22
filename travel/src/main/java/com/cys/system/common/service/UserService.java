package com.cys.system.common.service;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.pojo.User;
import com.cys.system.common.pojo.UserFingerprint;

public interface UserService {
    Result applyUser(UserFingerprint userFingerprint);
    Result listUser(Integer page, Integer rows, User user);
    Result getUserById(Integer userId);
}
