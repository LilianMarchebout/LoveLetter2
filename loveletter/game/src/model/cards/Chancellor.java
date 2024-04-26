package game.src.model.cards;

import game.src.model.players.Player;
import game.src.model.Game;

public class Chancellor extends SafeCard{

	public static int nbCopies = 2;
	private static int nbId = 0;
		
	public Chancellor(Game game, Player player) {
		// TODO Auto-generated constructor stub
		super(game,player);
		this.numCard = 6;
		this.idCard = Chancellor.nbId;
		Chancellor.nbId++;
	}

	public void specialAction() {
		int nbCarddraw = 0;
		Player player = this.player;
		
		//pioche 2 cartes 
		System.out.println("Pioche 2 cartes");
		if (this.game.getNbCardsDrawPile() > 1) {
			this.player.drawCard();
			this.player.drawCard();
			nbCarddraw = 2;
		} else {
			if (this.game.getNbCardsDrawPile() > 0) {
				this.player.drawCard();
				nbCarddraw = 1;
			}		
		}
		System.out.println(this.player);
		//choisit 2 cartes à mettre en dessous du paquet
		System.out.println("En met 2 en dessous de la pioche");
		Card card;
		for (int i = 0; i < nbCarddraw; i++) {
			card = player.chooseCardPlayed();
			this.game.addCardDrawPile(card);
			player.putOnCard(card);
		}
	}
}

