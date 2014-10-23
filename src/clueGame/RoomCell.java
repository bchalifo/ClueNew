package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RoomCell extends BoardCell {
	
	public enum DoorDirection{
		UP, DOWN, LEFT, RIGHT, NONE;
	}
	
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
				g.drawLine(x, y, x + Board.CELL_WIDTH, y);
				g.fillRect(x, y, Board.CELL_WIDTH, Board.CELL_HEIGHT);
			}
			// down door
			else if (this.doorDirection == DoorDirection.DOWN) {
				g.drawLine(x, y + Board.CELL_HEIGHT, x + Board.CELL_WIDTH, y + Board.CELL_HEIGHT);
				g.fillRect(x, y, Board.CELL_WIDTH, Board.CELL_HEIGHT);
			}
			// left door
			else if (this.doorDirection == DoorDirection.LEFT) {
				g.drawLine(x, y , x, y + Board.CELL_HEIGHT);
				g.fillRect(x, y, Board.CELL_WIDTH, Board.CELL_HEIGHT);
			}
			// right door
			if (this.doorDirection == DoorDirection.RIGHT) {
				g.drawLine(x + Board.CELL_WIDTH, y, x + Board.CELL_WIDTH, y + Board.CELL_HEIGHT);
				g.fillRect(x, y, Board.CELL_WIDTH, Board.CELL_HEIGHT);
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
