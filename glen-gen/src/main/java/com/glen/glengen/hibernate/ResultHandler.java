package com.glen.glengen.hibernate;/**
 * @author Glen
 * @create 2020- 09-2020/9/14-11:33
 * @Description
 */

import com.google.common.collect.Lists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Glen
 * @create 2020/9/14 11:33 
 * @Description
 */
// 结果处理器
public interface ResultHandler<T> {

    T handleResultSet(ResultSet resultSet) throws SQLException;

    ResultHandler<List<MysqlUserConnectionEntity>> X = new ResultHandler<List<MysqlUserConnectionEntity>>() {
        @Override
        public List<MysqlUserConnectionEntity> handleResultSet(ResultSet resultSet) throws SQLException {
            try {
                List<MysqlUserConnectionEntity> result = Lists.newArrayList();
                while (resultSet.next()) {
                    MysqlUserConnectionEntity item = new MysqlUserConnectionEntity();
                    item.setHost(resultSet.getString("Host"));
                    item.setUser(resultSet.getString("User"));
                    result.add(item);
                }
                return result;
            } finally {
                resultSet.close();
            }
        }
    };

}
