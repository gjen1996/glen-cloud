package com.glen.glenauthorization.service;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-10:32
 * @Description
 */

import com.glen.glenauthorization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Glen
 * @create 2019/6/28 10:32
 * @Description
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

