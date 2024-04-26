package game.src.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/LoveLetter";
    private static final String USER = "Test";
    private static final String PASSWORD = "Test2";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

}
