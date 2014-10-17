package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ClueGame {
	// constants
	private final int ROWS = 22;
	private final int COLS = 23;
	// instance variables
	private Map<Character,String> rooms;
	private Board board;

	/*************************************************************************/
	// NEW STUFF
	private Solution solution;
	private ArrayList<Player> players;
	private Map<Player, BoardCell> playerLocations;
	private ArrayList<Card> cards;
	/*************************************************************************/

	// constructor
	public ClueGame(String layout, String legend) {
		board = new Board(layout,legend,ROWS,COLS);
		rooms = new HashMap<Character, String>();
		playerLocations = new HashMap<Player, BoardCell>();
		players = new ArrayList<Player>(6);
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
			this.loadPlayers();
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

	/*************************************************************************/
	// NEW STUFF
	public void deal() {

	}

	public void selectAnswer() {

	}

	public void handleSuggestion(String person,
			String room,
			String weapon,
			Player accusingPerson)
	{

	}

	public boolean checkAccusation(Solution solution) {
		return false;
	}

	public void loadPlayers() {
		String playerFile = "resources/Players.txt";
		Scanner people = null;
		FileReader reader = null;
		try{
			reader = new FileReader(playerFile);
			people = new Scanner(reader);
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
			System.exit(0);
		}

		while(people.hasNextLine()){
			String temp[];
			String line = people.nextLine();
			temp = line.split(";");
			String name = temp[0];
			String color = temp[1];
			String location = temp[2];

			String tempLoc[] = location.split(",");
			String row = tempLoc[0];
			String col = tempLoc[1];
			
			BoardCell tempCell = board.getCellAt(Integer.parseInt(row), 
						Integer.parseInt(col));

			switch(name){
			case "Davey Jones":
				HumanPlayer hPerson = new HumanPlayer(name, color);
				playerLocations.put(hPerson, tempCell);
				players.add(hPerson);
				break;
			default:
				ComputerPlayer cPerson = new ComputerPlayer(name, color);				
				playerLocations.put(cPerson, tempCell);
				players.add(cPerson);
				break;
			}
		}
	}

	public ArrayList getPlayers(){
		return players;
	}

	public Map getPlayerLocations(){
		return playerLocations;
	}
	/*************************************************************************/
}
