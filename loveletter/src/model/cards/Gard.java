package loveletter.src.model.cards;


import loveletter.src.model.players.Player;
import loveletter.src.model.Game;

public class Gard extends AttackingCard{

	public static int nbCopies = 6;
	private static int nbId = 0;
		
	public Gard(Game game, Player player) {
		super(game, player);
		this.numCard = 1;
		this.idCard = Gard.nbId;
		Gard.nbId++;
	}

	public void specialAction(Player playerAttacked, String CardAttacked) {
		if (!CardAttacked.equals("Gard")) {
			System.out.println(this.player + " demande si " + playerAttacked + " a un " + CardAttacked);
			try {
				if (playerAttacked.getFirstCard().toString().equals(CardAttacked)) {
					System.out.println("Il a un " + CardAttacked);
					playerAttacked.loose();
				} else {
					System.out.println("Il n'en a pas.");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}         
	}
}

