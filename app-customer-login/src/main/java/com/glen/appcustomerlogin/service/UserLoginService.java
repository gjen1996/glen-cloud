package com.glen.appcustomerlogin.service;/**
 * @author Glen
 * @create 2019- 08-2019/8/30-11:48
 * @Description
 */

import com.glen.appcustomerlogin.entity.SysUserEntity;
import com.glen.appcustomerlogin.entity.UserLoginDTOEntity;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Glen
 * @create 2019/8/30 11:48 
 * @Description
 */
public interface UserLoginService {
    public UserLoginDTOEntity login(@Valid SysUserEntity loginDto, BindingResult bindingResult, HttpServletResponse response) throws  Exception;
   // public ResponseEntity<OAuth2AccessToken> login(@Valid User loginDto, BindingResult bindingResult, HttpServletResponse response) throws  Exception;

}
