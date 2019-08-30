package com.glen.appcustomerlogin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.glen")
@MapperScan("com.glen.appcustomerlogin.dao")
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class AppCustomerLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppCustomerLoginApplication.class, args);
    }

}
