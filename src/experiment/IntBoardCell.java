package experiment;

public class IntBoardCell implements Comparable<IntBoardCell> {
	

	
	private int currentRow;
	private int currentCol;
	
	public IntBoardCell(int row, int col){
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
	public int compareTo(IntBoardCell o) {
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
