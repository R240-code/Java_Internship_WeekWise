package Assignments_Week4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Simple helper to provide a JDBC Connection.
 * Reads connection properties from system properties:
 *   -db.url (default jdbc:mysql://localhost:3306/edutrack)
 *   -db.user (default root)
 *   -db.pass (default empty)
 *
 * Update these via -D flags or replace defaults with your own values.
 */
public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = System.getProperty("db.url", "jdbc:mysql://localhost:3306/edutrack");
        String user = System.getProperty("db.user", "root");
        String pass = System.getProperty("db.pass", "");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
            // Driver not on classpath; DriverManager may still work if using JDBC 4+ drivers
        }
        return DriverManager.getConnection(url, user, pass);
    }
}
