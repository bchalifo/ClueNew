package clueTests;

import java.util.LinkedList;
import java.util.Set;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;

public class PathTests {
	
	private static Board board;
	private static final int ROOMS = 11;
	private static final int ROWS = 22;
	private static final int COLS = 23;

	@BeforeClass
	public static void init(){
		ClueGame game = new ClueGame("resources/clueLayout.csv", "resources/legend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}

	//====================
	//       Test Adj   
	//====================
	//Orange cells are the corresponding test case
	@Test
	public void TestRooms() {
		LinkedList<BoardCell> testList = board.getAdjList(0, 0);
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(7, 0);
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(16, 3);
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(17, 5);
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(13, 10);
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(9, 10);
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(5, 11);
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(1, 18);
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(12, 17);
		Assert.assertEquals(0, testList.size());
		testList = board.getAdjList(17, 17);
		Assert.assertEquals(0, testList.size());
	}
	
	//Purple cells are the corresponding test case
	@Test
	public void inDoorTest(){
		LinkedList<BoardCell> testList = board.getAdjList(16, 1);
		Assert.assertEquals(1, testList.size());
		testList = board.getAdjList(18, 3);
		Assert.assertEquals(1, testList.size());
		testList = board.getAdjList(20, 6);
		Assert.assertEquals(1, testList.size());
		testList = board.getAdjList(12, 18);
		Assert.assertEquals(1, testList.size());
		Assert.assertEquals(board.getCellAt(15, 1), testList.get(0));
	}
	
	//Green cells are the corresponding test case
	@Test
	public void doorWallTest() {
		LinkedList<BoardCell> testList = board.getAdjList(9, 22);
		Assert.assertTrue(testList.contains(board.getCellAt(8, 22)));
		Assert.assertTrue(testList.contains(board.getCellAt(10, 22)));
		Assert.assertTrue(testList.contains(board.getCellAt(9, 21)));
		Assert.assertEquals(3, testList.size());
		
		testList = board.getAdjList(5, 18);
		Assert.assertTrue(testList.contains(board.getCellAt(5, 19)));
		Assert.assertTrue(testList.contains(board.getCellAt(5, 17)));
		Assert.assertTrue(testList.contains(board.getCellAt(6, 18)));
		Assert.assertTrue(testList.contains(board.getCellAt(4, 18)));
		Assert.assertEquals(4, testList.size());
		
		testList = board.getAdjList(13, 18);
		Assert.assertTrue(testList.contains(board.getCellAt(13, 19)));
		Assert.assertTrue(testList.contains(board.getCellAt(13, 17)));
		Assert.assertTrue(testList.contains(board.getCellAt(12, 18)));
		Assert.assertTrue(testList.contains(board.getCellAt(14, 18)));
		Assert.assertEquals(4, testList.size());
		
		testList = board.getAdjList(8,14);
		Assert.assertTrue(testList.contains(board.getCellAt(9, 15)));
		Assert.assertTrue(testList.contains(board.getCellAt(9, 13)));
		Assert.assertTrue(testList.contains(board.getCellAt(8, 14)));
		Assert.assertTrue(testList.contains(board.getCellAt(10, 14)));
		Assert.assertEquals(4, testList.size());
		
		testList = board.getAdjList(19, 4);
		Assert.assertTrue(testList.contains(board.getCellAt(19, 3)));
		Assert.assertTrue(testList.contains(board.getCellAt(19, 5)));
		Assert.assertTrue(testList.contains(board.getCellAt(20, 4)));
		Assert.assertTrue(testList.contains(board.getCellAt(20, 3)));
		Assert.assertEquals(4, testList.size());
		
		testList = board.getAdjList(11, 3);
		Assert.assertEquals(0, testList.size());
	}
	
	// light purple cells are test cases.
	@Test
	public void testBoardEdge(){
		
		LinkedList<BoardCell>testList = board.getAdjList(0, 11);
		Assert.assertTrue(testList.contains(board.getCellAt(0, 12)));
		Assert.assertTrue(testList.contains(board.getCellAt(1, 11)));
 		Assert.assertEquals(2, testList.size());
		
		testList = board.getAdjList(0, 22);
		Assert.assertTrue(testList.contains(board.getCellAt(0, 21)));
		Assert.assertTrue(testList.contains(board.getCellAt(1, 22)));
		Assert.assertEquals(2, testList.size());
		
		testList = board.getAdjList(21,22);
		Assert.assertTrue(testList.contains(board.getCellAt(20, 22)));
		Assert.assertTrue(testList.contains(board.getCellAt(21, 21)));
		Assert.assertEquals(2, testList.size());
		
		testList = board.getAdjList(14, 0);
		Assert.assertTrue(testList.contains(board.getCellAt(15, 0)));
		Assert.assertTrue(testList.contains(board.getCellAt(14, 1)));
		Assert.assertEquals(2, testList.size());
	}
	@Test
	public void testDoorwayCount(){
		int numDoors =  20;
		int count = 0;
		for(int r = 0; r < ROWS; r++){
			for(int c = 0; c < COLS; c++ ){
				if(board.getCellAt(r, c).isDoorway()){
					count++;
				}
			}
		}
		Assert.assertEquals(numDoors, count);
	}
	
	//====================
	//    Test Targets  
	//====================
	// Test case is marked by dark green
	@Test
	public void testWalkways(){
		
		board.calcTargets(7, 4, 2);
		Set targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(0, 0)));
	}

}
