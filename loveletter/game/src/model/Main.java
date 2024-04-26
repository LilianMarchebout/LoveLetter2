package game.src.model;

import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;


import game.src.utils.PrintDisabler;

public class Main {

	public static void main(String[] args) throws Exception {
		int nbPlayers = 2;
		int[] winRate = new int[nbPlayers];
//		PrintDisabler.disablePrint();
		for (int i = 0; i< 1; i++) {
			Game game = new Game(nbPlayers);
			game.launch();
			winRate[game.getIndexWinnerGame()]++;
		}
		PrintDisabler.enablePrint();
		System.out.println("WINRATE : ");
		System.out.print("[");
		for (int i = 0; i < nbPlayers; i++) {
			System.out.print(winRate[i]);
			if (i != nbPlayers-1) {
				System.out.print(", ");
			}
		}
		System.out.println("]");
		/*try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		    // Gérer l'exception (pilote non trouvé)
		}
		Connection connection = DatabaseConnector.getConnection();
		DatabaseManipulation.insertData(connection, "Gard");
		DatabaseManipulation.modifyData(connection, "Gard", 1);
		int freq = DatabaseManipulation.receiveData(connection, "Gard");
		System.out.println(freq);*/
	}
}



