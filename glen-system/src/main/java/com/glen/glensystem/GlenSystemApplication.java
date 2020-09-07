package com.glen.glensystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.glen")
@MapperScan("com.glen.glensystem.dao")
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class GlenSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlenSystemApplication.class, args);
    }

}
