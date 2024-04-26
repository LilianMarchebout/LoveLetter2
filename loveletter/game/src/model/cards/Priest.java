package game.src.model.cards;

import game.src.model.players.Player;
import game.src.model.Game;

public class Priest extends AttackingCard{

	public static int nbCopies = 2;
	private static int nbId = 0;
		
	public Priest(Game game, Player player) {
		super(game, player);
		this.numCard = 2;
		this.idCard = Priest.nbId;
		Priest.nbId++;
	}

	public void specialAction(Player playerAttacked, String CardAttacked) {
		try {
			System.out.println("La carte de " + playerAttacked + " revelée par le prête est " + playerAttacked.getFirstCard());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

