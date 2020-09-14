package com.glen.glengen.hibernate;/**
 * @author Glen
 * @create 2020- 09-2020/9/14-11:31
 * @Description
 */

import java.util.List;

/**
 * @author Glen
 * @create 2020/9/14 11:31
 * @Description
 */
public interface MysqlUserDao {
    List<MysqlUserConnectionEntity> selectAllMysqlUsers();
}

