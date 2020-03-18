package com.cys.sso.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cys.sso.config.Config;
import com.cys.sso.pojo.LoginUser;
import com.cys.sso.pojo.Result;
import com.cys.sso.service.TokenService;
import com.cys.sso.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ServerErrorException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Transactional(readOnly = true)
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    private final static Gson gson = new Gson();

    private final static int LOGINSTAUTS = 1;

    private final static String COOKIENAME="SYS-TOKEN";

//    @Value("${jwt.secret.key}")
//    private String secretKey;

//    @Value("${token.expire.time}")
//    private long tokenExpireTime;
//
//    @Value("${refresh.token.expire.time}")
//    private long refreshTokenExpireTime;
//
//    @Value("${jwt.refresh.token.key.format}")
//    private String jwtRefreshTokenKeyFormat;
//
//    @Value("${jwt.blacklist.key.format}")
//    private String jwtBlacklistKeyFormat;

    @Transactional(readOnly = false)
    @Override
    public Result buildToken(String remoteAddress,HttpServletRequest request,HttpServletResponse response) {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //清空springSecurity存储的信息
        SecurityContextHolder.getContext().setAuthentication(null);

//        int status = userService.isLogin(loginUser.getUsername());
//        if (status == 1) {
//            return new Result().success(200, "重复登陆");
//        } else if (status == 3) {
//            return new Result().success(200, "账号受限");
//        }

        Cookie[] cookies = request.getCookies();

        if(cookies != null && cookies.length > 0){
            String token = null;
            for (Cookie cookie : cookies) {
                if(COOKIENAME.equals(cookie.getName())){
                    token = cookie.getValue();
                    break;
                }
            }
            if(token != null){
                redisTemplate.delete(token);
            }
        }

        String token = UUID.randomUUID().toString().replaceAll("-","");
        redisTemplate.opsForValue().set(token,gson.toJson(loginUser));
        redisTemplate.expire(token,30, TimeUnit.MINUTES);

        Cookie cookie = new Cookie(Config.cookieName, token);
        cookie.setMaxAge(30 * 60);
        cookie.setPath("/");
//        cookie.setDomain("cys.com");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);


//        String userInfo = gson.toJson(loginUser);
//        //生成JWT
//        String token = buildJWT(userInfo);
//        //生成refreshToken
//        String refreshToken = UUID.randomUUID().toString().replaceAll("-","");
//        //保存refreshToken至redis，使用hash结构保存使用中的token以及用户标识
//        String refreshTokenKey = String.format(jwtRefreshTokenKeyFormat, refreshToken);
//        redisTemplate.opsForHash().put(refreshTokenKey,
//                "token", token);
//        redisTemplate.opsForHash().put(refreshTokenKey,
//                "userInfo", userInfo);
//        redisTemplate.expire(refreshTokenKey, refreshTokenExpireTime, TimeUnit.MINUTES);


//        userService.changeStatus(loginUser.getUsername(), LOGINSTAUTS);
        if(remoteAddress != null){
            return new Result().success(remoteAddress);
        }

        Collection<? extends GrantedAuthority> authorities = loginUser.getAuthorities();
        Iterator var2 = authorities.iterator();
        if (var2.hasNext()) {
            GrantedAuthority authority = (GrantedAuthority) var2.next();
            String role = authority.getAuthority();
            return role.contains("ADMIN") || role.contains("SHOP") ? (new Result()).success(Config.backPage) : (new Result()).success(Config.indexPage);
        } else {
            return (new Result()).success(200, "服务器繁忙，请稍后再试");
        }
    }

//    /**
//     * 刷新JWT
//     * @param refreshToken
//     * @return
//     */
//    @GetMapping("/token/refresh")
//    public void refreshToken(@RequestParam String refreshToken){
//        String refreshTokenKey = String.format(jwtRefreshTokenKeyFormat, refreshToken);
//        String userInfo = (String)redisTemplate.opsForHash().get(refreshTokenKey, "userInfo");
//
//
//        String newToken = buildJWT(userInfo);
//        //替换当前token，并将旧token添加到黑名单
//        String oldToken = (String)redisTemplate.opsForHash().get(refreshTokenKey,
//                "token");
//        redisTemplate.opsForHash().put(refreshTokenKey, "token", newToken);
//        redisTemplate.opsForValue().set(String.format(jwtBlacklistKeyFormat, oldToken), "",
//                tokenExpireTime, TimeUnit.MILLISECONDS);
//    }
//
//    private String buildJWT(String userInfo){
//        //生成jwt
//        Date now = new Date();
//        Algorithm algo = Algorithm.HMAC256(secretKey);
//        String token = JWT.create()
//                .withIssuer("MING")
//                .withIssuedAt(now)
//                .withExpiresAt(new Date(now.getTime() + tokenExpireTime))
//                .withClaim("userInfo", userInfo)//保存身份标识
//                .sign(algo);
//        return token;
//    }

    @Override
    public Result getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(!(cookies != null && cookies.length > 0)){
            return null;
        }
        String token = null;
        for (Cookie cookie : cookies) {
            if(COOKIENAME.equals(cookie.getName())){
                token = cookie.getValue();
                break;
            }
        }
        if(token == null){
            return null;
        }

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        String tokenJson = (String) redisTemplate.opsForValue().get(token);
        Map<String,Object> userMap = gson.fromJson(tokenJson, Map.class);
        if (!(userMap != null && !userMap.isEmpty())) {
            return new Result().success(null);
        }

        //清除无关信息避免暴漏
        userMap.remove("password");
        userMap.remove("accountNonExpired");
        userMap.remove("accountNonLocked");
        userMap.remove("credentialsNonExpired");
        userMap.remove("enabled");

        redisTemplate.expire(token, 30, TimeUnit.MINUTES);
        return new Result().success(userMap);
    }
}
