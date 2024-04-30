package game.src.model;

import game.src.utils.DatabaseAnalysis;
import game.src.utils.DatabaseQueries;
import game.src.utils.PrintDisabler;


public class Main {

	public static void main(String[] args) throws Exception {

//		DatabaseQueries.deleteAllTables();
//		DatabaseQueries.createAllTables();
//		int nbPlayers = 2;
//		for (int i = 0; i< 10; i++) {
//			PrintDisabler.enablePrint();
//			System.out.println(i);
//			PrintDisabler.disablePrint();
//			Game game = new Game(nbPlayers);
//			game.launch();
//		}
//		PrintDisabler.enablePrint();
		DatabaseAnalysis.betterBoardPlayer();
		DatabaseAnalysis.cardMoreUseBoard();
	}
}



