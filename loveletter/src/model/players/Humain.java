package loveletter.src.model.players;

import java.util.Scanner;

import loveletter.src.model.Game;
import loveletter.src.model.cards.Card;

public class Humain extends Player {

	public Humain(Game game) {
		super(game);
	}
	
	/**
	 * Choose a player's card to be attacked with the gard
	 * @return
	 */
	public String chooseCardAttacked() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Quelle carte attaquer ? ");
		String answer = scanner.nextLine();
		//scanner.close();
		
		answer = answer.toLowerCase();
		answer = answer.substring(0, 1).toUpperCase() + answer.substring(1); //capitalize
		if (answer.equals("Gard")) {
			System.out.println("Il n'est pas possible d'attaquer le garde !");
			return this.chooseCardAttacked();
		}
		if (!Game.cardsGame.contains(answer)) {
			System.out.println("Cette carte n'existe pas !");
			return this.chooseCardAttacked();
		}		
		return answer;
		
	}
	
	@Override
	public Player choosePlayerAttacked() {
		
		if (this.game.getNbPlayers() > 2) {
			System.out.print("Les joueurs : ");
			this.game.printPlayers();
			Scanner scanner = new Scanner(System.in);
			System.out.print("Quel joueur attaquer ? ");
			String answer = scanner.nextLine();
			//scanner.close();
			
			answer = answer.toLowerCase();
			answer = answer.substring(0, 1).toUpperCase() + answer.substring(1); //capitalize
			
			int nbPlayers = this.game.getPlayers().size();
			for (int i = 0; i < nbPlayers; i ++) {
				if (this.game.getPlayer(i).toString().equals(answer)) {
					return this.game.getPlayer(i);
				}
			}
			System.out.println("Ce joueur n'existe pas !");
			return this.choosePlayerAttacked();
		} else {
			Player playerAttacked = null;
			for (int i = 0; i < 2; i++) {
				if(!this.game.getPlayer(i).equals(this)) {
					playerAttacked = this.game.getPlayer(i);
				}
			}
			return playerAttacked;
		}
		
	}
	
	/**
	 * @return a card to play
	 */
	public Card chooseCardPlayed() {
		System.out.print("Vous avez ces cartes : ");
		this.printCards();
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Quelle carte voulez vous jouer ? ");
		String answer = scanner.nextLine();
		//scanner.close();
		
		answer = answer.toLowerCase();
		answer = answer.substring(0, 1).toUpperCase() + answer.substring(1); //capitalize
		//System.out.println(answer);
		
		int nbCards = this.getNbCards();
		for (int i = 0; i < nbCards; i ++) {
			//System.out.println(this.getCard(i).getClass().getSimpleName());
			if (this.getCard(i).getClass().getSimpleName().equals(answer)) {
				return this.getCard(i);
			}
		}
		System.out.println("Vous n'avez pas cette carte !");
		return this.chooseCardPlayed();
	}

	

}
