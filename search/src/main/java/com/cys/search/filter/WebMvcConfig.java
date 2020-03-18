package com.cys.search.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final List<String> EXCLUDE_PATH = Arrays.asList("/html/**", "css/**", "js/**", "/plugins/**", "/images/**");
    private static final List<String> TOKEN_EXCLUDE_PATH = Arrays.asList("/auth/getAll","/html/**", "css/**", "js/**", "/plugins/**");

//    @Autowired
//    private TokenFilter tokenFilter;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenFilter()).addPathPatterns("/system/**").excludePathPatterns(TOKEN_EXCLUDE_PATH);
    }
}