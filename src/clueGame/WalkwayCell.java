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
		int x = super.getColumn() * board.CELL_WIDTH;
		int y = super.getRow() * board.CELL_HEIGHT;
		
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, board.CELL_WIDTH, board.CELL_HEIGHT);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, board.CELL_WIDTH, board.CELL_HEIGHT);
	}
}
