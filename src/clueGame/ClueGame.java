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
		cards = new ArrayList<Card>();
		loadPlayers();
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
	}
	public void loadPlayers() {
		
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public Map getPlayerLocations(){
		return playerLocations;
	}
	/*************************************************************************/
}
