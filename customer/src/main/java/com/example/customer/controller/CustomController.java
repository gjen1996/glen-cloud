package com.example.customer.controller;/**
 * @author Glen
 * @create 2019- 06-2019/6/13-10:02
 * @Description
 */

import com.example.customer.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Glen
 * @create 2019/6/13 10:02
 * @Description
 */
@RestController
public class CustomController {
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return testService.test();
    }
}
