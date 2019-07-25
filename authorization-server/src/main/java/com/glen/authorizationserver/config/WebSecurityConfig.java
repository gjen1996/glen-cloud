package com.glen.authorizationserver.config;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-17:31
 * @Description
 */

import com.glen.authorizationserver.service.UserServiceDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


import javax.servlet.http.HttpServletResponse;

/**
 * @author Glen
 * @create 2019/6/28 17:31
 * @Description
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()  // 使用jwt，可以允许跨域
//                .exceptionHandling() // 错误处理
//                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//                .and()
//                .authorizeRequests()
//                .antMatchers("/oauth/**","/oauth/token_key","/user/register","/user/login").permitAll();
//               // .antMatchers("/**").authenticated()// 所有请求都要认证
////                .and()
////                .httpBasic(); // http_basic方式进行认证
//    }
//
//    @Autowired
//    UserServiceDetail userServiceDetail;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        log.info("daozhelibaocuo1");
//        auth.userDetailsService(userServiceDetail).passwordEncoder( PasswordEncoderFactories.createDelegatingPasswordEncoder());
//        log.info("daozhelibaocuo2");
//    }
@Bean
@Override
protected UserDetailsService userDetailsService() {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    String finalPassword = "{bcrypt}"+bCryptPasswordEncoder.encode("123456");
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername("user_1").password(finalPassword).authorities("USER").build());
    manager.createUser(User.withUsername("user_2").password(finalPassword).authorities("USER").build());

    return manager;
}

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll();
    }
}