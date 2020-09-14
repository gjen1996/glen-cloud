package com.glen.glengen.hibernate;/**
 * @author Glen
 * @create 2020- 09-2020/9/14-11:29
 * @Description
 */

import com.google.common.collect.Lists;

import java.sql.*;

/**
 * @author Glen
 * @create 2020/9/14 11:29 
 * @Description
 */
public interface MysqlConnectionManager {
        String USER_NAME = "root";
        String PASS_WORD = "root";
        String URL = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false";

        Connection newConnection() throws SQLException;

        void closeConnection(Connection connection);

    MysqlConnectionManager X = new MysqlConnectionManager() {

            @Override
            public Connection newConnection() throws SQLException {
                return DriverManager.getConnection(URL, USER_NAME, PASS_WORD);
            }

            @Override
            public void closeConnection(Connection connection) {
                try {
                    connection.close();
                } catch (Exception ignore) {

                }
            }
        };
    }



