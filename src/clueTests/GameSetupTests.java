package clueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;

import org.junit.*;

import clueGame.*;
import clueGame.Card.CardType;

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
	private ArrayList<Card> cards;
	
	@BeforeClass
	public void setUpPeople(){
		ClueGame game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		players = game.getPlayers();
		playerLocations = game.getPlayerLocations();
		cards = game.getCards();
	}
	
	@Test
	public void testLoadingPlayers(){
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
	
	// This tests that cards are loaded correctly from file
	@Test
	public void testLoadingCards() {
		// deck contains the correct total number of cards
		assertEquals(cards.size(), 21);
		// deck contains the correct number of cards of each type
		int personCardCount = 0, roomCardCount = 0, weaponCardCount = 0;
		for (Card card : cards) {
			if (card.getType() == CardType.PERSON) {
				personCardCount++;
			}
			if (card.getType() == CardType.ROOM) {
				roomCardCount++;
			}
			if (card.getType() == CardType.WEAPON) {
				weaponCardCount++;
			}
		}
		// check counts
		assertEquals(6, personCardCount);
		assertEquals(9, roomCardCount);
		assertEquals(6, weaponCardCount);
		// card names are loaded correctly
		String person = "Dr. Phil", room = "Bowling Alley", weapon = "Ray Gun";
		boolean personFound = false, roomFound = false, weaponFound = false;
		for (Card card : cards) {
			// found the person card
			if (card.getName().equals(person)) {
				personFound = true;
				assertEquals(card.getType(), CardType.PERSON);
			}
			// found the room card
			if (card.getName().equals(room)) {
				roomFound = true;
				assertEquals(card.getType(), CardType.ROOM);
			}
			// found the room card
			if (card.getName().equals(weapon)) {
				weaponFound = true;
				assertEquals(card.getType(), CardType.WEAPON);
			}
			// make sure they were found
			assert(personFound && roomFound && weaponFound);
		}
	}
}
