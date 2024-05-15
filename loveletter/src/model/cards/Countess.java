package loveletter.src.model.cards;

import loveletter.src.model.players.Player;
import loveletter.src.model.Game;

public class Countess extends SafeCard {
	
	public static int nbCopies = 1;
	private static int nbId = 0;

	public Countess(Game game, Player player) {
		super(game, player);
		this.numCard = 8;
		this.idCard = Countess.nbId;
		Countess.nbId++;
	}

	@Override
	public void specialAction() {
		// TODO Auto-generated method stub

	}

}
