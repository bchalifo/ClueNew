package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

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
	
	public abstract void drawLabel(Graphics g, Board board);
	
	public void drawTarget(Graphics g, Board board) {
		g.setColor(Color.pink);
		g.fillOval(col*Board.CELL_WIDTH, row*Board.CELL_HEIGHT, Board.CELL_WIDTH, Board.CELL_HEIGHT);
	}
	
	public boolean isClicked(int mouseX, int mouseY, Board board){
		Rectangle rect = new Rectangle(col*board.CELL_WIDTH, row*board.CELL_HEIGHT, board.CELL_WIDTH, board.CELL_HEIGHT);
		if(rect.contains(new Point(mouseX, mouseY))) {
			return true;
		}
		return false;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return col;
	}
	
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
