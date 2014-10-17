package clueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;

import org.junit.*;

import clueGame.*;
import clueGame.Card.CardType;

public class GameActionTests {
	/**
	 * Need tests for:
	 *  - selecting a target location
	 *  - disproving a suggestion
	 *  - making a suggestion
	 */
	
	// game information containers
	private static ClueGame game;
	private static Board board;
	private ArrayList<Player> players;
	private Map<Player, BoardCell> playerLocations;
	private ArrayList<Card> cards;
	
	@Before
	public void setUp(){
		game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		players = game.getPlayers();
		playerLocations = game.getPlayerLocations();
		cards = game.getCards();
	}
	
	// This test hard-codes a solution for the game and tests for if it is true
	// and once for which way it can fail in each category.
	@Test
	public void testAccusation(){
		game.makeSolution();
		Solution solution = new Solution("Dr. Phil", "Really Depressing Thoughts",
				"Bedroom");
		assertTrue(game.checkAccusation(solution));
		solution = new Solution("Vlad", "Really Depressing Thoughts", 
				"Bedroom");
		assertFalse(game.checkAccusation(solution));
		solution = new Solution("Dr. Phil", "Really Depressing Thoughts", 
				"Bowling Alley");
		assertFalse(game.checkAccusation(solution));
		solution = new Solution("Dr. Phil", "Bear Hands", "Bedroom");
		assertFalse(game.checkAccusation(solution));
	}
}
