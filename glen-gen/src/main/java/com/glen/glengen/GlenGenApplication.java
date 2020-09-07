package com.glen.glengen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration(exclude= HibernateJpaAutoConfiguration.class)
//java.lang.ClassCastException: org.springframework.orm.jpa.EntityManagerHolder cannot be cast to org.springframework.orm.hibernate5.SessionHolder
@ComponentScan("com.glen")
@SpringBootApplication
@EnableCaching
public class GlenGenApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlenGenApplication.class, args);
    }

}
