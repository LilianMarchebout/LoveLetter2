package game.src.model.players;

import java.util.Random;
import game.src.model.cards.*;
import game.src.model.Game;
import game.src.model.Draw;

public abstract class Player {
	private static int nbPlayer = 0;
	
	private Draw cards = new Draw();
	private int idPlayer;
	private boolean hasPutSpy = false;
	private boolean hasLoose = false;
	private boolean isProtected = false;
	private int token = 0;
	public Game game;
	
	public Player(Game game) {
		this.game = game;
		this.idPlayer = Player.nbPlayer;
		Player.nbPlayer++;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + this.idPlayer;
	}
	
	public boolean equals(Player p) {
		return this.idPlayer == p.idPlayer;
	}
	
	public void initPlayer() {
		this.hasLoose = false;
		this.isProtected = false;
		this.hasPutSpy = false;
		this.cards.setEmpty();
	}
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Getters
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	public int getIdPlayer() {
		return this.idPlayer;
	}
	
	public Card getFirstCard() throws Exception {
		return this.cards.getFirstCard();
	}
	
	public Draw getCards() {
		return this.cards;
	}
	
	public int getNbCards() {
		return this.cards.getNbCards();
	}
	
	public Card getCard(int index) {
		return this.cards.getCard(index);
	}
	
	public int getNbToken() {
		return this.token;
	}
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Setters
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	/**
	 * Give a card to the player
	 * <br> Add to the card the player
	 * @param card
	 */
	public void giveCard(Card card) {
		this.cards.add(card);
		card.setPlayer(this);
	}
	
	/**
	 * Remove the card from the player
	 * @param card
	 */
	public void putOnCard(Card card) {
		if (this != null) {
			if (card instanceof Princess) {
				this.loose();
			}
			this.cards.remove(card);
			
		}
	}
	
	/**
	 * Give the first card of draw pile to the player
	 */
	public void drawCard() {
		game.giveFirstCard(this);
	}
	
	/**
	 * Add one token
	 */
	public void addToken() {
		this.token++;
	}
	
	public void removeCards() {
		this.cards.setEmpty();
	}
	
	public void removeCard(Card card) {
		this.cards.remove(card);
		card.setPlayer(null);
	}
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Play a card
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	/**
	 * Choose a card to attack if choosenCard is a gard
	 * <br> Then place the card
	 * @param choosenCard
	 * @param playerAttacked
	 */
	public void playACard(Card choosenCard, Player playerAttacked) {
		//choose a card
		String CardAttacked = null;
		if (choosenCard instanceof Gard) {
			CardAttacked = this.chooseCardAttacked();
			while (CardAttacked.equals("Gard")) {
				CardAttacked = this.chooseCardAttacked();
			}
		}
		//play the card
		this.placeCard(playerAttacked, CardAttacked, choosenCard);
	}
	
	/**
	 * Remove the card of the player and use this special action
	 * @param playerAttacked : Player
	 * @param CardAttacked : String
	 * @param card : Card
	 */
	private void placeCard(Player playerAttacked, String CardAttacked, Card card) {
		this.putOnCard(card);
		this.useCard(card, playerAttacked, CardAttacked);
		card.getPlaced();
		
	}
	
	/**
	 * Use special action of card in playerAttacked
	 * <br> If playerAttacked is null and card is a AttackingCard, the card does nothing 
	 * @param card : Card
	 * @param playerAttacked : Player
	 * @param CardAttacked : String
	 */
	private void useCard(Card card, Player playerAttacked, String CardAttacked) {
		if (card instanceof AttackingCard) {
			if (playerAttacked != null) {
				AttackingCard cardAttack = (AttackingCard)card;
				cardAttack.specialAction(playerAttacked, CardAttacked);
			}
		}
		if (card instanceof SafeCard) {
			SafeCard cardSafe = (SafeCard)card;
			cardSafe.specialAction();
		}
	}
	
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Choose a card
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	/**
	 * Choose a player's card to be attacked with the gard
	 * @return
	 */
	public abstract String chooseCardAttacked();
	
	/**
	 * Choose a player's card to be attacked
	 * @return
	 */
	public abstract Player choosePlayerAttacked();
	
	
	/**
	 * @return a card to play
	 */
	public abstract Card chooseCardPlayed();
	
	
	
	/**
	 * @return a random player's card 
	 */
	public Card chooseRandomCard() {
		Random r = new Random();
		int n = r.nextInt(this.getNbCards());
		return this.getCard(n);
	}
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Spy
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	public void putOnSpy() {
		this.hasPutSpy = true;
	}
	
	public boolean hasPutSpy() {
		return this.hasPutSpy;
	}
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Servant
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	public void setProtected() {
		this.isProtected = true;
	}
	
	public void setNoProtected() {
		this.isProtected = false;
	}
	
	public boolean isProtected() {
		return this.isProtected;
	}
	
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Loose
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	public void loose() {
		System.out.println(this.toString() + " a perdu.");
		this.hasLoose = true;
	}
	
	public boolean hasLoose() {
		return this.hasLoose;
	}
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Print
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	public void printCards() {
		this.cards.printCards();
	}
	
	public boolean hasCards() {
		return this.getNbCards() <= 0;
	}
	
	
}
