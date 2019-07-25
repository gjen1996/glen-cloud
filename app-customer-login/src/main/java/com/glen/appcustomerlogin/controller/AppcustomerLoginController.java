//package com.glen.appcustomerlogin.controller;
//
//import com.glen.appcustomerlogin.entity.User;
//import com.glen.appcustomerlogin.entity.UserLoginDTO;
//import com.glen.appcustomerlogin.service.UserServiceDetail;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpMethod;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Enumeration;
//import java.util.UUID;
//
///**
// * @author Glen
// * @create 2019- 06-2019/6/24-16:32
// * @Description
// */
//@RestController
//@EnableResourceServer
//@Slf4j
//@RequestMapping("/user")
//public class AppcustomerLoginController {
//    @Autowired
//    UserServiceDetail userServiceDetail;
//    @Value("${server.port}")
//    String port;
//
//    @RequestMapping("/test")
//    public String test(HttpServletRequest request) {
//        return "Hello,world，恭喜您调用成功了，这个是appServerFirst,port为：" + port;
//    }
//
//    @RequestMapping("/totest")
//    public String test1() {
//        return "Hello,world，恭喜您调用成功了，这个测试二是appServerFirst,port为：" + port;
//    }
//
//
//
//    @PostMapping("/register")
//    public User postUser(@RequestParam("username") String username,
//                         @RequestParam("password") String password) {
//        return userServiceDetail.insertUser(username, password);
//    }
//
//    @PostMapping("/login")
//    public UserLoginDTO  login(@RequestParam("username") String username,
//                                                   @RequestParam("password") String password) {
//        log.info("login_username---password"+username+"---"+password);
//        return userServiceDetail.login(username, password);
//    }
//
//    @RequestMapping(value = "/foo", method = RequestMethod.GET)
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public String getFoo() {
//        return "i'm foo, " + UUID.randomUUID().toString();
//    }
//}
