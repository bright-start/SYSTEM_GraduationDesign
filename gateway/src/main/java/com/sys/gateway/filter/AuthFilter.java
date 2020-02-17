//package com.sys.gateway.filter;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//
//@Component
//public class AuthFilter implements GlobalFilter, Ordered {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Value("${jwt.secret.key}")
//    private String secretKey;
//
//    @Value("${auth.skip.urls}")
//    private String[] skipAuthUrls;
//
//    @Value("${jwt.blacklist.key.format}")
//    private String jwtBlacklistKeyFormat;
//
//    @Override
//    public int getOrder() {
//        return -100;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String url = exchange.getRequest().getURI().getPath();
//        //跳过不需要验证的路径
//        if (Arrays.asList(skipAuthUrls).contains(url)) {
//            return chain.filter(exchange);
//        }
//        //从请求头中取出token
//        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
//        //未携带token或者token失效
//        if (token == null || token.isEmpty() || isBlackToken(token)) {
//            HttpServletResponse response = (HttpServletResponse)exchange.getResponse();
//            try {
//                response.sendRedirect("http://www.cys.com:9200/sso/sso/html/login.html");
//            } catch (IOException e) {
//            }
//        }
//        //取出token包含的身份
//        String user = verifyJWT(token);
//        //将现在的request，添加当前身份
//        ServerHttpRequest mutableReq = exchange.getRequest().mutate().header("Authorization-User", user).build();
//        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
//        return chain.filter(mutableExchange);
//    }
//
//    /**
//     * JWT验证
//     *
//     * @param token
//     * @return userName
//     */
//    private String verifyJWT(String token) {
//        String userInfo;
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secretKey);
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .withIssuer("MING")
//                    .build();
//            DecodedJWT jwt = verifier.verify(token);
//            userInfo = jwt.getClaim("userInfo").asString();
//        } catch (JWTVerificationException e) {
//            LOGGER.error(e.getMessage(), e);
//            return "";
//        }
//        return userInfo;
//    }
//
//    /**
//     * 判断token是否在黑名单内
//     * @param token
//     * @return
//     */
//    private boolean isBlackToken(String token){
//        assert token != null;
//        return redisTemplate.hasKey(String.format(jwtBlacklistKeyFormat, token));
//    }
//}