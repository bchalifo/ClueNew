package clueTests;

import static org.junit.Assert.*;

import java.util.Map;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.ClueGame;
import clueGame.RoomCell;

public class FileInitTests {
	
	private static Board board;
	private static final int ROOMS = 11;
	private static final int ROWS = 22;
	private static final int COLS = 23;

	@BeforeClass
	public static void init(){
		ClueGame game = new ClueGame("resources/clueLayout.csv", "resources/legend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void legendAndRooms(){
		Map<Character, String> rooms = board.getRooms();
		Assert.assertEquals(ROOMS, rooms.size());
		
		// Test legend
		Assert.assertEquals("Closet", rooms.get('X'));
		Assert.assertEquals("Library", rooms.get('L'));
		Assert.assertEquals("Bedroom", rooms.get('B'));
		Assert.assertEquals("Kitchen", rooms.get('K'));
		Assert.assertEquals("Dinning Room", rooms.get('D'));
		Assert.assertEquals("Bowling Alley", rooms.get('A'));
		Assert.assertEquals("Water Closet", rooms.get('W'));
		Assert.assertEquals("Office", rooms.get('O'));
		Assert.assertEquals("Family Room", rooms.get('F'));
		Assert.assertEquals("Observitory", rooms.get('S'));
		Assert.assertEquals("Walk way", rooms.get('.'));
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void dimension(){
		Assert.assertEquals(ROWS, board.getNumRows());
		Assert.assertEquals(COLS, board.getNumColumns());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testDoor(){
		RoomCell room = board.getRoomCellAt(4, 1);
		Assert.assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		
		room = board.getRoomCellAt(6, 18);
		Assert.assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		
		room = board.getRoomCellAt(9, 15);
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.getRoomCellAt(9,21);
		Assert.assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		
		int numDoors = 0;
		for(int r = 0; r < ROWS; r++)
		{
			for(int c = 0; c < COLS; c++)
			{
				if(board.getCellAt(r, c).isDoorway()) numDoors++;
			}
		}
		System.out.println(numDoors);
		Assert.assertEquals(20, numDoors);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testRoomType(){
		Assert.assertEquals("X", board.getRoomCellAt(9, 9).getInitial());
		Assert.assertEquals("L", board.getRoomCellAt(16, 15).getInitial());
		Assert.assertEquals("B", board.getRoomCellAt(0, 18).getInitial());
		Assert.assertEquals("K", board.getRoomCellAt(9, 18).getInitial());
		Assert.assertEquals("D", board.getRoomCellAt(21, 0).getInitial());
		Assert.assertEquals("A", board.getRoomCellAt(0, 0).getInitial());
		Assert.assertEquals("W", board.getRoomCellAt(13, 10).getInitial());
		Assert.assertEquals("O", board.getRoomCellAt(4, 9).getInitial());
		Assert.assertEquals("F", board.getRoomCellAt(12, 0).getInitial());
		Assert.assertEquals("S", board.getRoomCellAt(19, 6).getInitial());
		Assert.assertEquals(".", board.getRoomCellAt(15, 0).getInitial());
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testLayout() throws BadConfigFormatException{
		ClueGame game = new ClueGame("resources/badLayout.csv", "resources/legend.txt");
		game.getBoard().loadBoardConfig();
		game.loadConfigFiles();
		
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testLegend() throws BadConfigFormatException{
		ClueGame game = new ClueGame("resources/clueLayout.csv", "resources/badLegend.txt");
		game.getBoard().loadBoardConfig();
		game.loadConfigFiles();
		
	}

}
