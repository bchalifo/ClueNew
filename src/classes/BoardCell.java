package classes;

public class BoardCell implements Comparable<BoardCell> {
	

	
	private int currentRow;
	private int currentCol;
	
	public BoardCell(int row, int col){
		currentCol = col;
		currentRow = row;
	}

	public int getRow() {
		return currentRow;
	}

	public int getCol() {
		return currentCol;
	}

	@Override
	public int compareTo(BoardCell o) {
		if(this.currentCol == o.currentCol && this.currentRow == o.currentRow)
			return 0;
		else
			return -1;
	}
	@Override
	public String toString(){
		return "Row: " + currentRow + " Col: " + currentCol;
	}
	

}
