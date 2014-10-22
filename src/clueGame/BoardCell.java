package clueGame;

import java.awt.Graphics;

public abstract class BoardCell implements Comparable<BoardCell> {
	
	private int row;
	private int col;

	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public boolean isDoorway() {
		return false;
	}
	
	public abstract void draw(Graphics g, Board board);
	
	@Override
	public int compareTo(BoardCell o) {
		if(this.col == o.col && this.row == o.row) {
			return 0;
		}
		else {
			return -1;
		}
	}
	@Override
	public String toString() {
		return "Row: " + row + " Col: " + col;
	}
}
