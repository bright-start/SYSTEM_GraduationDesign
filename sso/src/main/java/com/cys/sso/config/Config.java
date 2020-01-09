package com.cys.sso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class Config {

    @Value("${redirectPage1}")
    public static String backPage;
    @Value("${redirectPage2}")
    public static String index;
    @Value("${testPage}")
    public static String testPage;

    @Value("${cookieName}")
    public static String cookieName;
}
