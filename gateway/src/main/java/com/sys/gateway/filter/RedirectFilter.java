package com.sys.gateway.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;

@Component
public class RedirectFilter implements GlobalFilter, Order {

    private static final Log log = LogFactory.getLog(GatewayFilter.class);
    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";
    private static final String DEFAULT_URL = "/";
    private static final String BACK_DEFAULT_URL = "/back";
    protected String REDIRECT_Path = "http://www.cys.com:9200/search/search/html/index.html";
    protected String BACK_REDIRECT_Path = "http://www.cys.com:9200/system/system/html/index.html";

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (DEFAULT_URL.equals(exchange.getRequest().getURI().getRawPath())) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.SEE_OTHER);
            response.getHeaders().set(HttpHeaders.LOCATION, REDIRECT_Path);
            return response.setComplete();
        }
//        if (BACK_DEFAULT_URL.equals(exchange.getRequest().getURI().getRawPath())) {
//            ServerHttpResponse response = exchange.getResponse();
//            response.setStatusCode(HttpStatus.SEE_OTHER);
//            response.getHeaders().set(HttpHeaders.LOCATION, BACK_REDIRECT_Path);
//            return response.setComplete();
//        }

        exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
        return chain.filter(exchange).then(
            Mono.fromRunnable(() -> {
                Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                if (startTime != null) {
                    log.info(exchange.getRequest().getURI().getRawPath() + ": " + (System.currentTimeMillis() - startTime) + "ms");
                }
            })
        );
    }

    @Override
    public int value() {
        return 0;
    }
}
