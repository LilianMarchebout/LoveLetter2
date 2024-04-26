package game.src.model.cards;

import game.src.model.players.Player;
import game.src.model.Game;

public abstract class AttackingCard extends Card {
	
	public abstract void specialAction(Player playerAttacked, String CardAttacked);

	public AttackingCard(Game game, Player player) {
		super(game, player);
		// TODO Auto-generated constructor stub
	}

}
