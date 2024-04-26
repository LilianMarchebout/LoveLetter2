package game.src.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManipulation {
    
    public static void modifyData(Connection connection, String key, int value) {
    	// Requête SQL de mise à jour
        String updateQuery = "UPDATE Frequency SET card = ? WHERE freq = ?";
    	try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {


            // Remplacer les valeurs des paramètres
            preparedStatement.setString(1, key);
            preparedStatement.setInt(2, value);
            
            // Exécuter la requête de mise à jour
            int rowsAffected = preparedStatement.executeUpdate();

            // Vérifier le nombre de lignes affectées
            if (rowsAffected > 0) {
                System.out.println("Mise à jour réussie !");
            } else {
                System.out.println("Aucune ligne mise à jour.");
            }
  
            
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertData(Connection connection, String key) {
    	// Requête SQL de mise à jour
        String updateQuery = "INSERT INTO Frequency (card, freq) VALUES (?, ?)";
    	try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {


            // Remplacer les valeurs des paramètres
            preparedStatement.setString(1, key);
            preparedStatement.setInt(2, 0);
            
            // Exécuter la requête de mise à jour
            int rowsAffected = preparedStatement.executeUpdate();

            // Vérifier le nombre de lignes affectées
            if (rowsAffected > 0) {
                System.out.println("Mise à jour réussie !");
            } else {
                System.out.println("Aucune ligne mise à jour.");
            }
  
            
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static int receiveData(Connection connection, String key) {
    	// Requête SQL de mise à jour
        String updateQuery = "SELECT * FROM Frequency";
    	try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
    			ResultSet resultSet = preparedStatement.executeQuery()) {

    		while (resultSet.next()) {
    			String cardName = resultSet.getString("card");
    			int freq = resultSet.getInt("freq");
    			
    			if(cardName.equals(key)) {
    				return freq;
    			}
    			
    		}
    	} catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	
    	return 0;
      
    }
}
