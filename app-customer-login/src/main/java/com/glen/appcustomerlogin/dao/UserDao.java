package com.glen.appcustomerlogin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.glen.appcustomerlogin.entity.UserEntity;

/**
 * @author Glen
 * @create 2019/6/28 10:32 
 * @Description
 */
public interface UserDao extends BaseMapper<UserEntity> {
    UserEntity findByUsername(String username);
}

