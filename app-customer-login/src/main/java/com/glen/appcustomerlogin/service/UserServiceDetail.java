package com.glen.appcustomerlogin.service;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:05
 * @Description
 */

import com.glen.appcustomerlogin.config.BPwdEncoderUtil;
import com.glen.appcustomerlogin.entity.JWT;
import com.glen.appcustomerlogin.entity.User;
import com.glen.appcustomerlogin.entity.UserLoginDTO;
import com.glen.appcustomerlogin.exception.UserLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;


import java.util.Base64;
import java.util.Collections;


/**
 * @author Glen
 * @create 2019/6/28 11:05 
 * @Description
 */
@Service
@Slf4j
public class UserServiceDetail {
    @Value("${security.oauth2.client.client-id}")
    String clientId;
    @Value("${security.oauth2.client.client-secret}")
    String secret;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Autowired(required=false)
    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;

    private AuthServiceClient client;
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
// 注册用户
    public User insertUser(String username, String  password){
        User user=new User();
        user.setUsername(username);
        user.setPassword(BPwdEncoderUtil.BCryptPassword(password));
        return userRepository.save(user);
    }


    public UserLoginDTO login(String username, String password) {
        // 查询数据库
        UserLoginDTO loginDto= new UserLoginDTO();
        User user = userRepository.findByUsername(username);
        loginDto.setUser(user);
        log.info("user--"+loginDto.getUser().getUsername()+"---"+loginDto.getUser().getPassword());
        if (user == null) {
            throw new UserLoginException("error username");
        }

        if(!BPwdEncoderUtil.matches(password,user.getPassword())){
            throw new UserLoginException("error password");
        }
        // 从auth-service获取JWT
        String client_secret = clientId+":"+secret;
//
        client_secret = "Basic "+Base64.getEncoder().encodeToString(client_secret.getBytes());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Authorization",client_secret);
//        log.info("client_secret"+client_secret+"--"+oAuth2ClientProperties.getClientId()+"--"+oAuth2ClientProperties.getClientSecret());
//
//        //授权请求信息
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.put("username", Collections.singletonList(loginDto.getUser().getUsername()));
//        map.put("password", Collections.singletonList(loginDto.getUser().getPassword()));
//        map.put("grant_type", Collections.singletonList("password"));
//
//        map.put("scope", Collections.singletonList("server"));
//        log.info("11233:"+map);
//        //HttpEntity
//        HttpEntity httpEntity = new HttpEntity(map,httpHeaders);
//        //获取 Token
//        ResponseEntity<OAuth2AccessToken> responseEntity=restTemplate.exchange(oAuth2ProtectedResourceDetails.getAccessTokenUri(), HttpMethod.POST,httpEntity, OAuth2AccessToken.class);
//        log.info("responseEntity;"+responseEntity);
          log.info("client_secret"+client_secret);
          JWT jwt = client.getToken(client_secret,"password", username, password);
     //   JWT jwt = client.getToken("Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==","password", username, password);
        log.info("jwt"+jwt);
        if(jwt == null){
            throw new UserLoginException("error internal");
        }
        UserLoginDTO userLoginDTO=new UserLoginDTO();
        userLoginDTO.setJwt(jwt);
        userLoginDTO.setUser(user);
        return userLoginDTO;
    }
}
