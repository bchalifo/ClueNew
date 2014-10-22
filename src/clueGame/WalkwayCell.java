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
		g.setColor(Color.ORANGE);
		g.drawRect(super.getRow() * 10, super.getColumn() * 10, 10, 10);
	}
}
