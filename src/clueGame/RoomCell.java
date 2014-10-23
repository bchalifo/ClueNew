package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RoomCell extends BoardCell {
	
	// door direction and size in GUI
	public enum DoorDirection {
		UP, DOWN, LEFT, RIGHT, NONE;
	}
	public static final int DOOR_WIDTH = 5;
	
	// instance variables
	private DoorDirection doorDirection;
	private char roomInitial;
	private boolean isLabel;
	
	// constructor
	public RoomCell(int row, int col, String ID) throws BadConfigFormatException {
		super(row, col);
		isLabel = false;
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
				case 'N': doorDirection = DoorDirection.NONE;
					isLabel = true;
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
		// get x and y coordinates
		int x = super.getColumn() * Board.CELL_WIDTH;
		int y = super.getRow() * Board.CELL_HEIGHT;
		
		// draw cell
		g.setColor(Color.BLUE);
		g.fillRect(x, y, Board.CELL_WIDTH, Board.CELL_HEIGHT);
		
		// draw door if applicable
		if (this.isDoorway()) {
			g.setColor(Color.CYAN);
			// up door
			if (this.doorDirection == DoorDirection.UP) {
				g.fillRect(x, y, Board.CELL_WIDTH, DOOR_WIDTH);
			}
			// down door
			else if (this.doorDirection == DoorDirection.DOWN) {
				g.fillRect(x, y + Board.CELL_HEIGHT - DOOR_WIDTH,
						Board.CELL_WIDTH, DOOR_WIDTH);
			}
			// left door
			else if (this.doorDirection == DoorDirection.LEFT) {
				g.fillRect(x, y, DOOR_WIDTH, Board.CELL_HEIGHT);
			}
			// right door
			if (this.doorDirection == DoorDirection.RIGHT) {
				g.fillRect(x + Board.CELL_WIDTH - DOOR_WIDTH, y,
						DOOR_WIDTH, Board.CELL_HEIGHT);
			}
		}
		
		// draw room label if applicable
		if (isLabel) {
			String roomName = board.getRooms().get(roomInitial);
			g.setColor(Color.WHITE);
			g.drawString(roomName, x, y);
		}
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
