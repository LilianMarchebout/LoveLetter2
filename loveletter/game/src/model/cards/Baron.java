package game.src.model.cards;

import game.src.model.players.Player;
import game.src.model.Game;

public class Baron extends AttackingCard{

	public static int nbCopies = 2;
	private static int nbId = 0;
		
	public Baron(Game game, Player player) {
		super(game, player);
		this.numCard = 3;
		this.idCard = Baron.nbId;
		Baron.nbId++;
	}

	public void specialAction(Player playerAttacked, String CardAttacked) {
		try {
			Card card1 = this.player.getFirstCard();
			if (card1.equals(this)) {
				card1 = this.player.getCard(1);
			}
			Card card2 = playerAttacked.getFirstCard();
			
			if (!card1.isEquals(card2)) {
				if (card1.isGreater(card2)) {
					System.out.println("Le " + playerAttacked + " avait " + card2);
					playerAttacked.loose();
				} else {
					System.out.println("Le " + this.player + " avait " + card1);
					this.player.loose();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

