package loveletter.src.model.players;

import java.util.HashMap;
import java.util.Map;

import loveletter.src.model.Game;
import loveletter.src.model.cards.Card;

public class LearningRobot extends Robot1 {

    private Map<String, Integer> cardFrequency;

    public LearningRobot(Game game) {
        super(game);
        this.cardFrequency = new HashMap<>();
    }
    
    @Override
    public void playACard(Card choosenCard, Player playerAttacked) {
        super.playACard(choosenCard, playerAttacked);
        this.updateCardFrequency(choosenCard);
    }
    
    private void updateCardFrequency(Card card) {
    	String cardName = card.toString();
        cardFrequency.put(cardName, cardFrequency.getOrDefault(cardName, 0) + 1);
    }
    
    @Override
    public Card chooseCardPlayed() {
    	Card card = this.getMostFrequentCardInHand();
    	if (card != null) {
    		return card;
    	}
        return super.chooseCardPlayed();
    }
    
 // Méthode pour récupérer la carte la plus fréquemment jouée dans la main
    private Card getMostFrequentCardInHand() {
        Card mostFrequentCard = null;
        int maxFrequency = 0;
        int nbCards = this.getNbCards();
        for (int i = 0; i < nbCards; i++) {
        	Card card = this.getCard(i);
        	String cardName = card.toString();
        	if (cardFrequency.containsKey(cardName) && cardFrequency.get(cardName) > maxFrequency) {
                mostFrequentCard = card;
                maxFrequency = cardFrequency.get(cardName);
            }
        }
        
        return mostFrequentCard;
    }
}
