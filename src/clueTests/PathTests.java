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
		board = game.getBoard();
		board.calcAdjacencies();
	}

	//====================
	//       Test Adj   
	//====================
	
	//Orange cells are the corresponding test case
	@Test
	public void TestRooms() {
		LinkedList<BoardCell> testList = board.getAdjList(0,0);
		Assert.assertEquals(0, testList.size());
		
		testList = board.getAdjList(7, 0);
		Assert.assertEquals(0, testList.size());
		
		testList = board.getAdjList(16, 3);
		Assert.assertEquals(0, testList.size());
		
		testList = board.getAdjList(17, 5);
		Assert.assertEquals(0, testList.size());
		
		testList = board.getAdjList(13, 10);
		System.out.println(testList);
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
		testList = board.getAdjList(19,5);
		Assert.assertEquals(1, testList.size());
		testList = board.getAdjList(12, 18);
		Assert.assertEquals(1, testList.size());
		testList = board.getAdjList(16,1);
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
		
		testList = board.getAdjList(9,14);
		Assert.assertTrue(testList.contains(board.getCellAt(9, 15)));
		Assert.assertTrue(testList.contains(board.getCellAt(9, 13)));
		Assert.assertTrue(testList.contains(board.getCellAt(8, 14)));
		Assert.assertTrue(testList.contains(board.getCellAt(10, 14)));
		Assert.assertEquals(4, testList.size());
		
		testList = board.getAdjList(19, 4);
		Assert.assertTrue(testList.contains(board.getCellAt(19, 3)));
		Assert.assertTrue(testList.contains(board.getCellAt(19, 5)));
		Assert.assertTrue(testList.contains(board.getCellAt(20, 4)));
		Assert.assertTrue(testList.contains(board.getCellAt(18, 4)));
		Assert.assertEquals(4, testList.size());
		
		testList = board.getAdjList(11, 3);
		Assert.assertTrue(testList.contains(board.getCellAt(11, 4)));
		Assert.assertTrue(testList.contains(board.getCellAt(12, 3)));
		Assert.assertEquals(2, testList.size());
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
	// Cases marked with light green with purple dot.
	@Test
	public void testDoorwayCount(){
		int count = 0;
		LinkedList<BoardCell>testList = board.getAdjList(11,3);
		for(BoardCell c : testList){
			if(c.isDoorway()) count++;
		}
		Assert.assertEquals(0, count);
		
		count = 0;
		testList = board.getAdjList(19, 4);
		for(BoardCell c : testList){
			if(c.isDoorway()) count++;
		}
		Assert.assertEquals(2, count);

	}
	
	//====================
	//    Test Targets  
	//====================
	
	// Test case is marked by dark green, targets marked by red text and grey cell
	@Test
	public void testWalkways(){
		
		board.calcTargets(7, 4, 2);
		Set targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(7, 2)));
		Assert.assertTrue(targets.contains(board.getCellAt(6, 3)));
		Assert.assertTrue(targets.contains(board.getCellAt(5, 4)));
		Assert.assertTrue(targets.contains(board.getCellAt(6, 5)));
		Assert.assertTrue(targets.contains(board.getCellAt(7, 6)));
		Assert.assertTrue(targets.contains(board.getCellAt(8, 5)));
		Assert.assertTrue(targets.contains(board.getCellAt(9, 4)));
		Assert.assertTrue(targets.contains(board.getCellAt(8, 3)));
		Assert.assertEquals(8, targets.size());
		
		board.calcTargets(15, 18, 2);
		targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(15, 16)));
		Assert.assertTrue(targets.contains(board.getCellAt(14, 17)));
		Assert.assertTrue(targets.contains(board.getCellAt(13, 18)));
		Assert.assertTrue(targets.contains(board.getCellAt(14, 19)));
		Assert.assertTrue(targets.contains(board.getCellAt(15,20)));
		Assert.assertTrue(targets.contains(board.getCellAt(16, 18))); // Enters room
		Assert.assertTrue(targets.contains(board.getCellAt(16, 17))); // Enters room
		Assert.assertEquals(7, targets.size());
		
		board.calcTargets(19, 11, 4);
		targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(16, 10)));
		Assert.assertTrue(targets.contains(board.getCellAt(16, 12)));
		Assert.assertTrue(targets.contains(board.getCellAt(17, 9)));
		Assert.assertTrue(targets.contains(board.getCellAt(17, 11)));
		Assert.assertTrue(targets.contains(board.getCellAt(17, 13)));
		Assert.assertTrue(targets.contains(board.getCellAt(18, 8)));
		Assert.assertTrue(targets.contains(board.getCellAt(18, 10)));
		Assert.assertTrue(targets.contains(board.getCellAt(18, 12)));
		Assert.assertTrue(targets.contains(board.getCellAt(19, 9)));
		Assert.assertTrue(targets.contains(board.getCellAt(19, 13)));
		Assert.assertTrue(targets.contains(board.getCellAt(20, 10)));
		Assert.assertTrue(targets.contains(board.getCellAt(20, 12)));
		Assert.assertTrue(targets.contains(board.getCellAt(21, 11)));
		Assert.assertTrue(targets.contains(board.getCellAt(21, 13)));
		Assert.assertTrue(targets.contains(board.getCellAt(15, 11))); // Enters Room
		Assert.assertTrue(targets.contains(board.getCellAt(20, 9))); // Enters Room
		Assert.assertEquals(16, targets.size());
		
		board.calcTargets(2, 12, 2);
		targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(0, 12)));
		Assert.assertTrue(targets.contains(board.getCellAt(1, 11)));
		Assert.assertTrue(targets.contains(board.getCellAt(3, 11)));
		Assert.assertTrue(targets.contains(board.getCellAt(2, 14)));
		Assert.assertEquals(4, targets.size());
	}
	
	// Test cells marked by Dark blue doorways
	@Test
	public void testLeaving(){
		
		board.calcTargets(4, 1, 2);
		Set targets = board.getTargets();
		
		Assert.assertTrue(targets.contains(board.getCellAt(5, 0)));
		Assert.assertTrue(targets.contains(board.getCellAt(6, 1)));
		Assert.assertTrue(targets.contains(board.getCellAt(5, 2)));
		Assert.assertEquals(3, targets.size());
		
		board.calcTargets(9, 15, 4);
		targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(6, 14)));
		Assert.assertTrue(targets.contains(board.getCellAt(7, 13)));
		Assert.assertTrue(targets.contains(board.getCellAt(7, 15)));
		Assert.assertTrue(targets.contains(board.getCellAt(8, 14)));
		Assert.assertTrue(targets.contains(board.getCellAt(9, 13)));
		Assert.assertTrue(targets.contains(board.getCellAt(10, 14)));
		Assert.assertTrue(targets.contains(board.getCellAt(11, 13)));
		Assert.assertTrue(targets.contains(board.getCellAt(11, 15)));
		Assert.assertTrue(targets.contains(board.getCellAt(12, 14)));
		Assert.assertEquals(9, targets.size());
	}

}
