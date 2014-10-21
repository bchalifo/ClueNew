package clueGame;

public class ComputerPlayer extends Player {
	// instance variables
	char lastRoomVisited;
	
	// default constructor
	public ComputerPlayer() {
		super();
	}

	// constructor with fields
	public ComputerPlayer(String name, String color) {
		super(name, color);
	}

	// picks a location from a list of targets
	public void pickLocation() {
		
	}
	
	// create suggestion
	public Suggestion createSuggestion(String room) {
		return null;
	}
	
	// mark card as seen
	void updateSeen(Card seen) {
		
	}
}
