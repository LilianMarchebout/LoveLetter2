package game.src.model;


import game.src.database.DatabaseController;
import game.src.database.DatabaseQueries;


public class Main {

	public static void main(String[] args) throws Exception {
//		int nbPlayers = 2;
//		int[] winRate = new int[nbPlayers];
////		PrintDisabler.disablePrint();
//		for (int i = 0; i< 1; i++) {
//			Game game = new Game(nbPlayers);
//			game.launch();
//			winRate[game.getIndexWinnerGame()]++;
//		}
//		PrintDisabler.enablePrint();
//		System.out.println("WINRATE : ");
//		System.out.print("[");
//		for (int i = 0; i < nbPlayers; i++) {
//			System.out.print(winRate[i]);
//			if (i != nbPlayers-1) {
//				System.out.print(", ");
//			}
//		}
//		System.out.println("]");

		DatabaseController.openConnection();
		DatabaseController.closeConnection();
	}
}



