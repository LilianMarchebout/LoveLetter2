package loveletter.src.model;

import loveletter.src.utils.PrintDisabler;
import static loveletter.src.database.DatabaseQueries.*;


public class Main {

	public static void main(String[] args) throws Exception {

		deleteAllTables();
		createAllTables();
		int nbPlayers = 2;
		for (int i = 0; i< 50; i++) {
			System.out.println(i);
			PrintDisabler.disablePrint();
			Game game = new Game(nbPlayers);
			game.launch();
			PrintDisabler.enablePrint();
		}
	}
}



