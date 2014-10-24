package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class ClueGame extends JFrame {
	
	// constants
	private final int ROWS = 22;
	private final int COLS = 23;
	// instance variables
	private Map<Character,String> rooms;
	private Board board;
	private DetectiveNotes detectiveNotes;
	private JMenuBar menuBar;
	private Solution solution;
	private ArrayList<Player> players;
	private Map<Player, BoardCell> playerLocations;
	private Map<Player, RoomCell> playerLastRoom;
	private ArrayList<Card> cards;
	private ArrayList<Card> seenCards;

	// default constructor
	public ClueGame() {
		// initialize containers
		rooms = new HashMap<Character, String>();
		board = new Board("ClueLayout.csv", "ClueLegend.txt",ROWS,COLS);
		detectiveNotes = new DetectiveNotes();
		menuBar = new JMenuBar();
		rooms = new HashMap<Character, String>();
		cards = new ArrayList<Card>();
		seenCards = new ArrayList<Card>();
		playerLocations = new HashMap<Player, BoardCell>();
		playerLastRoom = new HashMap<Player, RoomCell>();
		players = new ArrayList<Player>(6);
		solution = new Solution();
		// initalize GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}

	// constructor with game configuration files
	public ClueGame(String layout, String legend) {
		// initialize containers
		board = new Board(layout,legend,ROWS,COLS);
		detectiveNotes = new DetectiveNotes();
		menuBar = new JMenuBar();
		rooms = new HashMap<Character, String>();
		cards = new ArrayList<Card>();
		seenCards = new ArrayList<Card>();
		playerLocations = new HashMap<Player, BoardCell>();
		playerLastRoom = new HashMap<Player, RoomCell>();
		players = new ArrayList<Player>(6);
		solution = new Solution();
		// initialize GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}
	
	// create the file menu
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createDetectiveNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}
	
	// create the detective notes menu option
	private JMenuItem createDetectiveNotesItem() {
		JMenuItem dn = new JMenuItem("Detective Notes");
		// open detective notes when clicked
		dn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detectiveNotes.setVisible(true);
			}
		});
		return dn;
	}

	// create the menu exit option
	private JMenuItem createFileExitItem() {
		JMenuItem exit = new JMenuItem("Exit");
		// exit program when clicked
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		return exit;
	}
	
	// configure game from files
	public void loadConfigFiles(){
		try {
			board.loadBoardConfig();
			this.loadCards();
			this.loadPlayers();
			board.updatePlayerLocations(playerLocations);
		} catch (BadConfigFormatException e) {
			System.out.println(e.getLocalizedMessage());
			System.exit(0);
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
			System.exit(0);
		}
	}

	// hard coded solution for testing
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

	// ??
	public void selectAnswer() {

	}

	// handles player suggestions; returns matched card if a player can
	// disprove the suggestion, or null if no player can disprove it
	public Card handleSuggestion(Card person,
			Card weapon,
			Card room,
			Player accusingPlayer)
	{
		int nextPlayerIndex = players.indexOf(accusingPlayer) + 1;
		// check each player to disprove
		while (true) {
			Player nextPlayer = players.get(nextPlayerIndex % players.size());
			// no player can disprove, so break and return null
			if (nextPlayer == accusingPlayer) {
				break;
			}
			Card result = nextPlayer.disproveSuggestion(person, weapon, room);
			// no match, check next player
			if (result == null) {
				nextPlayerIndex++;
			}
			// match, return the result
			else {
				return result;
			}
		}
		return null;
	}

	// Checks an accusation against the game's solution
	public boolean checkAccusation(Solution accusation) {
		if(this.solution.equals(accusation)){
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
		people.close();
	}

	// getters/setters for various containers:
	public Board getBoard() {
		return board;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}

	public ArrayList<Player> getPlayers(){
		return players;
	}

	public Map<Player, BoardCell> getPlayerLocations(){
		return playerLocations;
	}

	public Map getPlayerLastRoom(){
		return playerLastRoom;
	}
	// set players (needed for testing)
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void addSeenCard(Card card) {
		seenCards.add(card);
	}

	public void setPlayerLocation(Player player, BoardCell cell) {
		playerLocations.put(player, cell);
	}
	
	// main
	public static void main(String args[]) {
		// initialize and configure game
		ClueGame game = new ClueGame("resources/clueLayout.csv", "resources/legend.txt");
		game.loadConfigFiles();
		Board board = game.getBoard();
		board.calcAdjacencies();
		// configure GUI
		game.add(board, BorderLayout.CENTER);
		Dimension boardDimensions = new Dimension(game.COLS * Board.CELL_WIDTH, game.ROWS * Board.CELL_HEIGHT);
		game.getContentPane().setPreferredSize(boardDimensions);
		game.pack();
		game.setVisible(true);
	}
}
