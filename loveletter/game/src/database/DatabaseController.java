package game.src.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController {

	private static final String JDBC_URL = "jdbc:sqlite:loveletter/game/src/database/mybase.db";
    private static Connection connection;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement du pilote JDBC SQLite !");
            e.printStackTrace();
        }
    }

    public static void openConnection() throws SQLException {
        if (DatabaseController.connection == null) {
            DatabaseController.connection = DriverManager.getConnection(JDBC_URL);
        }
    }

    public static void closeConnection() throws SQLException {
        DatabaseController.connection.close();
    }

    public static void request(String sql) {
        try {
            Statement statement = DatabaseController.connection.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTable(String name) {
        String sql = String.format("DROP TABLE IF EXISTS %s", name);
        DatabaseController.request(sql);
    }
}
