package clueTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

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
	private Map<Player, RoomCell> playerLastRoom;
	private ArrayList<Card> cards;

	@Before
	public void setUp(){
		ClueGame game = new ClueGame("resources/clueLayout.csv", "resources/legend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
//		players = game.getPlayers();
//		playerLocations = game.getPlayerLocations();
//		playerLastRoom = game.getPlayerLastRoom();
//		cards = game.getCards();
	}

	// This test hard-codes a solution for the game and tests for if it is true
	// and once for which way it can fail in each category.
//	@Test
//	public void testAccusation(){
//		game.makeSolution();
//		Solution solution = new Solution("Dr. Phil", "Really Depressing Thoughts",
//				"Bedroom");
//		assertTrue(game.checkAccusation(solution));
//		solution = new Solution("Vlad", "Really Depressing Thoughts", 
//				"Bedroom");
//		assertFalse(game.checkAccusation(solution));
//		solution = new Solution("Dr. Phil", "Really Depressing Thoughts", 
//				"Bowling Alley");
//		assertFalse(game.checkAccusation(solution));
//		solution = new Solution("Dr. Phil", "Bear Hands", "Bedroom");
//		assertFalse(game.checkAccusation(solution));
//	}

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
}

