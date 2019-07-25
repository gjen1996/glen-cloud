package com.glen.appserverlogin.service;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-10:32
 * @Description
 */

import com.glen.appserverlogin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author Glen
 * @create 2019/6/28 10:32 
 * @Description
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

