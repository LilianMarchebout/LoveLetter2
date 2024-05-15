package loveletter.src.tests;

import static org.junit.Assert.*;

import loveletter.src.model.Game;
import loveletter.src.model.cards.Gard;
import loveletter.src.model.cards.Spy;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameTest {
	
	static Game g = new Game(2);
	
	@BeforeClass
	public static void init() {
		g.createPlayers();
	}
	
	@Test
	public void testTrue() {
		assertTrue(true);
	}
	
	@Test 
	public void test_determineWinner1() throws Exception {
		g.init();
		
		g.getPlayers().get(0).loose(); //loose the first player
		g.setWinner();
		
		assertEquals(g.getPlayers().get(1), g.getWinner());
	}
	
	@Test 
	public void test_determineWinner2() throws Exception {
		g.init();
		
		g.setDrawPileEmpty(); //pioche vide
		
		Spy spy1 = new Spy(g, null);
		Spy spy2 = new Spy(g, null);
		g.getPlayer(0).removeCards();
		g.getPlayer(1).removeCards();
		
		g.getPlayer(0).giveCard(spy1);
		g.getPlayer(1).giveCard(spy2);
		
		g.setWinner(); //Egalité
		g.printPlayers();
		
		assertEquals(0, g.getNbCardsDrawPile());
		assertNull(g.getWinner());
	}
	
	@Test
	public void test_Gard() throws Exception {
		g.init();
		Gard gard = new Gard(g, g.getPlayer(0));
		
	}
}
