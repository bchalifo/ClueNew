package clueGame;

import java.util.*;

public class ClueGame {
	// constants
	private final int ROWS = 22;
	private final int COLS = 23;
	// instance variables
	private Map<Character,String> rooms;
	private Board board;
	// new stuff
	private Solution solution;
	private ArrayList<Player> players;
	private Map<Player, BoardCell> playerLocations;
	private ArrayList<Card> cards;
	
	// constructor
	public ClueGame(String layout, String legend) {
		board = new Board(layout,legend,ROWS,COLS);
		rooms = new HashMap<Character, String>();
	}
	// default constructor
	public ClueGame() {
		rooms = new HashMap<Character, String>();
		board = new Board("ClueLayout.csv", "ClueLegend.txt",ROWS,COLS);
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
		loadConfigFiles();
		
	}
}
