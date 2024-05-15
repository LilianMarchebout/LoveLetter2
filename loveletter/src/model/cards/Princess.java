package loveletter.src.model.cards;

import loveletter.src.model.players.Player;
import loveletter.src.model.Game;

public class Princess extends SafeCard {
	
	public static int nbCopies = 1;
	private static int nbId = 0;

	public Princess(Game game, Player player) {
		super(game, player);
		this.numCard = 1;
		this.idCard = Princess.nbId;
		Princess.nbId++;
	}

	@Override
	public void specialAction() {
		// TODO Auto-generated method stub
		this.player.loose();
	}

}
