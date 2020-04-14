package com.cys.system.common.service.impl;

import com.cys.system.common.common.pojo.Result;
import com.cys.system.common.config.OnlyOneClassConfig;
import com.cys.system.common.mapper.AuthUrlMapper;
import com.cys.system.common.mapper.AuthUrlRoleMapper;
import com.cys.system.common.mapper.RoleMapper;
import com.cys.system.common.pojo.AuthUrl;
import com.cys.system.common.service.AuthUrlService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AuthUrlServiceImpl implements AuthUrlService {

    @Resource
    private AuthUrlMapper authUrlMapper;

    @Resource
    private AuthUrlRoleMapper authUrlRoleMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RedisTemplate redisTemplate;

    private static final String ROLE_AUTH_KEY = "roleAuthUrl";

    @Override
    public Map<String, List<AuthUrl>> listAllRoleAuthUrl() {
        List<Map> listRole = roleMapper.listRole();

        Map<String, List<AuthUrl>> authUrlMap = new HashMap<>();
        for (Map map : listRole) {

            String roleName = (String) map.get("role_name");
            Integer roleId = (Integer) map.get("role_id");

            RedisSerializer redisSerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(redisSerializer);
            String json = (String) redisTemplate.opsForHash().get(ROLE_AUTH_KEY, roleName);
            if (json == null) {
                synchronized (this) {
                    json = (String) redisTemplate.opsForHash().get(ROLE_AUTH_KEY, roleName);
                    if (json == null) {
                        List<AuthUrl> authUrls = authUrlMapper.listAuthUrlForRole(roleId);
                        if (authUrls == null || authUrls.isEmpty()) {
                            continue;
                        }
                        String authUrlJson = OnlyOneClassConfig.gson1.toJson(authUrls);
                        redisTemplate.opsForHash().put(ROLE_AUTH_KEY, roleName, authUrlJson);
                        redisTemplate.expire(ROLE_AUTH_KEY, 30, TimeUnit.MINUTES);
                    }
                }
            }
            List<AuthUrl> authUrlList = OnlyOneClassConfig.gson1.fromJson(json, List.class);
            if(authUrlList != null) {
                authUrlMap.put(roleName, authUrlList);
            }
        }
        return authUrlMap;
    }

    @Override
    public Result getAllRole() {
        List<Map> listRole = roleMapper.listRole();
        return new Result().success(listRole);
    }

    @Override
    public Result loadHaveAuth(Integer roleId) {
        List<AuthUrl> list = authUrlRoleMapper.loadAuth(roleId);
        return new Result().success(list);
    }

    @Override
    public Result loadNoHaveAuth(Integer roleId) {
        List<AuthUrl> allAuthUrl = authUrlMapper.getAllAuthUrl();
        List<AuthUrl> list = authUrlRoleMapper.loadAuth(roleId);
        allAuthUrl.removeAll(list);
        return new Result().success(allAuthUrl);
    }

    @Override
    public Result addAuth(Integer roleId, Integer authId) {
        authUrlRoleMapper.addAuth(roleId,authId);
        return new Result().success(200,"添加成功");
    }

    @Override
    public Result removeAuth(Integer roleId, Integer authId) {
        authUrlRoleMapper.removeAuth(roleId,authId);
        return new Result().success(200,"移出成功");
    }
}
