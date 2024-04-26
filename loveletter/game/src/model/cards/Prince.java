package game.src.model.cards;

import game.src.model.players.Player;
import game.src.model.Game;

public class Prince extends AttackingCard{

	public static int nbCopies = 2;
	private static int nbId = 0;
		
	public Prince(Game game, Player player) {
		super(game, player);
		this.numCard = 4;
		this.idCard = Prince.nbId;
		Prince.nbId++;
	}
	
	public void specialAction(Player playerAttacked, String CardAttacked) {
		if (game.getNbCardsDrawPile() > 0) {
			Card firstCard;
			try {
				firstCard = playerAttacked.getFirstCard();
				System.out.println("Le " + playerAttacked + " pose la carte " + firstCard + " et repioche");
				this.game.addCardPosedCardsInBoard(firstCard);
				playerAttacked.putOnCard(firstCard);
				playerAttacked.drawCard();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			System.out.println("La pioche est vide, le prince ne peut pas attaquer");
		}
	}
}

