package com.cys.system.common.service.impl;

import com.cys.system.common.config.OnlyOneClassConfig;
import com.cys.system.common.mapper.AuthUrlMapper;
import com.cys.system.common.mapper.RoleMapper;
import com.cys.system.common.pojo.AuthUrl;
import com.cys.system.common.service.AuthUrlService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthUrlServiceImpl implements AuthUrlService {

    @Resource
    private AuthUrlMapper authUrlMapper;

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
            String json = (String) redisTemplate.opsForHash().get(ROLE_AUTH_KEY, roleId);
            if (json == null) {
                synchronized (this) {
                    json = (String) redisTemplate.opsForHash().get(ROLE_AUTH_KEY, roleId);
                    if (json == null) {
                        List<AuthUrl> authUrls = authUrlMapper.listAuthUrlForRole(roleId);
                        if (authUrls == null || authUrls.isEmpty()) {
                            continue;
                        }
                        String authUrlJson = OnlyOneClassConfig.gson.toJson(authUrls);
                        redisTemplate.opsForHash().put(ROLE_AUTH_KEY, roleId, authUrlJson);
                        redisTemplate.expire(ROLE_AUTH_KEY, 30, TimeUnit.MINUTES);
                    }
                }
            }
            List<AuthUrl> authUrlList = OnlyOneClassConfig.gson.fromJson(json, (Type) AuthUrl.class);
            authUrlMap.put(roleName, authUrlList);
        }
        return authUrlMap;
    }
}
