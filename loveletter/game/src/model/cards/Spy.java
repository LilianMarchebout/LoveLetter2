package game.src.model.cards;

import game.src.model.players.Player;
import game.src.model.Game;

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
