package game.src.model.cards;

import game.src.model.players.Player;
import game.src.model.Game;

public abstract class SafeCard extends Card {
	
	public abstract void specialAction();

	public SafeCard(Game game, Player player) {
		super(game, player);
		// TODO Auto-generated constructor stub
	}

}
