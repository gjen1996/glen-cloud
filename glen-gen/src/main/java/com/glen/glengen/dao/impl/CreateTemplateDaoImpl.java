package com.glen.glengen.dao.impl;


import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.GlenGenApplication;
import com.glen.glengen.config.HibernateConfig;
import com.glen.glengen.dao.CreateTemplateDao;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;

@Slf4j
@Service
public class CreateTemplateDaoImpl extends Object implements CreateTemplateDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     * @author Glen
     * @date 2020/9/10 13:44
     * @Description
     */
    public Session getCurrentSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
    }

    @Override
    public <T> R createTables(JSONObject param, Object entityTpye) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // Class entityClass = (Class) Class.forName("com.glen.glengen.entity."+r.getString("classNameStand"));
        //返回与带有给定字符串名的类 或接口相关联的 Class 对象。
        Class clz = Class.forName(param.getString("packageName") + "." + param.getString("classNameStand"));
        log.info("clz:" + clz);
        Object o = clz.newInstance();
        log.info("o:"+o);
        Serializable s = null;
        HibernateConfig hibernateConfig =new HibernateConfig();
        hibernateConfig.sessionFactory();
        Session session =getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            session.load(o,"1");
            s = session.save(o);
            log.info("s:" + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        return R.ok();

    }
}