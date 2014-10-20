package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import clueGame.Card.CardType;

public class ClueGame {
	// constants
	private final int ROWS = 22;
	private final int COLS = 23;
	// instance variables
	private Map<Character,String> rooms;
	private Board board;

	private Solution solution;
	private ArrayList<Player> players;
	private Map<Player, BoardCell> playerLocations;
	private ArrayList<Card> cards;

	// constructor
	public ClueGame(String layout, String legend) {
		// initialize containers
		board = new Board(layout,legend,ROWS,COLS);
		rooms = new HashMap<Character, String>();
		cards = new ArrayList<Card>();
		playerLocations = new HashMap<Player, BoardCell>();
		players = new ArrayList<Player>(6);
		solution = new Solution();
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
			this.loadCards();
			this.loadPlayers();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getLocalizedMessage());
			System.exit(0);
		} catch (FileNotFoundException e) {
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

	public void makeSolution(){
		this.solution = new Solution("Dr. Phil", "Really Depressing Thoughts", 
				"Bedroom");
	}
	
	// A function called separately to simulate dealing the deck out to each player.
	public void deal() {
		Random r = new Random();
		while(!cards.isEmpty()){
			for(int i = 0; i < players.size(); i++){
				if(cards.isEmpty()){
					break;
				}
				int high = cards.size();
				int rando = r.nextInt(high);
				players.get(i).addCard(cards.get(rando));
				cards.remove(rando);
			}
		}
	}

	public void selectAnswer() {

	}

	// handles player suggestions
	public Card handleSuggestion(Card person,
			Card room,
			Card weapon,
			Player accusingPerson)
	{
		return null;
	}

	// Checks an accusation against the game's solution
	public boolean checkAccusation(Solution assertion) {
		if(this.solution.equals(assertion)){
			return true;
		}
		return false;
	}

	// load the deck of cards from file
	public void loadCards() throws FileNotFoundException {
		// open cards file
		FileReader reader = new FileReader("resources/Cards.txt");
		Scanner in = new Scanner(reader);

		// read in data
		while (in.hasNextLine()) {
			String[] parts = in.nextLine().split(",");
			String type = parts[0];
			String name = parts[1];
			// set name and type of new card
			Card card = new Card();
			card.setName(name);
			switch(type) {
			case "person":
				card.setType(CardType.PERSON);
				break;
			case "room":
				card.setType(CardType.ROOM);
				break;
			case "weapon":
				card.setType(CardType.WEAPON);
				break;
			}
			// add card to the deck
			cards.add(card);
		}
		in.close();
	}

	// This function loads the players from the text file and instantiates a player
	// object with the given parameters and places them in their correct container.
	public void loadPlayers() {
		// This is the set up for the scanner to read the txt file
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

		// Read txt file and pull required data from each line
		while(people.hasNextLine()){
			String temp[];
			String line = people.nextLine();
			temp = line.split(";");
			// Get each player's respective name, color, and starting location
			String name = temp[0];
			String color = temp[1];
			String location = temp[2];

			// Change location from String to two integers
			String tempLoc[] = location.split(",");
			String row = tempLoc[0];
			String col = tempLoc[1];

			// Create boardCell copy of where each player starts.
			BoardCell tempCell = board.getCellAt(Integer.parseInt(row), 
					Integer.parseInt(col));
			// Davey Jones is our human player and the rest are computer players
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

	// Getters for various containers:
	public ArrayList<Card> getCards() {
		return cards;
	}

	public ArrayList<Player> getPlayers(){
		return players;
	}

	public Map<Player, BoardCell> getPlayerLocations(){
		return playerLocations;
	}
	
	// set players (needed for testing)
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
}
