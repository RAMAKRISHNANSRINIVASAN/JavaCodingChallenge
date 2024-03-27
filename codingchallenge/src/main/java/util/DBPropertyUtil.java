package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class DBPropertyUtil {
	public static String getPropertyString(String filePath) {
        Properties properties = new Properties();
        String connectionString = null;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);

            String hostname = properties.getProperty("db.hostname");
            String dbname = properties.getProperty("db.dbname");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            String port = properties.getProperty("db.port");

            // Check if all required properties are present
            if (hostname != null && dbname != null && username != null && password != null && port != null) {
                // Construct connection string
                connectionString = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname + "?user=" + username + "&password=" + password;
            } else {
                System.err.println("Some required properties are missing.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connectionString;
    }
}
