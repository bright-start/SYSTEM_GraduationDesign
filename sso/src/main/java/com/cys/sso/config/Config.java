package com.cys.sso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class Config {


    public static String backPage;

    public static String index;

    public static String testPage;


    public static String cookieName;

    @Value("${redirectPage1}")
    public void setBackPage(String backPage) {
        Config.backPage = backPage;
    }

    @Value("${redirectPage2}")
    public void setIndex(String index) {
        Config.index = index;
    }

    @Value("${testPage}")
    public void setTestPage(String testPage) {
        Config.testPage = testPage;
    }

    @Value("${cookieName}")
    public void setCookieName(String cookieName) {
        Config.cookieName = cookieName;
    }
}
