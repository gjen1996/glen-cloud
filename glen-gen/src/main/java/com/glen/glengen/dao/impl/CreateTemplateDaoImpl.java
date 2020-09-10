package com.glen.glengen.dao.impl;


import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.dao.CreateTemplateDao;
import com.glen.glengen.entity.SysUserEntity;
import com.glen.glengen.util.HibernateBaseDao;
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
public class CreateTemplateDaoImpl extends HibernateBaseDao implements CreateTemplateDao {

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
    public R createTable(JSONObject r) {
        log.info("jinrucreateTable");
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(1);
        log.info("sysUserEntity:" + sysUserEntity);
        Serializable s = null;
        log.info("这个是session" + getSession());
        //Session session1 = getSession1();
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();
        try {
            s = session.save(sysUserEntity);
            log.info("s:" + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        return R.ok();
    }
}