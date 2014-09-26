package clueGame;

import java.util.*;

public class ClueGame {
	// Constants
	private final int ROWS = 22;
	private final int COLS = 23;
	// variables
	private Map<Character,String> rooms;
	private Board board;
	//Constructor
	public ClueGame(String layout, String legend) {
		board = new Board(layout,legend,ROWS,COLS);
		rooms = new HashMap<Character, String>();
	}

	public ClueGame() {
		rooms = new HashMap<Character, String>();
	}

	// Methods
	public void loadConfigFiles(){
		try {
			board.loadBoardConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getLocalizedMessage());
			System.exit(0);
		}
	}

	public Board getBoard() {
		return board;
	}

	public void loadRoomConfig() {
		// TODO Auto-generated method stub
		
	}
}
