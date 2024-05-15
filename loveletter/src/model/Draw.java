package loveletter.src.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import loveletter.src.model.cards.Card;


public class Draw {
	
	private List<Card> cards = new ArrayList<>();

	public Draw() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Met la pioche vide
	 */
	public void setEmpty() {
		this.cards = new ArrayList<>();
	}
	
	/**
	 * @return the number of cards in the draw pile
	 */
	public int getNbCards() {
		return this.cards.size();
	}
	
	/**
	 * @return the first card in draw pile
	 * @throws Exception : Draw pile is empty
	 */
	public Card getFirstCard() throws Exception {
		if (this.getNbCards() > 0) {
			return this.cards.get(0);
		}
		else
			throw new Exception("Draw is empty");
	}
	
	public Card getRandomCard() throws Exception {
		if (this.getNbCards() > 0) {
			Random r = new Random();
			int n = r.nextInt(this.getNbCards());
			return this.cards.get(n);
		}
		else
			throw new Exception("Draw is empty");
	}
	
	public Card getCard(int index) {
		return this.cards.get(index);
	}
	
	public void add(Card card) {
		this.cards.add(card);
	}
	
	public void addAll(List<? extends Card> cards) {
		this.cards.addAll(cards);
	}
	
	public void remove(Card card) {
		this.cards.removeIf(x -> x.equals(card));
	}
	
	public void shuffle() {
		Collections.shuffle(this.cards);
	}
	
	public void printCards() {
		System.out.print("[");
		for (Card card : this.cards) {
			System.out.print(card);
			if (cards.indexOf(card) != cards.size()-1)
				System.out.print(", ");
		}
		System.out.println("]");
	}

}
