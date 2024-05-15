package loveletter.src.model.cards;

import loveletter.src.model.Game;
import loveletter.src.model.players.Player;


public abstract class Card {
	protected int numCard = -1;
	protected Player player;
	protected int idCard;
	protected Game game;
	
	//public abstract void specialAction(Player playerAttacked, String CardAttacked);
	//public abstract void straightenOut(); //defausser
	
	public Card(Game game, Player player) {
		this.game = game;
		this.player = player;
	}

	public String toString() {
		return this.getClass().getSimpleName();
	}
	
	public boolean equals(Card otherCard) {
		return this.getClass() == otherCard.getClass() && this.idCard == otherCard.idCard;
	}
	
	public boolean isGreater(Card otherCard) {
		return this.numCard > otherCard.numCard;
	}
	
	/**
	 * @param otherCard
	 * @return if there are the same type of card
	 */
	public boolean isEquals(Card otherCard) {
		return this.numCard == otherCard.numCard;
	}
	
	public int getNumCard() {
		return this.numCard;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public void getPlaced() {
		this.setPlayer(null);
	}
	
}
