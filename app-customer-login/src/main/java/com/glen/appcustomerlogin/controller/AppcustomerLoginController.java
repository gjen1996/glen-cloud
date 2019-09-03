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
import com.auth0.jwt.interfaces.DecodedJWT;
import com.glen.appcustomerlogin.config.BPwdEncoderUtil;
import com.glen.appcustomerlogin.dao.UserDao;
import com.glen.appcustomerlogin.entity.JWTEntity;
import com.glen.appcustomerlogin.entity.UserEntity;
import com.glen.appcustomerlogin.entity.UserLoginDTOEntity;
import com.glen.appcustomerlogin.service.UserLoginService;
import com.glen.appcustomerlogin.util.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
@Slf4j
public class AppcustomerLoginController {
    @Autowired
    UserLoginService userLoginService;
    @Autowired
    private UserDao userDao;
    @Autowired
    HttpServletRequest request;
    @RequestMapping("/login")
    public UserLoginDTOEntity login(@Valid UserEntity loginDto, BindingResult bindingResult, HttpServletResponse response) throws Exception {
        userLoginService.login(loginDto,bindingResult,response);
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

       log.info("这是："+"--"+userLoginService.login(loginDto,bindingResult,response));
        return userLoginService.login(loginDto,bindingResult,response);
    }
    @PostMapping("/register")
    public UserEntity postUser(@RequestParam("username") String username,
                               @RequestParam("password") String password) {
        UserEntity user=new UserEntity();
        user.setUsername(username);
        user.setPassword(BPwdEncoderUtil.BCryptPassword(password));
        userDao.insert(user);
        return user;
    }
    @RequestMapping("/foo")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getFoo() {
        return "程序猿小哥哥调用成功";
    }

    @RequestMapping("/getToken")
    public String getToken(){
        String token = CookieUtils.getCookie(request, "token");
        String userinfo = CookieUtils.getCookie(request, "userinfo");
        log.info("userinfo:"+userinfo+"--token:"+token);
        return null;

    }
}