package clueTests;

import java.util.LinkedList;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import classes.IntBoardCell;
import classes.IntBoard;

public class IntBoardTest {
	public IntBoard board;

	@Before
	public void init(){
		 board = new IntBoard();
		 board.calcAdjacencies();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void adjListTL() {
		IntBoardCell cell = board.getCell(0,0);
		LinkedList<IntBoardCell> list = board.getAdjList(cell);
		Assert.assertTrue(list.contains(board.getCell(1, 0)));
		Assert.assertTrue(list.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, list.size());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void adjListBR(){
		IntBoardCell cell = board.getCell(3, 3);
		LinkedList<IntBoardCell> list = board.getAdjList(cell);
		Assert.assertTrue(list.contains(board.getCell(2,3)));
		Assert.assertTrue(list.contains(board.getCell(3,2)));
		Assert.assertEquals(2, list.size());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void adjListRE(){
		IntBoardCell cell = board.getCell(1, 3);
		LinkedList<IntBoardCell> list = board.getAdjList(cell);
		Assert.assertTrue(list.contains(board.getCell(0,3)));
		Assert.assertTrue(list.contains(board.getCell(2,3)));
		Assert.assertTrue(list.contains(board.getCell(1,2)));
		Assert.assertEquals(3, list.size());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void adjListLE(){
		IntBoardCell cell = board.getCell(3, 0);
		LinkedList<IntBoardCell> list = board.getAdjList(cell);
		Assert.assertTrue(list.contains(board.getCell(3,1)));
		Assert.assertTrue(list.contains(board.getCell(2,0)));
		Assert.assertEquals(2, list.size());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void adjListM(){
		IntBoardCell cell = board.getCell(2, 2);
		LinkedList<IntBoardCell> list = board.getAdjList(cell);
		Assert.assertTrue(list.contains(board.getCell(2,3)));
		Assert.assertTrue(list.contains(board.getCell(2,1)));
		Assert.assertTrue(list.contains(board.getCell(1,2)));
		Assert.assertTrue(list.contains(board.getCell(3,2)));
		Assert.assertEquals(4, list.size());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void adjList2C(){
		IntBoardCell cell = board.getCell(1, 1);
		LinkedList<IntBoardCell> list = board.getAdjList(cell);
		Assert.assertTrue(list.contains(board.getCell(1,2)));
		Assert.assertTrue(list.contains(board.getCell(0,1)));
		Assert.assertTrue(list.contains(board.getCell(2,1)));
		Assert.assertTrue(list.contains(board.getCell(1,0)));
		Assert.assertEquals(4, list.size());
	}
	
	// Test path creation
	@Test
	public void testTargets_TL_3()
	{
		IntBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertEquals(6, targets.size());
	}
	
	@Test
	public void testTargets_BR_1()
	{
		IntBoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();

		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertEquals(6, targets.size());
	}
	@SuppressWarnings("deprecation")
	@Test
	public void testTargets_RE_2()
	{
		IntBoardCell cell = board.getCell(1, 3);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();

		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		Assert.assertEquals(4, targets.size());
	}
	@SuppressWarnings("deprecation")
	@Test
	public void testTargets_C1_1()
	{
		IntBoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 1);
		Set targets = board.getTargets();

		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertEquals(4, targets.size());

	}
	@SuppressWarnings("deprecation")
	@Test
	public void testTargets_C2_2()
	{
		IntBoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();

		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		Assert.assertEquals(6, targets.size());
	}
	@Test
	public void testTargets_C2_3()
	{
		IntBoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 3);
		Set targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertEquals(8, targets.size());
	}
	@Test
	public void testTargets_LE_2()
	{
		IntBoardCell cell = board.getCell(0, 2);
		board.calcTargets(cell, 2);
		Set targets = board.getTargets();

		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertEquals(4, targets.size());
	}

}
