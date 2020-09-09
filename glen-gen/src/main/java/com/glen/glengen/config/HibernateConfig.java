package com.glen.glengen.config;




import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author 盖玉成
 * @date 2020/9/7 16:38
 * @description HibernateP配置
 */
@Configuration
@Slf4j
@PropertySource(value = { "classpath:bootstrap.yml" })
public class HibernateConfig {
    @Autowired
    private Environment environment;
    @Autowired
    private DataSource dataSource;
    //session factory
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        log.info("data:"+dataSource());
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.glen");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
    // 数据源配置
    @Bean
    public  DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.druid.driver-class-name"));
        dataSource.setUrl(environment.getRequiredProperty("spring.datasource.druid.url"));
        dataSource.setUsername(environment.getRequiredProperty("spring.datasource.druid.username"));
        dataSource.setPassword(environment.getRequiredProperty("spring.datasource.druid.password"));
        return dataSource;
    }
    //获取hibernate配置
    private  Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.current_session_context_class", environment.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
        properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.setProperty("hibernate.show-sql", environment.getProperty("spring.jpa.properties.hibernate.show-sql"));
        properties.setProperty("hibernate.cache.use_second_level_cache", environment.getProperty("spring.jpa.properties.hibernate.cache.use_second_level_cache"));
        properties.setProperty("hibernate.cache.use_query_cache", environment.getProperty("spring.jpa.properties.hibernate.cache.use_query_cache"));
        return properties;
    }
//    // 事务管理
//    @Bean
//    @Autowired
//    public HibernateTransactionManager transactionManager(SessionFactory sf) {
//        HibernateTransactionManager txManager = new HibernateTransactionManager();
//        txManager.setSessionFactory(sf);
//        return txManager;
//    }

//
//    public  Session currentSession() throws HibernateException {
//        Session s = (Session)session.get();
//        //Open a new Session,if this Thread has none yet
//        if(s == null || !s.isOpen()) {
//            s = this.sessionFactory().oopenSession();
//            session.set(s);
//        }
//        return s;
//    }
//
//    public  void closeSession() throws HibernateException {
//        Session s = (Session)session.get();
//        session.set(null);
//        if(s != null) {
//            s.close();
//        }
//    }
}
