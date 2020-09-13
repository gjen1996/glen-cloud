package com.glen.glengen.dao.impl;


import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.dao.CreateTemplateDao;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
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
    public <T> R createTables(JSONObject r) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        log.info("R:"+r);
       // Class entityClass = (Class) Class.forName("com.glen.glengen.entity."+r.getString("classNameStand"));
        Object entityTpye = Class.forName("com.glen.glengen.entity."+r.getString("classNameStand")).newInstance();
        log.info("EntityTpye:"+entityTpye);//SystemUserM(id=null, username=null, password=null, createTime=null)
        log.info("EntityTpye1:"+entityTpye.getClass());//class com.glen.glengen.entity.SystemUserM
        log.info("EntityTpye2:"+entityTpye.getClass().getDeclaringClass());//null
        log.info("EntityTpye3:"+entityTpye.getClass().getComponentType());//null
        log.info("EntityTpye4:"+entityTpye.getClass().getSuperclass());//class java.lang.Object
        Serializable s = null;
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            s = session.save(entityTpye);
            log.info("s:" + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
       return R.ok();

    }
}