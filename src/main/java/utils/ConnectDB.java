package utils;


import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

    private static final String URL = PropertiesUtils.getPropertiesValue("db.properties","jdbc.url");
    private static final String USER = PropertiesUtils.getPropertiesValue("db.properties","jdbc.username");
    private static final String PASSWORD = PropertiesUtils.getPropertiesValue("db.properties","jdbc.password");
    private static final String DRIVER = PropertiesUtils.getPropertiesValue("db.properties","jdbc.driver");
    private static Connection conn;
    private static final Logger logger = LoggerUtlis.getLogger(ConnectDB.class);


    static {
        try {
            logger.debug(DRIVER);
            logger.debug(URL);
            logger.debug(USER);
            logger.debug(PASSWORD);
            //1.加载数据库 驱动
            Class.forName(DRIVER);
            //2.获得 数据库 连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }
}