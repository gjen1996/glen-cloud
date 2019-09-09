package com.glen.authorizationserver.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.glen.authorizationserver.entity.SysUserEntity;

/**
 * @author Glen
 * @create 2019/6/28 10:32 
 * @Description
 */
public interface UserDao extends BaseMapper<SysUserEntity> {
    SysUserEntity findByUsername(String username);
}

