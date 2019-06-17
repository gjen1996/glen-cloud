package com.example.server.controller;/**
 * @author Glen
 * @create 2019- 06-2019/6/13-14:54
 * @Description
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Glen
 * @create 2019/6/13 14:54 
 * @Description
 */
@RestController
public class TestController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/test")
    public String test(){
        return "server被调用了！port为：" +port;
    }
}
