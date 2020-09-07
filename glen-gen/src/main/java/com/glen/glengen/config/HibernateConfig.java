package com.glen.glengen.config;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author 盖玉成
 * @date 2020/9/7 16:38
 * @description HibernateP配置
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.glen" })
@PropertySource(value = { "classpath:bootstrap.yml" })
public class HibernateConfig {

    @Autowired
    private Environment environment;
    //session factory
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.glen.entity" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
    // 数据源配置
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getRequiredProperty("spring.datasource..username"));
        dataSource.setPassword(environment.getRequiredProperty("spring.datasource..password"));
        return dataSource;
    }
    //获取hibernate配置
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("spring.jpa.hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("spring.jpa.hibernate.show-sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("spring.jpa.hibernate.format_sql"));
        return properties;
    }
    // 事务管理
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sf) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sf);
        return txManager;
    }
}
