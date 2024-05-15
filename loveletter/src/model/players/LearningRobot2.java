package loveletter.src.model.players;

import loveletter.src.model.Game;
import loveletter.src.model.cards.Card;
import loveletter.src.database.DatabaseAnalysis;

/**
 * A Learning Robot who determine the card to play with the frequencies of cards (in the database)
 */
public class LearningRobot2 extends Robot1 {

    public LearningRobot2(Game game) {
        super(game);
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
        	Object[][] frequencyBoard = DatabaseAnalysis.cardMoreUseBoard();
            for (Object[] line : frequencyBoard) {
                String cardFreq = (String)line[0];
                int freq = (int)line[1];
                if (cardName.equals(cardFreq)) {
                    if (freq > maxFrequency) {
                        maxFrequency = freq;
                        mostFrequentCard = card;
                    }
                }
            }
        }
        
        return mostFrequentCard;
    }
}
