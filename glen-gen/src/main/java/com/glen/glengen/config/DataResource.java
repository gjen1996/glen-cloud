package com.glen.glengen.config;/**
 * @author Glen
 * @create 2020- 09-2020/9/10-13:01
 * @Description
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author Glen
 * @create 2020/9/10 13:01 
 * @Description
 */
public class DataResource {
    @Value("${spring.jpa.properties.hibernate.current_session_context_class}")
    public  String current_session_context_class;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    public  String hibernate_ddl_auto;
    @Value("${spring.jpa.properties.hibernate.show-sql}")
    public  String hibernate_show_sql;
    @Value("${spring.jpa.properties.hibernate.cache.use_second_level_cache}")
    public  String cache_use_second_level_cache;
    @Value("${spring.jpa.properties.hibernate.cache.use_query_cache}")
    public  String cache_use_query_cache;
    @Bean
    public String getCurrent_session_context_class() {
        return current_session_context_class;
    }
    @Bean
    public String getHibernate_ddl_auto() {
        return hibernate_ddl_auto;
    }@Bean
    public String getHibernate_show_sql() {
        return hibernate_show_sql;
    }@Bean
    public String getCache_use_second_level_cache() {
        return cache_use_second_level_cache;
    }@Bean
    public String getCache_use_query_cache() {
        return cache_use_query_cache;
    }



}
