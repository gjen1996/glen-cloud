package com.glen.glengen.dao.impl;


import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.dao.MappingDao;
import com.glen.glengen.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Slf4j
public class MappingDaoImpl implements MappingDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     * 开启一个session
     * 需要手动开关session
     *
     * @return
     */
    public Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    /**
     * 托管给spring 需要引入一个配置
     *
     * @return
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
        Session session = getCurrentSession();
        try {
            s = session.save(sysUserEntity);
            log.info("s:" + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }
}