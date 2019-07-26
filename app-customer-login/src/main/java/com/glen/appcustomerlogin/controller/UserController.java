package com.glen.appcustomerlogin.controller;/**
 * @author Glen
 * @create 2019- 07-2019/7/9-17:21
 * @Description
 */

/**
 * @author Glen
 * @create 2019/7/9 17:21
 * @Description
 */
import com.glen.appcustomerlogin.config.BPwdEncoderUtil;
import com.glen.appcustomerlogin.entity.User;
import com.glen.appcustomerlogin.entity.UserLoginDTO;
import com.glen.appcustomerlogin.service.UserRepository;
import com.glen.appcustomerlogin.service.UserServiceDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;
    @Autowired
    UserServiceDetail userServiceDetail;
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Value("${security.oauth2.client.grant-type}")
    String grantType;
    @Value("${security.oauth2.client.scope}")
    String scope;
    @Value("${security.oauth2.client.access-token-uri}")
    String accessTokenUri;
    @Value("${security.oauth2.client.client-id}")
    String clientId;
    @Value("${security.oauth2.client.client-secret}")
    String secret;

    @RequestMapping("/login")
    public ResponseEntity<OAuth2AccessToken> login(@Valid User loginDto, BindingResult bindingResult,HttpServletResponse response) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new Exception("登录信息错误，请确认后再试");
        }

        User user = userRepository.findByUsername(loginDto.getUsername());

        if (null == user) {
            throw new Exception("用户为空，出错了");
        }

        if (!BPwdEncoderUtil.matches(loginDto.getPassword(), user.getPassword().replace("{bcrypt}",""))) {
            throw new Exception("密码不正确");
        }

        String client_secret =oAuth2ClientProperties.getClientId()+":"+oAuth2ClientProperties.getClientSecret();

        client_secret = "Basic "+Base64.getEncoder().encodeToString(client_secret.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization",client_secret);

        //授权请求信息
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("username", Collections.singletonList(loginDto.getUsername()));
        map.put("password", Collections.singletonList(loginDto.getPassword()));
        map.put("grant_type", Collections.singletonList(oAuth2ProtectedResourceDetails.getGrantType()));
        map.put("scope", oAuth2ProtectedResourceDetails.getScope());
        //HttpEntity
        HttpEntity httpEntity = new HttpEntity(map,httpHeaders);
        //获取 Token

         log.info("client_secret:"+client_secret+"loginDto.getUsername:"+loginDto.getUsername()+"--loginDto.getUsername:"+loginDto.getPassword()+"123:"+oAuth2ProtectedResourceDetails.getAccessTokenUri()+"httpEntity:"+httpEntity+"OAuth2AccessToken.class:"+OAuth2AccessToken.class);
        ResponseEntity<OAuth2AccessToken> re =restTemplate.exchange(oAuth2ProtectedResourceDetails.getAccessTokenUri(), HttpMethod.POST,httpEntity,OAuth2AccessToken.class);
        if (re.getStatusCode() != HttpStatus.OK) {
            log.debug("failed to authenticate user with OAuth2 token endpoint, status: {}",
                    re.getStatusCodeValue());
            throw new HttpClientErrorException(re.getStatusCode());
        }
        OAuth2AccessToken oAuth2AccessToken = re.getBody();
        log.info("re----"+re);
        return restTemplate.exchange(accessTokenUri, HttpMethod.POST,httpEntity,OAuth2AccessToken.class);

    }
    @PostMapping("/register")
    public User postUser(@RequestParam("username") String username,
                         @RequestParam("password") String password) {
        return userServiceDetail.insertUser(username, password);
    }
}