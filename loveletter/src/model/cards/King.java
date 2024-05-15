package loveletter.src.model.cards;

import loveletter.src.model.players.Player;
import loveletter.src.model.Game;

public class King extends AttackingCard {
	
	public static int nbCopies = 1;
	private static int nbId = 0;

	public King(Game game, Player player) {
		super(game, player);
		this.numCard = 7;
		this.idCard = King.nbId;
		King.nbId++;
	}

	@Override
	public void specialAction(Player playerAttacked, String CardAttacked) {
		//get the other card in the player hand
		Card card = this.player.getCard(0);
		if (card instanceof King) {
			card = this.player.getCard(1);
		}
		try {
			Card card2 = playerAttacked.getFirstCard();
			this.player.removeCard(card);
			playerAttacked.removeCard(card2);
			
			this.player.giveCard(card2);
			playerAttacked.giveCard(card);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
