package loveletter.src.model.cards;

import loveletter.src.model.players.Player;
import loveletter.src.model.Game;

public abstract class AttackingCard extends Card {
	
	public abstract void specialAction(Player playerAttacked, String CardAttacked);

	public AttackingCard(Game game, Player player) {
		super(game, player);
		// TODO Auto-generated constructor stub
	}

}
