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
		// correct solution
		Solution solution = new Solution("Dr. Phil", "Really Depressing Thoughts",
				"Bedroom");
		assertTrue(game.checkAccusation(solution));
		// wrong person
		solution = new Solution("Vlad", "Really Depressing Thoughts", 
				"Bedroom");
		assertFalse(game.checkAccusation(solution));
		// wrong room
		solution = new Solution("Dr. Phil", "Really Depressing Thoughts", 
				"Bowling Alley");
		assertFalse(game.checkAccusation(solution));
		// wrong weapon
		solution = new Solution("Dr. Phil", "Bear Hands", "Bedroom");
		assertFalse(game.checkAccusation(solution));
	}
	
	// This tests that players are able to disprove suggestions correctly
	@Test
	public void testDisproveSuggestion() {
		// test cards
		Card philCard = new Card("Dr. Phil", CardType.PERSON);
		Card johnCard = new Card("John Elway", CardType.PERSON);
		Card bearCard = new Card("Bear Hands", CardType.WEAPON);
		Card gunCard = new Card("Ray Gun", CardType.WEAPON);
		Card bowlingCard = new Card("Bowling Alley", CardType.ROOM);
		Card officeCard = new Card("Office", CardType.ROOM);
		
		// test for one player, one correct match
		// 'deal' fixed hand to test player
		Player disprover = new Player();
		disprover.addCard(philCard);
		disprover.addCard(johnCard);
		disprover.addCard(bearCard);
		disprover.addCard(gunCard);
		disprover.addCard(bowlingCard);
		disprover.addCard(officeCard);
		// cards needed for testing results
		Card result = new Card();
		Card testCard = new Card();
		// test correct person is returned
		result = disprover.disproveSuggestion(philCard, testCard, testCard);
		assertEquals(result, philCard);
		// test correct weapon is returned
		result = disprover.disproveSuggestion(testCard, gunCard, testCard);
		assertEquals(result, gunCard);
		// test correct person is returned
		result = disprover.disproveSuggestion(testCard, testCard, bowlingCard);
		assertEquals(result, bowlingCard);
		// test null is returned for no matches
		result = disprover.disproveSuggestion(testCard, testCard, testCard);
		assertEquals(result, null);
		
		// test for one player, multiple possible matches
		int personMatchCount, weaponMatchCount, roomMatchCount;
		// person and weapon match
		personMatchCount = 0;
		weaponMatchCount = 0;
		for (int i = 0; i < 15; i++) {
			result = disprover.disproveSuggestion(johnCard, gunCard, testCard);
			assert(result.equals(johnCard) || result.equals(gunCard));
			if (result.getType() == CardType.PERSON) personMatchCount++;
			else if (result.getType() == CardType.WEAPON) weaponMatchCount++;
			else assert(false);
		}
		assert(personMatchCount > 0 && weaponMatchCount > 0);
		// person and room match
		personMatchCount = 0;
		roomMatchCount = 0;
		for (int i = 0; i < 15; i++) {
			result = disprover.disproveSuggestion(johnCard, testCard, officeCard);
			assert(result.equals(johnCard) || result.equals(officeCard));
			if (result.getType() == CardType.PERSON) personMatchCount++;
			else if (result.getType() == CardType.ROOM) roomMatchCount++;
			else assert(false);
		}
		assert(personMatchCount > 0 && roomMatchCount > 0);
		// weapon and room match
		personMatchCount = 0;
		roomMatchCount = 0;
		for (int i = 0; i < 15; i++) {
			result = disprover.disproveSuggestion(testCard, bearCard, officeCard);
			assert(result.equals(bearCard) || result.equals(officeCard));
			if (result.getType() == CardType.WEAPON) weaponMatchCount++;
			else if (result.getType() == CardType.ROOM) roomMatchCount++;
			else assert(false);
		}
		assert(weaponMatchCount > 0 && roomMatchCount > 0);
		// person, weapon, and room match
		personMatchCount = 0;
		weaponMatchCount = 0;
		roomMatchCount = 0;
		for (int i = 0; i < 15; i++) {
			result = disprover.disproveSuggestion(johnCard, testCard, officeCard);
			assert(result.equals(johnCard) || result.equals(officeCard));
			if (result.getType() == CardType.PERSON) personMatchCount++;
			else if (result.getType() == CardType.WEAPON) weaponMatchCount++;
			else if (result.getType() == CardType.ROOM) roomMatchCount++;
			else assert(false);
		}
		assert(personMatchCount > 0 && weaponMatchCount > 0 && roomMatchCount > 0);
		
		// test that all players are queried
		// create players
		ArrayList<Player> testPlayers = new ArrayList<Player>();
		Player humanPlayer = new HumanPlayer();
		Player compPlayer1 = new ComputerPlayer();
		Player compPlayer2 = new ComputerPlayer();
		Player compPlayer3 = new ComputerPlayer();
		// assign cards to players
		humanPlayer.addCard(johnCard);
		compPlayer1.addCard(gunCard);
		compPlayer2.addCard(officeCard);
		compPlayer3.addCard(philCard);
		// add to players container
		testPlayers.add(humanPlayer);
		testPlayers.add(compPlayer1);
		testPlayers.add(compPlayer2);
		testPlayers.add(compPlayer3);
		game.setPlayers(testPlayers);
		// test suggestion no players can disprove
		result = game.handleSuggestion(testCard, testCard, testCard, humanPlayer);
		assertEquals(result, null);
		// test suggestion only human can disprove
		result = game.handleSuggestion(johnCard, testCard, testCard, compPlayer3);
		assertEquals(result, johnCard);
		// test suggestion only suggesting player can disprove
		result = game.handleSuggestion(johnCard, testCard, testCard, humanPlayer);
		assertEquals(result, null);
		result = game.handleSuggestion(testCard, gunCard, testCard, compPlayer1);
		// test order 1: first possible player disproves
		result = game.handleSuggestion(testCard, testCard, officeCard, compPlayer1);
		// test order 2: furthest player disproves
		result = game.handleSuggestion(testCard, gunCard, testCard, compPlayer2);
	}
}
