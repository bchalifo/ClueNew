package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell {
	
	public enum DoorDirection{
		UP, DOWN, LEFT, RIGHT, NONE;
	}
	
	// instance variables
	private DoorDirection doorDirection;
	private char roomInitial;
	
	// constructor
	public RoomCell(int row, int col, String ID) throws BadConfigFormatException {
		super(row, col);
		roomInitial = ID.charAt(0);
		if(ID.length()>1){
			switch (ID.charAt(1)){
				case 'U': doorDirection = DoorDirection.UP;
						break;
				case 'D': doorDirection = DoorDirection.DOWN;
						break;
				case 'L': doorDirection = DoorDirection.LEFT;
						break;
				case 'R': doorDirection = DoorDirection.RIGHT;
						break;
				default: throw new BadConfigFormatException("Incorrect Room Format");
			}
		}
		else doorDirection = DoorDirection.NONE;
	}
	

	@Override
	public boolean isRoom(){
		return true;
	}
	
	@Override
	public boolean isDoorway(){
		if(doorDirection != DoorDirection.NONE) {
			return true; 
		}
		return false;
	}
	
	// draw room cell
	@Override
	public void draw(Graphics g, Board board) {
		g.setColor(Color.BLUE);
		g.drawRect(super.getRow() * 10, super.getColumn() * 10, 10, 10);
	}

	// get door direction
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	// get room initial
	public char getInitial() {
		return roomInitial;
	}
}
