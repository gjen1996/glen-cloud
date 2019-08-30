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
import com.glen.appcustomerlogin.service.UserLoginService;
import com.glen.appcustomerlogin.util.CookieUtils;
import com.glen.appcustomerlogin.util.JwtDecrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import com.auth0.jwt.JWT;

@RequestMapping("/user")
@RestController
@Slf4j
public class AppcustomerLoginController {
    @Autowired
    UserLoginService userLoginService;
    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    public JWTEntity login(@Valid UserEntity loginDto, BindingResult bindingResult, HttpServletResponse response) throws Exception {
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
    // 更新token
    @RequestMapping("/getToken")
    public JWTEntity getToken(HttpServletRequest request) throws Exception {
        String token =
        //String token = CookieUtils.getCookie(request, "token");
        log.info("token:"+token);
        if (token == null) {
            // cookie没有获取到token就直接返回错误
            return null;
        }
        // 解密token
        JwtDecrypt jwtDecrypt =new JwtDecrypt();
        DecodedJWT jwt = jwtDecrypt.deToken(token);
        log.info("jwt.getClaim(\"username\").asString():"+jwt.getClaim("username").asString());
        if (StringUtils.isBlank(jwt.getToken())) {
            //如果解密出来的token是空就直接返回错误
            return null;
        }
        // 生成新的token
        UserEntity loginDto =new UserEntity();
        loginDto.setUsername(jwt.getClaim("username").asString());
        loginDto.setPassword(jwt.getClaim("password").asString());
        // JWTEntity jwtReturn = userLoginService.login(loginDto,bindingResult,response);
        JWTEntity jwtReturn = null;

        // 返回成功
        return jwtReturn;
    }
}