package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {
	// instance variables
	char lastRoomVisited;

	// default constructor
	public ComputerPlayer() {
		super();
	}

	public ComputerPlayer(String name, String color) {
		super(name, color);
	}

	// Computer selects a move based off of the input roll and possible moves
	// The selected BoardCell is returned.
	public BoardCell pickLocation(Set<BoardCell> targets){
		return null;
	}

	// create suggestion
	public void createSuggestion() {

	}

	// mark card as seen
	void updateSeen(Card seen) {

	}
}
