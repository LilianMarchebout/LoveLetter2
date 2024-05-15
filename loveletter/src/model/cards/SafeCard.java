package loveletter.src.model.cards;

import loveletter.src.model.players.Player;
import loveletter.src.model.Game;

public abstract class SafeCard extends Card {
	
	public abstract void specialAction();

	public SafeCard(Game game, Player player) {
		super(game, player);
		// TODO Auto-generated constructor stub
	}

}
