package classes;

import java.lang.reflect.Array;
import java.util.*;

public class IntBoard {
	
	private static final int ROWS=4;
	private static final int COLS=4;
	
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private Map<Integer[],BoardCell> board;
	
	// Constructor
	public IntBoard(){
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		//Create board
		board = new HashMap<Integer[],BoardCell>();
		for(int r = 0; r < ROWS; r++){
			for(int c = 0; c < COLS; c++){
				Integer[] key = {r,c};
				board.put(key,new BoardCell(r,c));
			}
		}
		
	}
	
	// Calculate adjacent cells for each cell and add to adjMtx
	public void calcAdjacencies(){
		LinkedList<BoardCell> tempList = new LinkedList<BoardCell>();
		
		for(int r = 0; r < ROWS; r++){
			for(int c = 0; c < COLS; c++){
				if(c+1 < COLS ) tempList.add(getCell(r,c+1));
				if( r+1 < ROWS) tempList.add(getCell(r+1,c));
				if(c-1 >= 0 ) tempList.add(getCell(r,c-1));
				if(r-1 >= 0) tempList.add(getCell(r-1,c));
				
				adjMtx.put(new BoardCell(r,c), new LinkedList<BoardCell>(tempList));
			}
			tempList.clear();
		}

	}
	
	// Calculates targets and stores them in a list of targets
	public void calcTargets(BoardCell cell, int moves){
		if(moves == 0){
			if(!visited.contains(cell)) targets.add(cell);
			return;
		}
		visited.add(cell);
		for(BoardCell c : adjMtx.get(cell)){
			if(!visited.contains(c)) calcTargets(c,moves-1);
		}
		visited.clear();
		
		
	}
	
	// Getter for targets
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	// returns list of adjacent cells to a cell
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		return  adjMtx.get(cell);
	}
	
	
	public BoardCell getCell(int row, int col){
		Integer[] key = {row, col};
		return board.get(key);
	}
	

}
