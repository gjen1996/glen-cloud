package com.example.customer.Service;
/**
 * @author Glen
 * @create 2019- 06-2019/6/13-10:03
 * @Description
 */


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Glen
 * @create 2019/6/13 10:03
 * @Description
 */

@FeignClient(value="springcloud-server")
public interface TestService {

    @RequestMapping("/test")
    public String test();

}

