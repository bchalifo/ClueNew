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

	/*************************************************************************/
	// NEW STUFF
	public void makeSolution(){
		
	}
	
	public void deal() {
		Random r = new Random();
		int low = 0;
		while(!cards.isEmpty()){
			for(int i = 0; i < 6; i++){
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

	public void handleSuggestion(String person,
			String room,
			String weapon,
			Player accusingPerson)
	{

	}

	public boolean checkAccusation(Solution solution) {
		if(this.solution.equals(solution)){
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

	public ArrayList<Card> getCards() {
		return cards;
	}

	public ArrayList getPlayers(){
		return players;
	}

	public Map getPlayerLocations(){
		return playerLocations;
	}
	/*************************************************************************/
}
