package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Properties properties = new Properties();
                // Load properties from db.properties file in the classpath
                InputStream inputStream = DBConnUtil.class.getClassLoader().getResourceAsStream("db.proploan.txt");
                if (inputStream == null) {
                    throw new IOException("db.proploan not found in the classpath");
                }
                properties.load(inputStream);

                String url = properties.getProperty("db.url");
                String username = properties.getProperty("db.username");
                String password = properties.getProperty("db.password");

                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the connection
                connection = DriverManager.getConnection(url, username, password);
            } catch (IOException | ClassNotFoundException e) {
                // Handle exceptions
                e.printStackTrace();
            }
        }
        return connection;
    }
}

