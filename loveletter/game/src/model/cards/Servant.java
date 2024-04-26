package game.src.model.cards;

import game.src.model.players.Player;
import game.src.model.Game;

public class Servant extends SafeCard{

	public static int nbCopies = 2;
	private static int nbId = 0;
		
	public Servant(Game game, Player player) {
		super(game,player);
		this.numCard = 4;
		this.idCard = Servant.nbId;
		Servant.nbId++;
	}

	public void specialAction() {
		this.player.setProtected();
	}
}

