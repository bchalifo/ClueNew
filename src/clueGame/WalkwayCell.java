package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {
	
	public WalkwayCell(int row, int col) {
		super(row, col);
	}
	
	@Override
	public boolean isWalkway(){
		return true;
	}

	@Override
	public void draw(Graphics g, Board board) {
		int x = super.getColumn() * Board.CELL_WIDTH;
		int y = super.getRow() * Board.CELL_HEIGHT;
		
		g.setColor(Color.lightGray);
		g.fillRect(x, y, Board.CELL_WIDTH, Board.CELL_HEIGHT);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, Board.CELL_WIDTH, Board.CELL_HEIGHT);
	}
}
