package clueGame;

public class RoomCell extends BoardCell {
	
	public enum DoorDirection{
		UP, DOWN, LEFT, RIGHT, NONE;
	}
	
	private DoorDirection doorDirection;
	private char roomInitial;
	

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
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
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
