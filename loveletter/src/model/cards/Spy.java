package loveletter.src.model.cards;

import loveletter.src.model.players.Player;
import loveletter.src.model.Game;

public class Spy extends SafeCard{

	public static int nbCopies = 2;
	private static int nbId = 0;
	
	public Spy(Game game, Player player) {
		super(game, player);
		this.idCard = Spy.nbId;
		this.numCard = 0;
		Spy.nbId++;
	}

	public void specialAction() {
		this.getPlayer().putOnSpy();
	}
}
