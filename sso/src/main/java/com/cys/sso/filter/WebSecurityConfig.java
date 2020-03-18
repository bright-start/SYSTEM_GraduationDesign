package com.cys.sso.filter;

import com.cys.sso.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;

/**
 * 前后不分离配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailServiceImpl userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }


    //框架不识别springboot的server.servlet.context-path
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .and()
                .sessionManagement().sessionFixation().none()
                .and()
                .authorizeRequests()
                .antMatchers("/css/**",
                        "/webfonts/**",
                        "/js/**",
                        "/images/**",
                        "/html/**"
                ).permitAll()
                .anyRequest().permitAll()
                .antMatchers("/sso/**").access("hasRole('SUPPERADMIN')")
                .and()
                .formLogin()
                .loginPage("/sso/html/login.html")
                .loginProcessingUrl("/login")
                .successForwardUrl("/redirectPage")
                .failureForwardUrl("/loginFail")
                .permitAll();
    }

}
