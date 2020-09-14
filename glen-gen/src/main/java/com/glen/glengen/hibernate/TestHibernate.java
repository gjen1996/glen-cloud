package com.glen.glengen.hibernate;/**
 * @author Glen
 * @create 2020- 09-2020/9/14-16:56
 * @Description
 */

import com.alibaba.fastjson.JSON;
import com.glen.glengen.compiler.JdkCompiler;

/**
 * @author Glen
 * @create 2020/9/14 16:56 
 * @Description
 */
public class TestHibernate {
    static String SOURCE_CODE = "package club.throwable.compile;\n" +
            "import java.sql.Connection;\n" +
            "import java.sql.ResultSet;\n" +
            "import java.util.List;\n" +
            "\n" +
            "public class DefaultMysqlInfoMapper implements MysqlInfoMapper {\n" +
            "\n" +
            "    private final ConnectionManager connectionManager;\n" +
            "    private final SqlExecutor sqlExecutor;\n" +
            "    private final ResultHandler resultHandler;\n" +
            "    private final String sql;\n" +
            "\n" +
            "    public DefaultMysqlInfoMapper(ConnectionManager connectionManager,\n" +
            "                                  SqlExecutor sqlExecutor,\n" +
            "                                  ResultHandler resultHandler,\n" +
            "                                  String sql) {\n" +
            "        this.connectionManager = connectionManager;\n" +
            "        this.sqlExecutor = sqlExecutor;\n" +
            "        this.resultHandler = resultHandler;\n" +
            "        this.sql = sql;\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public List<MysqlUser> selectAllMysqlUsers() {\n" +
            "        try {\n" +
            "            Connection connection = connectionManager.newConnection();\n" +
            "            try {\n" +
            "                ResultSet resultSet = sqlExecutor.execute(connection, sql);\n" +
            "                return (List<MysqlUser>) resultHandler.handleResultSet(resultSet);\n" +
            "            } finally {\n" +
            "                connectionManager.closeConnection(connection);\n" +
            "            }\n" +
            "        } catch (Exception e) {\n" +
            "            // 暂时忽略异常处理,统一封装为IllegalStateException\n" +
            "            throw new IllegalStateException(e);\n" +
            "        }\n" +
            "    }\n" +
            "}\n";

    static String SQL = "SELECT Host,User FROM mysql.user";

    public static void main(String[] args) throws Exception {
        MysqlUserDao mysqlInfoMapper = JdkCompiler.compile(
                "com.glen.glengen.hibernate",
                "DefaultMysqlUserDao",
                SOURCE_CODE,
                new Class[]{MysqlConnectionManager.class, SqlExecutor.class, ResultHandler.class, String.class},
                new Object[]{MysqlConnectionManager.X, SqlExecutor.X, ResultHandler.X, SQL});
        System.out.println(JSON.toJSONString(mysqlInfoMapper.selectAllMysqlUsers()));
    }
}
