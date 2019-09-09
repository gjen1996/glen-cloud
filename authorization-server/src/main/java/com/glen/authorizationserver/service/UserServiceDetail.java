package com.glen.authorizationserver.service;


import com.glen.authorizationserver.methodservice.AppcustomerLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Glen
 * @create 2019- 06-2019/6/28-11:05
 * @Description
 */

@Service
@Slf4j
public class UserServiceDetail implements UserDetailsService {
    @Autowired
    private AppcustomerLoginService appcustomerLoginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appcustomerLoginService.findByUsername(username);
    }
}
