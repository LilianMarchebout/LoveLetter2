package loveletter.src.model.players;

import java.util.Random;
import loveletter.src.model.Game;
import loveletter.src.model.cards.Card;

public class Robot extends Player {

	public Robot(Game game) {
		super(game);
	}
	
	/**
	 * Choose a player's card to be attacked with the gard
	 * @return
	 */
	public String chooseCardAttacked() {
		Random r = new Random();
		int n = r.nextInt(Game.nbTypeCards-1);
        return Game.cardsGame.get(n);
	}
	
	/**
	 * @return a card to play, in default the 1st card
	 */
	public Card chooseCardPlayed() {
		return this.getCard(0);
	}

	@Override
	public Player choosePlayerAttacked() {
		return this.game.getRandomPlayerAttacked();
	}

}
