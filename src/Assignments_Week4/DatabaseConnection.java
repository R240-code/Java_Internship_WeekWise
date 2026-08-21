package Assignments_Week4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/student_management_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "MySQL Connector/J is not added to this project.", e
            );
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}