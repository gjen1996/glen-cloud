package com.glen.authorizationserver.methodservice;/**
 * @author Glen
 * @create 2019- 06-2019/6/24-17:04
 * @Description
 */


import com.glen.authorizationserver.entity.SysUserEntity;
import com.glen.authorizationserver.methodservice.fallback.AppcustomerLoginServiceImplFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Glen
 * @create 2019/6/24 17:04 
 * @Description
 */
@FeignClient(value="app-customer-login", fallback = AppcustomerLoginServiceImplFallback.class)
public interface AppcustomerLoginService {
    @RequestMapping("/user/findByUsername/{username}")
    SysUserEntity findByUsername(@PathVariable String username);
}