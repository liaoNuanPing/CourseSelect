package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

    private static final String URL = PropertiesUtils.getPropertiesValue("db.properties","jdbc.url");
    private static final String USER = PropertiesUtils.getPropertiesValue("db.properties","jdbc.username");
    private static final String PASSWORD = PropertiesUtils.getPropertiesValue("db.properties","jdbc.password");
    private static Connection conn;

    static {
        try {
            //1.加载数据库 驱动
            Class.forName(PropertiesUtils.getPropertiesValue("db.properties","jdbc.driver"));
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