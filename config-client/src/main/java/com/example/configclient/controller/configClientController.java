package com.example.configclient.controller;/**
 * @author Glen
 * @create 2019- 06-2019/6/17-16:47
 * @Description
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Glen
 * @create 2019/6/17 16:47 
 * @Description
 */
@RestController
@RefreshScope
public class configClientController {
    @Value("${neo.hello}")
    private String hello;

    @RequestMapping("/hello")
    public String from() {
        return this.hello;
    }
}