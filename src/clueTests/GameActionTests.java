package clueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.junit.*;

import clueGame.*;
import clueGame.Card.CardType;

public class GameActionTests {
	// game information containers
	private static ClueGame game;
	private static Board board;
	private ArrayList<Player> players;
	private Map<Player, BoardCell> playerLocations;
	private Map<Player, RoomCell> playerLastRoom;
	private ArrayList<Card> cards;
	private ArrayList<Card> seenCards;
	
	@Before
	// set up game before each test
	public void setUp(){
		game = new ClueGame("resources/clueLayout.csv", "resources/legend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		players = game.getPlayers();
		playerLocations = game.getPlayerLocations();
		cards = game.getCards();
		seenCards = game.getSeenCards();
	}
	
	@Test
	public void testTarget(){		
		// Set Player to corner of map, no doors are possible.
		ComputerPlayer player = new ComputerPlayer();
		board.calcTargets(3, 3, 2);
		
		Set targets = board.getTargets();
		System.out.println(targets.size());
		
		int cell3_5 = 0;
		int cell5_3 = 0;
		int cell4_4 = 0;
		// Run the test 100 times:
		for(int i = 0; i < 100; i++){
			BoardCell selected = player.pickLocation(targets);
			if(selected == board.getCellAt(3, 5)){
				cell3_5++;
			}
			else if(selected == board.getCellAt(5, 3)){
				cell5_3++;
			}
			else if(selected == board.getCellAt(4, 4)){
				cell4_4++;
			}
			else{
				fail("Invalid Target Selected");
			}
		}
		// Make sure 100 moves were made and somewhat evenly:
		assertEquals(100, cell3_5 + cell5_3 + cell4_4);
		assertTrue(cell3_5 > 10);
		assertTrue(cell5_3 > 10);
		assertTrue(cell4_4 > 10);
	}

	//		// Should return Bowling Alley as the selected option:
	//		BoardCell chosen = game.chooseMove(targets);
	//		assertTrue(chosen.equals(board.getCellAt(4, 1)));
	//
	//		// Set That Guy player to the position of 6,2 on the board (reset location):
	//		// Room last visited should be Bowling Alley, now no longer on the list of options
	//		start = board.getCellAt(6, 2);
	//		playerLocations.put(players.get(3), start);
	//		chosen = game.chooseMove(targets);
	//		assertFalse(chosen.equals(board.getCellAt(4, 1)));

	// This test hard-codes a solution for the game and tests for if it is true
	// and once for which way it can fail in each category.
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
		assertEquals(result, null);
		// test order 1: first possible player disproves
		result = game.handleSuggestion(testCard, testCard, officeCard, compPlayer1);
		assertEquals(result, officeCard);
		// test order 2: furthest player disproves
		result = game.handleSuggestion(testCard, gunCard, testCard, compPlayer2);
		assertEquals(result, gunCard);
	}
	
	// This tests computer players making a suggestion
	@Test
	public void testComputerMakingSuggestion() {
		// setup
		Card philCard = new Card("Dr. Phil", CardType.PERSON);
		Card johnCard = new Card("John Elway", CardType.PERSON);
		Card bearCard = new Card("Bear Hands", CardType.WEAPON);
		Card gunCard = new Card("Ray Gun", CardType.WEAPON);
		Card bedroomCard = new Card("Bedroom", CardType.ROOM);
		Card familyRoomCard = new Card("Family Room", CardType.ROOM);
		ComputerPlayer compPlayer = new ComputerPlayer();
		compPlayer.addCard(philCard);
		compPlayer.addCard(johnCard);
		compPlayer.addCard(bearCard);
		compPlayer.addCard(gunCard);
		
		// make suggestion in bedroom with no seen cards
		seenCards = game.getSeenCards();
		RoomCell bedroomDoor = board.getRoomCellAt(3, 17);
		game.setPlayerLocation(compPlayer, bedroomDoor);
		String roomName = board.getRooms().get(bedroomDoor.getInitial());
		assertEquals("Bedroom", roomName);
		Suggestion suggestion = compPlayer.createSuggestion(roomName, seenCards);
		assertEquals(johnCard, suggestion.getPerson());
		assertEquals(gunCard, suggestion.getWeapon());
		assertEquals(bedroomCard, suggestion.getRoom());
		
		// make suggestion in family room with two seen cards
		game.addSeenCard(johnCard);
		game.addSeenCard(gunCard);
		seenCards = game.getSeenCards();
		RoomCell familyRoomDoor = board.getRoomCellAt(10, 3);
		roomName = board.getRooms().get(familyRoomDoor.getInitial());
		assertEquals("Family Room", roomName);
		suggestion = compPlayer.createSuggestion(roomName, seenCards);
		assertEquals(philCard, suggestion.getPerson());
		assertEquals(bearCard, suggestion.getWeapon());
		assertEquals(familyRoomCard, suggestion.getRoom());
	}
}

