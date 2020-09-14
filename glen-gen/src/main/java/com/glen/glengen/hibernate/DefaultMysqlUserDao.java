package com.glen.glengen.hibernate;/**
 * @author Glen
 * @create 2020- 09-2020/9/14-11:36
 * @Description
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author Glen
 * @create 2020/9/14 11:36
 * @Description
 */
public class DefaultMysqlUserDao implements MysqlUserDao {

    private final MysqlConnectionManager mysqlConnectionManage;
    private final SqlExecutor sqlExecutor;
    private final ResultHandler resultHandler;
    private final String sql;

    public DefaultMysqlUserDao(MysqlConnectionManager mysqlConnectionManage, SqlExecutor sqlExecutor, ResultHandler resultHandler, String sql) {
        this.mysqlConnectionManage = mysqlConnectionManage;
        this.sqlExecutor = sqlExecutor;
        this.resultHandler = resultHandler;
        this.sql = sql;
    }

    @Override
    public List<MysqlUserConnectionEntity> selectAllMysqlUsers() {
        try {
            Connection connection = mysqlConnectionManage.newConnection();
            try {
                ResultSet resultSet = sqlExecutor.execute(connection, sql);
                return (List<MysqlUserConnectionEntity>) resultHandler.handleResultSet(resultSet);
            } finally {
                mysqlConnectionManage.closeConnection(connection);
            }
        } catch (Exception e) {
            // 暂时忽略异常处理,统一封装为IllegalStateException
            throw new IllegalStateException(e);
        }
    }
}
