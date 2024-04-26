package game.src.model.players;

import game.src.model.Game;
import game.src.model.cards.*;

public class Robot1 extends Robot {

	public Robot1(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Card chooseCardPlayed() {
		int nbCards = this.getNbCards();
		
		for (int i = 0; i < nbCards; i++) {
			Card card = this.getCard(i);
			Card card2 = this.getCard((i+1)%2);
			
			//Pas jouer princess
			if (card instanceof Princess) {
				return card2;
			}
			
			//Si baron, le jouer que si l'autre carte est > 4
			if (card instanceof Baron) {
				if (card2.getNumCard() >= 5) {
					return card;
				} else {
					return card2;
				}
			}
			
			//Si espionne, la jouer
			if (card instanceof Spy) {
				return card;
			}
			
			//Si le roi, pas le garde
			if (card instanceof King && card2 instanceof Gard) {
				return card2;
			}
			
		}
		return super.chooseCardPlayed();
	}
}
