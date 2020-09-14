package com.glen.glengen.hibernate;/**
 * @author Glen
 * @create 2020- 09-2020/9/14-11:32
 * @Description
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Glen
 * @create 2020/9/14 11:32
 * @Description
 */
// 执行器
public interface SqlExecutor {
    ResultSet execute(Connection connection, String sql) throws SQLException;
    SqlExecutor X = new SqlExecutor() {
        @Override
        public ResultSet execute(Connection connection, String sql) throws SQLException {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            return statement.getResultSet();
        }
    };
}
