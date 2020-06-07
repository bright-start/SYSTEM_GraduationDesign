package com.cys.sso.security;

import com.cys.sso.security.filter.MobileCodeAuthenticationProcessingFilter;
import com.cys.sso.security.provider.MobileCodeAuthenticationProvider;
import com.cys.sso.security.provider.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider())
                .authenticationProvider(mobileCodeAuthenticationProvider());
    }

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
                        "/html/**",
                        "/login",
                        "/sendSms",
                        "/upload",
                        "/registry",
                        "/shop_registry"
                ).permitAll()
                .anyRequest().permitAll()
//                .antMatchers("/sso/**").access("hasRole('SUPPERADMIN')")
                .and()
                .formLogin()
                .loginPage("/sso/html/login.html")
                .loginProcessingUrl("/login")
                .successForwardUrl("/redirectPage")
                .failureForwardUrl("/loginFail")
                .permitAll();
        http.addFilterBefore(mobileCodeAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public MobileCodeAuthenticationProcessingFilter mobileCodeAuthenticationProcessingFilter() throws Exception {
        MobileCodeAuthenticationProcessingFilter filter = new MobileCodeAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    public UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider() {
        return new UsernamePasswordAuthenticationProvider();
    }

    @Bean
    public MobileCodeAuthenticationProvider mobileCodeAuthenticationProvider() {
        return new MobileCodeAuthenticationProvider();
    }

}
