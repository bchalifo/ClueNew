package experiment;

import java.lang.reflect.Array;
import java.util.*;

public class IntBoard {
	
	private static final int ROWS=4;
	private static final int COLS=4;
	
	private Map<IntBoardCell, LinkedList<IntBoardCell>> adjMtx;
	private Set<IntBoardCell> visited;
	private Set<IntBoardCell> targets;
	private ArrayList<ArrayList<IntBoardCell>> board;
	
	// Constructor
	public IntBoard(){
		adjMtx = new HashMap<IntBoardCell, LinkedList<IntBoardCell>>();
		visited = new HashSet<IntBoardCell>();
		targets = new HashSet<IntBoardCell>();
		
		//Create board
		board = new ArrayList<ArrayList<IntBoardCell>>();
		for(int r = 0; r < ROWS; r++){
			board.add(new ArrayList<IntBoardCell>());
			for(int c = 0; c < COLS; c++){
				board.get(r).add(new IntBoardCell(r,c));
			}
		}
		
	}
	
	// Calculate adjacent cells for each cell and add to adjMtx
	public void calcAdjacencies(){
		LinkedList<IntBoardCell> tempList = new LinkedList<IntBoardCell>();
		for(int r = 0; r < ROWS; r++){
			for(int c = 0; c < COLS; c++){
				if(c+1 < COLS ) tempList.add(getCell(r,c+1));
				if( r+1 < ROWS) tempList.add(getCell(r+1,c));
				if(c-1 >= 0 ) tempList.add(getCell(r,c-1));
				if(r-1 >= 0) tempList.add(getCell(r-1,c));
				
				adjMtx.put(getCell(r,c), new LinkedList<IntBoardCell>(tempList));
				tempList.clear();
			}
		}

	}
	
	// Calculates targets and stores them in a list of targets
	public void calcTargets(IntBoardCell cell, int moves){
		IntBoardCell start = cell;
		recursion(cell,moves,start);
	}
	private void recursion(IntBoardCell cell, int moves, IntBoardCell start){
		if(moves == 0){
			if(!cell.equals(start)) targets.add(cell);
			visited.clear();
			return;
		}
		visited.add(cell);
		for(IntBoardCell c : adjMtx.get(cell)){
			if(!visited.contains(c)) recursion(c,moves-1,start);
		}
	}
	
	// Getter for targets
	public Set<IntBoardCell> getTargets(){
		return targets;
	}
	
	// returns list of adjacent cells to a cell
	public LinkedList<IntBoardCell> getAdjList(IntBoardCell cell){
		return  adjMtx.get(cell);
	}
	
	
	public IntBoardCell getCell(int row, int col){
		return board.get(row).get(col);
	}
	

}
