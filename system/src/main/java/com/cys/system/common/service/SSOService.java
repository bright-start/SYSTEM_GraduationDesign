package com.cys.system.common.service;


import com.cys.system.common.pojo.User;

import java.io.IOException;

public interface SSOService {
    User getUserByToken(String token);

    String SaveUser(User user);
}
