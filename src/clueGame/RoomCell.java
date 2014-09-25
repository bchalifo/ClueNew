package clueGame;

public class RoomCell extends BoardCell {
	
	private enum DoorDirection{
		UP,DOWN,LEFT,RIGHT,NONE;
	}
	
	private DoorDirection doorDirection;
	private char roomInitial;
	

	public RoomCell(int row, int col) {
		super(row, col);
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean isRoom(){
		return true;
	}
	
	

}
