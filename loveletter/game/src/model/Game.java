package game.src.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import game.src.model.cards.*;
import game.src.model.players.Humain;
import game.src.model.players.Player;
import game.src.model.players.Robot1;

/**
 * 
 */
public class Game {
	
	public static final List<String> cardsGame = new ArrayList<>(Arrays.asList("Spy", "Gard", "Priest", "Baron", "Servant", "Prince" , "Chancellor", "King", "Countess", "Princess"));
	
	private List<Player> players = new ArrayList<>();
	private Draw drawPile = new Draw();
	private Draw posedCardsInBoard = new Draw();
	private Card hiddenCard;
	private int nbPlayers;
	private int turnPlayerNum = 0;
	private Player turnPlayer;
	private Player winner = null;
	private Player winnerGame = null;
	public int nbCards = 21;
	
	public static int nbTypeCards = 7;
	
	public Game(int nbPlayers){
		this.nbPlayers = nbPlayers;
		
	}
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Phases
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	/**
	 * Initialize all the variables of one game
	 * @throws Exception
	 */
	public void init() throws Exception {
		System.out.println("DEBUT DU JEU");
		System.out.println();
		
		for (Player p : this.players) {
			p.initPlayer();
		}
		
		//create draw pile
		this.createDrawPile();
		this.posedCardsInBoard.setEmpty();
		//shuffle draw pile
		this.drawPile.shuffle();
		System.out.println("Nb Cartes : " + this.drawPile.getNbCards());
		
		//hidden card
		this.setHiddenCard();
		//System.out.println("Une carte a été cachée");
		
		//On pose 3 cartes visibles
		if (nbPlayers == 2) { 
			this.setVisiblesCard();
		}
		//System.out.println("On pose 3 cartes visibles");
		
		//give one card for each player
		this.giveOneCardPlayers();
		//System.out.println("On donne une carte à chaque joueur");
		
		if (this.winner == null) {
			this.turnPlayerNum = 0;
			this.turnPlayer = this.getTurnPlayer();
		} else {
			this.turnPlayerNum = this.winner.getIdPlayer();
			this.turnPlayer = this.winner;
		}

		
	}
	
	/**
	 * Launch the game
	 * @throws Exception
	 */
	public void launch() throws Exception {
		//create players
		this.createPlayers();
		while (!this.isGameOver()) {
			this.printGameStatus();
			//init
			this.init();
			
			while(!this.isOver() && this.drawPile.getNbCards() > 0) {
				this.turn();
				
				//Next Turn
				this.nextTurn();
				System.out.println();
			}
			this.end();
			System.out.println();
			System.out.println();
		}
		this.endGame();
	}
	
	/**
	 * When the game is over, print all the status
	 */
	private void end() {
		System.out.println("FIN DU JEU");
		
		System.out.print("DrawPile in end : ");
		this.drawPile.printCards();
		
		System.out.print("posedCardsInBoard in end : ");
		this.posedCardsInBoard.printCards();
		
		for (Player p : this.players) {
			System.out.print("Carte de " + p + " : ");
			p.printCards();
		}
		
		this.setWinner();
		System.out.print("La carte cachée était : " + this.hiddenCard);
		
		this.giveTokenSpy();
		this.giveTokenWinner();
	}
	
	/**
	 * When the game is over, print the winner
	 */
	private void endGame() {
		System.out.println("FIN DE LA PARTIE");
	
		this.printGameStatus();
		this.winnerGame = this.players.get(0);
		for (Player p : this.players) {
			if (p.getNbToken() > this.winnerGame.getNbToken()) {
				this.winnerGame = p;
			}
		}
		System.out.println("Le gagnant de la partie est : " + this.winnerGame);

	}
	
	/**
	 * All progress of one turn in the game
	 * @throws Exception
	 */
	private void turn() throws Exception {
		if (!this.turnPlayer.hasLoose()) {
			//Not protected anymore
			this.turnPlayer.setNoProtected();
			
			//print player
			System.out.println("Tour de " + this.turnPlayer + " : ");
			
			//Cartes posées sur le plateau
			System.out.print("Cartes posées sur le plateau : ");
			this.posedCardsInBoard.printCards();
			
			//give a card
			System.out.println(this.turnPlayer + " pioche");
			//this.giveFirstCard(this.turnPlayer);
			this.turnPlayer.drawCard();
			
			//System.out.print("Carte de " + this.turnPlayer + " : ");
			//this.turnPlayer.printCards();
			
			/*for (Player p : this.players) {
				System.out.print("Player des cartes de " + p + " : ");
				System.out.print("[");
				for (int i = 0; i < p.getNbCards(); i++) {
					System.out.print(p.getCard(i).getPlayer());
				}
				System.out.println("]");
			}*/
			
			//choisit une carte
			Card choosenCard = null;
			if (!this.checkCountess()) {
				choosenCard = this.turnPlayer.chooseCardPlayed();
			} else {
				Card card1 = this.turnPlayer.getCard(0);
				Card card2 = this.turnPlayer.getCard(1);
				if (card1 instanceof Countess) {
					choosenCard = card1;
				} else {
					choosenCard = card2;
				}
			}
			
			System.out.println(this.turnPlayer + " joue : " + choosenCard);
			
			//Choisit un joueur à attaquer
			Player playerAttacked = this.choosePlayerAttacked(choosenCard);
			
			//Play a card
			this.turnPlayer.playACard(choosenCard, playerAttacked);
			this.posedCardsInBoard.add(choosenCard);
			
			//Cas d'erreur, doit avoir une seule carte à la fin du tour
			if (this.turnPlayer.getNbCards() != 1) { 
				throw new Exception(this.turnPlayer + " hasn't 1 card in the turn's end ");
			}
		}
	}
	
	private boolean checkCountess() {
		// TODO Auto-generated method stub
		Card card1 = this.turnPlayer.getCard(0);
		Card card2 = this.turnPlayer.getCard(1);
		if (card1 instanceof Countess || card2 instanceof Countess) {
			int nbCards = this.turnPlayer.getNbCards();
			for (int i = 0; i < nbCards; i++) {
				if (this.turnPlayer.getCard(i) instanceof Prince || this.turnPlayer.getCard(i) instanceof King) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 *  Define the next turn :
	 *  <br>- The new number of turn's player
	 *  <br>- The next turn's player
	 */
	private void nextTurn() {
		this.turnPlayerNum++;
		this.turnPlayerNum %= this.nbPlayers;
		this.turnPlayer = this.getTurnPlayer();
	}
	
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Creations
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	public static <T extends Card> List<T> createCards(Game game, Class<T> cardClass) {
        List<T> cardList = new ArrayList<>();
        try {
            for (int i = 0; i < cardClass.getDeclaredField("nbCopies").getInt(null); i++) {
                cardList.add(cardClass.getDeclaredConstructor(Game.class, Player.class).newInstance(game, null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardList;
    }
	
	/**
	 * Create all the cards base on each number of copies
	 */
	public void createDrawPile() {
		this.drawPile.setEmpty();
		this.drawPile.addAll(Game.createCards(this, Spy.class));
		this.drawPile.addAll(Game.createCards(this, Gard.class));
		this.drawPile.addAll(Game.createCards(this, Priest.class));
		this.drawPile.addAll(Game.createCards(this, Baron.class));
		this.drawPile.addAll(Game.createCards(this, Servant.class));
		this.drawPile.addAll(Game.createCards(this, Prince.class));
		this.drawPile.addAll(Game.createCards(this, Chancellor.class));
		this.drawPile.addAll(Game.createCards(this, King.class));
		this.drawPile.addAll(Game.createCards(this, Countess.class));
		this.drawPile.addAll(Game.createCards(this, Princess.class));
		this.nbCards = this.drawPile.getNbCards();
	}
	
	/**
	 * Create all the player based on nbPlayers
	 */
	public void createPlayers() {
		this.players = new ArrayList<>();
		/*for (int i = 0; i < this.nbPlayers; i++) {
			this.players.add(new Robot(this));
		}*/
		this.players.add(new Robot1(this));
		this.players.add(new Robot1(this));
		//this.players.add(new Humain(this));
	}
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Getters
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	
	/**
	 * @return the turn's player
	 */
	public Player getTurnPlayer() {
		return this.players.get(this.turnPlayerNum);
	}

	
	/**
	 * @return the number of players who are protected by the servant
	 */
	public int getNbProtected() {
		int nbProtected = 0;
		for (Player p : this.players) {
			if(p.isProtected()) 
				nbProtected++;
		}
		return nbProtected;
	}
	
	/**
	 * @return random player
	 */
	public Player getRandomPlayer() {
		Random r = new Random();
		int n = r.nextInt(this.nbPlayers);
		return this.players.get(n);
		
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	public Player getPlayer(int index) {
		return this.players.get(index);
	}
	
	/**
	 * The player isn't the turn's player
	 * @return player to attack randomly 
	 */
	public Player getRandomPlayerAttacked() {
		Player playerAttacked;
		playerAttacked = this.getRandomPlayer(); 
		while (playerAttacked.equals(this.turnPlayer))
			playerAttacked = this.getRandomPlayer();
		return playerAttacked;
	}
	
	private int getNbSpy() {
		int nbSpy = 0;
		for (Player p : this.players) {
			if (p.hasPutSpy())
				nbSpy++;
		}
		return nbSpy;
	}
	
	/**
	 * @return the max tokens of players
	 */
	private int getMaxTokenPlayer() {
		int max = 0;
		for (Player p : this.players) {
			if (p.getNbToken() > max) 
				max = p.getNbToken();
		}
		return max;
	}
	
	public Player getWinner() {
		return this.winner;
	}
	
	public int getNbPlayers() {
		return this.players.size();
	}
	
	public int getIndexWinnerGame() {
		int nbPlayers = this.getNbPlayers();
		for (int i = 0; i < nbPlayers; i++) {
			Player p = this.getPlayer(i);
			if (p.equals(this.winnerGame)) {
				return i;
			}
		}
		return -1;
	}
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Setters
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	/**
	 * Set the hidden card and remove it from the draw pile
	 */
	private void setHiddenCard() {
		try {
			this.hiddenCard = this.drawPile.getFirstCard();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.drawPile.remove(hiddenCard);
	}
	
	/**
	 * Place 3 cards in the board
	 * @throws Exception
	 */
	private void setVisiblesCard() throws Exception{
		for (int i = 0; i < 3; i++) {
			this.placeCardDrawPile();
		}
	}
	
	
	
	/**
	 * Determine le gagnant de la manche
	 */
	public void setWinner() {
		if (this.isOver()) { //Si tous les joueurs ont perdus
			for (Player p : this.players) {
				if (!p.hasLoose()) {
					this.winner = p;
					System.out.println("The winner is : " + this.winner);
				}
			}
		} else {
			if (this.drawPile.getNbCards() <= 0) { //Si le paquet est fini
				if (this.hasWinnerInEnd()) {
					System.out.println("The winner is : " + this.winner);
				} else {
					this.winner = null;
					System.out.println("The winner is : Egalité");
				}
			}
		}
	}
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * DrawPile && PosedCArdsInBoard
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	/**
	 * Add a card in the last position in draw pile
	 * @param card : card to add
	 */
	public void addCardDrawPile(Card card) {
		this.drawPile.add(card);
	}
	
	public int getNbCardsDrawPile() {
		return this.drawPile.getNbCards();
	}
	
	public void setDrawPileEmpty() {
		this.drawPile.setEmpty();
	}
	
	/**
	 * Add a card in the last position in the board
	 * @param card : card to add
	 */
	public void addCardPosedCardsInBoard(Card card) {
		this.posedCardsInBoard.add(card);
	}
	
	/**
	 * Place a card from the draw pile to the board
	 * @throws Exception : 
	 */
	private void placeCardDrawPile() throws Exception {
		Card cardPicked = this.drawPile.getFirstCard();
		this.drawPile.remove(cardPicked);
		this.addCardPosedCardsInBoard(cardPicked);
	}
	

	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Conditions
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	/**
	 * Exec this function only if there is no card in drawPile
	 * <br>Compare the best card between players
	 * <br> Determine the winner
	 * @return if there is a winner
	 */
	public boolean hasWinnerInEnd() {
		this.winner = this.players.get(0);
		
		for (Player p : this.players) {
			if (!winner.equals(p)) { //Si ce n'est pas  le même joueur
				try {
					Card card1 = p.getFirstCard();
					Card card2 = winner.getFirstCard();
					if (!card1.isEquals(card2) ) { //Si leur cartes ne sont pas égales
						//Si p a une meilleur carte que winner
						if (!p.hasCards() && !winner.hasCards() && card1.isGreater(card2))
							this.winner = p;
					} else { //Sont égales => pas de winner
						return false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		}
		return true;
	}
	
	/**
	 * @return if all players, except one, loose 
	 */
	private boolean isOver() {
		int nbLoose = 0;
		for (Player p : this.players) {
			if (p.hasLoose()) 
				nbLoose++;
		}
		return nbLoose == this.nbPlayers-1;
	}
	
	private boolean isGameOver() {
		int max = this.getMaxTokenPlayer();
		switch(this.nbPlayers) {
		case 2:
			return max >= 6;
		case 3:
			return max >= 5;
		case 4:
			return max >= 4;
		default: 
			return max >= 3;
		}
	}
	
	/**
	 * @return if there is 1 players in the end who has place the spy
	 */
	private boolean has1Spy() {
		return this.getNbSpy() == 1;
	}
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Give Card Player
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	
	/**
	 * Give the first card of draw pile to the player
	 * Remove this card within draw pile
	 * @param player
	 */
	public void giveFirstCard(Player player) {
		try {
			Card cardPick = this.drawPile.getFirstCard();
			player.giveCard(cardPick);
			this.drawPile.remove(cardPick);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *  Give one card to all players
	 */
	private void giveOneCardPlayers() {
		for (Player p : this.players) {
			//this.giveFirstCard(p);
			p.drawCard();
		}
	}
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Print
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	public void printPlayers() {
		System.out.print("[");
		for (Player p : this.players) {
			System.out.print(p);
			if (this.players.indexOf(p) != this.players.size()-1)
				System.out.print(", ");
		}
		System.out.println("]");
	}
	
	private void printPlayersTokens() {
		for (Player p : this.players) {
			System.out.println("Jetons de " + p + " : " + p.getNbToken());
		}
	}
	
	private void printGameStatus() {
		System.out.println("Status de la partie : ");
		this.printPlayersTokens();
		System.out.println();
	}
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Tokens
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	private void giveTokenSpy() {
		if (this.has1Spy()) {
			for (Player p : this.players) {
				if (p.hasPutSpy() && !p.hasLoose()) {
					p.addToken();
				}
			}
		}
	}
	
	private void giveTokenWinner() {
		if (this.winner != null) 
			this.winner.addToken();
	}
	
	/*------------------------------------------------------------------------------------------------
	 * 
	 * Others
	 * 
	 ------------------------------------------------------------------------------------------------*/
	
	/**
	 * Choose one player to attack with a specific card
	 * @param choosenCard : the card who attack a player
	 * @return - null if the choosen card isn't a AttackingCard 
	 * <br> - null if there is no player to attack 
	 * <br> - the player who is choose to be attacked 
	 */
	private Player choosePlayerAttacked(Card choosenCard) {
		Player playerAttacked = null;
		if (choosenCard instanceof AttackingCard) { //this card attack
			int nbProtected = this.getNbProtected();
			if (this.nbPlayers-nbProtected-1 > 0) { //if there a player to attack
				playerAttacked = this.turnPlayer.choosePlayerAttacked();
				while (playerAttacked.isProtected() ) {
					playerAttacked = this.turnPlayer.choosePlayerAttacked();
				}
			}
		}
		return playerAttacked;
	}

	
}
