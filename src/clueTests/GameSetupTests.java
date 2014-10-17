package clueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;

import org.junit.*;

import clueGame.*;

public class GameSetupTests {
	/**
	 * Need tests for:
	 * 	- loading the people
	 *  - loading the cards
	 *  - dealing the cards
	 */
	private static Board board;
	private ArrayList<Player> players;
	private Map<Player, BoardCell> playerLocations;
	@Before
	public void setUpPeople(){
		ClueGame game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		players = game.getPlayers();
		playerLocations = game.getPlayerLocations();
	}
	
	@Test
	public void testPeople(){
		assertEquals(players.size(), 6);
		
		assertEquals(players.get(0).getName(), "Dr. Phil");
		assertEquals(players.get(0).getColor(), Color.PURPLE);
		assertEquals(playerLocations.get(players.get(0)), board.getCellAt(0, 11));
		
		assertEquals(players.get(1).getName(), "Popeye");
		assertEquals(players.get(1).getColor(), Color.RED);
		assertEquals(playerLocations.get(players.get(1)), board.getCellAt(5, 0));
		
		assertEquals(players.get(2).getName(), "Vlad");
		assertEquals(players.get(2).getColor(), Color.BLACK);
		assertEquals(playerLocations.get(players.get(2)), board.getCellAt(21, 4));
		
		assertEquals(players.get(3).getName(), "That Guy");
		assertEquals(players.get(3).getColor(), Color.YELLOW);
		assertEquals(playerLocations.get(players.get(3)), board.getCellAt(22, 16));
		
		assertEquals(players.get(4).getName(), "Davey Jones");
		assertEquals(players.get(4).getColor(), Color.BLUE);
		assertEquals(playerLocations.get(players.get(4)), board.getCellAt(13, 22));
		
		assertEquals(players.get(5).getName(), "John Elway");
		assertEquals(players.get(5).getColor(), Color.ORANGE);
		assertEquals(playerLocations.get(players.get(5)), board.getCellAt(0, 22));
	}
}
