package loveletter.src.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {

	private static final String JDBC_URL = "jdbc:sqlite:loveletter/src/database/mybase.db";
    private static Connection connection;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement du pilote JDBC SQLite !");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (DatabaseController.connection == null || DatabaseController.connection.isClosed()) {
            DatabaseController.connection = DriverManager.getConnection(JDBC_URL);
        }
        return DatabaseController.connection;
    }

    public static String getTableName(String sql) {
        String[] words = sql.split("\\s+"); // Divise la requête SQL en mots en utilisant l'espace comme séparateur
        for (String word : words) {
            if (Character.isUpperCase(word.charAt(0)) && !Character.isUpperCase(word.charAt(1))) {
                return word;
            }
        }
        return null; // Retourne null si aucun mot commençant par une majuscule n'est trouvé
    }

    /**
     * To use to INSERT, UPDATE, DELETE, but not to SELECT because there isn't return
     * @param sql
     */
    public static void request(String sql, Object... params) {
        try {
            Connection connection = DatabaseController.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            // Définition des valeurs des paramètres
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            // Exécution de la requête
            int rowsAffected = statement.executeUpdate();
            statement.close();
            connection.close();

            String tableName = getTableName(sql);
            if (sql.contains("CREATE")) {
                System.out.printf("La table %s créé avec succès.%n", tableName);
            } else if (sql.contains("INSERT")) {
                System.out.printf("L'insertion dans %s de %d lignes.%n", tableName, rowsAffected);
            } else if (sql.contains("DROP")) {
                System.out.printf("La table %s a été supprimée avec succès.%n", tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int insertID(String sql, Object... params) {
        try {
            Connection connection = DatabaseController.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Définition des valeurs des paramètres
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            // Exécution de la requête
            int rowsAffected = statement.executeUpdate();

            // Récupération de l'ID généré
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    connection.close();
                    return id;
                }
            }
            connection.close();
            return -1; // Retourne -1 si aucun ID n'a été généré
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Retourne -1 en cas d'erreur
        }
    }

    /**
     * * @param sql
     */
    public static Object[] find(String sql, Object... params) {
        try {
            Connection connection = DatabaseController.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            // Définition des valeurs des paramètres
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            // Exécution de la requête
            ResultSet resultSet = statement.executeQuery();

            // Récupération de l'ID généré
            if (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                Object[] results = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    results[i] = resultSet.getObject(i + 1); // Les index de colonne commencent à 1
                }
                connection.close();
                return results;
            }
            statement.close();
            connection.close();
            return new Object[0];
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Object[][] findAll(String sql, Object... params) {
        try {
            Connection connection = DatabaseController.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            // Définition des valeurs des paramètres
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            // Exécution de la requête
            ResultSet resultSet = statement.executeQuery();

            // Récupération des résultats
            List<Object[]> resultsList = new ArrayList<>();
            while (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = resultSet.getObject(i + 1); // Les index de colonne commencent à 1
                }
                resultsList.add(row);
            }
            connection.close();

            // Conversion de la liste en tableau
            Object[][] results = new Object[resultsList.size()][];
            resultsList.toArray(results);

            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Object[0][];
    }


    public static void deleteTable(String name) {
        String sql = String.format("DROP TABLE IF EXISTS %s", name);
        DatabaseController.request(sql);
    }
}
