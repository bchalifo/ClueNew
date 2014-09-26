package clueGame;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Board {
	private int numRows;
	private int numColumns;
	private BoardCell[][] board;
	private Map<Character,String> rooms;
	
	public void loadBoardConfig(){
		
	}

	public Map<Character, String> getRooms() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public RoomCell getRoomCellAt(int row, int col) {
		// TODO Auto-generated method stub
		return null;
	}

	public BoardCell getCellAt(int row, int col) {
		// TODO Auto-generated method stub
		return null;
	}

	public void calcAdjacencies() {
		// TODO Auto-generated method stub
		
	}

	public LinkedList<BoardCell> getAdjList(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<BoardCell> getTargets() {
		// TODO Auto-generated method stub
		return null;
	}

	public void calcTargets(int row, int col, int moves) {
		// TODO Auto-generated method stub
		
	}

}
