package com.glen.appserverlogin.service.impl;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:30
 * @Description
 */

import com.glen.appserverlogin.entity.JWT;
import com.glen.appserverlogin.service.AuthServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Glen
 * @create 2019/6/28 11:30 
 * @Description
 */
@Slf4j
@Component
public class AuthServiceClientFallback implements AuthServiceClient {
    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
       log.info("Fallback of getToken is executed");
        return null;
    }
}