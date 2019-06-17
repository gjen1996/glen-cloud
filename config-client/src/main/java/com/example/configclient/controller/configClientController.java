package com.example.configclient.controller;/**
 * @author Glen
 * @create 2019- 06-2019/6/17-16:47
 * @Description
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
/**
 * @author Glen
 * @create 2019/6/17 16:47 
 * @Description
 */
public class configClientController {
    @Value("test")
    private String hello;

    @RequestMapping("/hello")
    public String from() {
        return this.hello;
    }
}