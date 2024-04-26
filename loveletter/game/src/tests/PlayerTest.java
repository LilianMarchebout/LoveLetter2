package game.src.tests;

import static org.junit.Assert.*;

import game.src.model.Game;
import game.src.model.cards.Gard;
import game.src.model.cards.Spy;
import game.src.model.players.Player;
import game.src.model.players.Robot;
import org.junit.Before;
import org.junit.Test;



public class PlayerTest {
	static Game g;
	
	@Before
	public void init() {
		g = new Game(2);
	}
	
	@Test
	public void removeCard1() {
		Player p = new Robot(g);
		Spy card = new Spy(g, p);
		p.giveCard(card);
		p.removeCard(card); //remove the card
		assertEquals(0, p.getNbCards());
	}
	
	@Test
	public void removeCard2() throws Exception {
		Player p = new Robot(g);
		Spy spy = new Spy(g, p);
		Gard gard = new Gard(g, p);
		p.giveCard(spy);
		p.giveCard(gard);
		p.removeCard(spy); //remove the first card
		assertEquals(Gard.class, p.getFirstCard().getClass());
		assertEquals(1, p.getNbCards());
	}
	
	@Test
	public void removeCard3() throws Exception {
		Player p = new Robot(g);
		Spy spy1 = new Spy(g, p);
		Spy spy2 = new Spy(g, p);
		p.giveCard(spy1);
		p.giveCard(spy2);
		p.removeCard(spy1); //remove one spy
		assertEquals(Spy.class, p.getFirstCard().getClass());
		assertEquals(1, p.getNbCards());
	}
	
	@Test
	public void removeCard4() throws Exception {
		Player p = new Robot(g);
		Gard gard = new Gard(g, p);
		Spy spy = new Spy(g, p);
		p.giveCard(gard);
		p.giveCard(spy);
		p.removeCard(spy); //remove the 2nd card
		assertEquals(Gard.class, p.getFirstCard().getClass());
		assertEquals(1, p.getNbCards());
	}
	
	@Test
	public void removeCard5() throws Exception {
		Player p = new Robot(g);
		Gard gard1 = new Gard(g, p);
		Gard gard2 = new Gard(g, p);
		Spy spy = new Spy(g, p);
		p.giveCard(gard1);
		p.giveCard(gard2);
		p.giveCard(spy);
		p.removeCard(gard2); //remove the middle
		assertEquals(2, p.getNbCards());
		assertEquals(Gard.class, p.getFirstCard().getClass());
		assertEquals(Spy.class, p.getCards().getCard(1).getClass());
		
	}
}
