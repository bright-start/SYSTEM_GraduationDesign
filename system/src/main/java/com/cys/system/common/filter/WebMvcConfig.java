package com.cys.system.common.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private IPFilter ipFilter;

    @Autowired
    private TokenFilter tokenFilter;

    private static final List<String> EXCLUDE_PATH = Arrays.asList("/html/**", "css/**", "js/**", "/plugins/**", "/images/**","/ip/**");
    private static final List<String> TOKEN_EXCLUDE_PATH = Arrays.asList("/getToken","/html/**", "css/**", "js/**", "/plugins/**", "/ip/**");

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipFilter).addPathPatterns("/system/**").excludePathPatterns(EXCLUDE_PATH);
        registry.addInterceptor(tokenFilter).addPathPatterns("/system/**").excludePathPatterns(TOKEN_EXCLUDE_PATH);
    }

}