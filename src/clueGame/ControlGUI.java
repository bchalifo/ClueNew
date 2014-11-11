package clueGame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.jar.Attributes.Name;

public class ControlGUI extends JPanel implements MouseListener {
	public static final int WIDTH = 700;
	public static final int HEIGHT = 150;
	private Board board;
	private ArrayList<Player> players;
	private Map<Player, BoardCell> playerLocations;
	private static ArrayList<Card> seenCards;
	private ArrayList<Card> deck;
	private int playerIndex;
	private boolean turnFinished, canMakeAccusation, isMakingChoice;
	private northPanel nPanel;
	private southPanel sPanel;
	private Solution solution;

	public ControlGUI(Board board, ArrayList<Player> players, Map<Player, BoardCell> playerLocations,
			ArrayList<Card> seenCards, ArrayList<Card> deck, Solution solution) {
		super();
		this.board = board;
		this.board.addMouseListener(this);
		this.players = players;
		this.playerLocations = playerLocations;
		this.seenCards = seenCards;
		this.deck = deck;
		this.solution = solution;
		playerIndex = 0;
		turnFinished = true;
		canMakeAccusation = false;
		isMakingChoice = false;
		setLayout(new GridLayout(2,1));

		nPanel = new northPanel();
		add(nPanel);

		sPanel = new southPanel();
		add(sPanel);
	}

	public class northPanel extends JPanel {
		private Button nextPlayer, makeAccusation;
		private JTextField turn;

		public northPanel() {
			super();
			JLabel label = new JLabel("Whose Turn?");
			turn = new JTextField(15);
			turn.setFont(new Font("SansSerif", Font.BOLD, 12));
			turn.setEditable(false);
			add(label);
			add(turn);
			nextPlayer = new Button("Next Player");
			nextPlayer.addActionListener(new ButtonListener());
			makeAccusation = new Button("Make an Accusation");
			makeAccusation.addActionListener(new ButtonListener());
			add(nextPlayer);
			add(makeAccusation);
		}

		public void displayPlayerTurn(Player player) {
			turn.setText(player.getName());
		}

		public class ButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == nextPlayer) {
					if(isMakingChoice) {
						JOptionPane.showMessageDialog(null, "You are't finished!");
					}
					else {
						nextPlayer(players.get(playerIndex));
					}
				}
				else if(e.getSource() == makeAccusation) {
					makeAccusation();
				}

			}
		}
	}

	public class dieRoll extends JPanel{
		private JTextField dieRoll;
		public dieRoll(){
			super();
			setBorder(new TitledBorder (new EtchedBorder(), "Die"));
			JLabel labelRoll = new JLabel("Roll");
			dieRoll = new JTextField(3);
			dieRoll.setFont(new Font("SansSerif", Font.BOLD, 12));
			dieRoll.setEditable(false);
			add(labelRoll);
			add(dieRoll);
		}

		public int rollDie() {
			Random rand = new Random();
			Integer randomNum = rand.nextInt(6) + 1;
			dieRoll.setText(randomNum.toString());
			return randomNum;
		}
	}

	public class guess extends JPanel{
		private JTextField guess;
		public guess(){
			super();
			setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
			JLabel labelGuess = new JLabel("Guess");
			guess = new JTextField(20);
			guess.setFont(new Font("SansSerif", Font.BOLD, 12));
			guess.setEditable(false);
			add(labelGuess);
			add(guess);
		}

		public void displayGuess(Card personCard, Card weaponCard, Card roomCard) {
			guess.setText(personCard.getName() + " " + weaponCard.getName() + " " + roomCard.getName());
		}
	}

	public class guessResult extends JPanel{
		private JTextField guessResult;
		public guessResult() {
			super();
			setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
			JLabel labelGuessResult = new JLabel("Guess Result");
			guessResult = new JTextField(10);
			guessResult.setFont(new Font("SansSerif", Font.BOLD, 12));
			guessResult.setEditable(false);
			add(labelGuessResult);
			add(guessResult);
		}
		public void displayResult(Card card) {
			guessResult.setText(card.getName());
		}
		public void displayResult(String string){
			guessResult.setText(string);
		}
	}

	public class southPanel extends JPanel{
		public dieRoll roll;
		private guess guess;
		private guessResult guessResult;

		public southPanel() {
			super();
			roll = new dieRoll();
			guess = new guess();
			guessResult = new guessResult();

			add(roll);
			add(guess);
			add(guessResult);
		}
	}

	public void nextPlayer(Player player) {
		if(!turnFinished) {
			JOptionPane.showMessageDialog(null, "Your turn isn't finished!");
			return;
		}
		// Iterate to next player
		playerIndex = (playerIndex + 1) % players.size();
		// Roll the die
		int roll = sPanel.roll.rollDie();
		// Change display of whose turn it is
		nPanel.displayPlayerTurn(player);
		// Get targets
		board.calcTargets(playerLocations.get(player).getRow(),
				playerLocations.get(player).getColumn(), roll);
		// Human Player
		if(player instanceof HumanPlayer) {
			turnFinished = false;
			canMakeAccusation = true;
			board.displayTargets();
			// See if target clicked is valid
			board.checkValidity();		
			board.repaint();
			return;
		}
		// Computer Player
		else {
			int count = 0;
			for(Card c : player.getHand()) {
				if(!seenCards.contains(c)) {
					count++;
				}
			}
			if((count + seenCards.size()) == deck.size() - 3) {
				JOptionPane.showMessageDialog(null, player.getName() + " has won. You lose. Solution: \n" + 
											solution.toString());
				System.exit(0);
			}
			BoardCell choice = player.pickLocation(board.getTargets());
			playerLocations.put(player, choice);
			board.repaint();
			if(choice.isRoom()) {
				makeSuggestion(player);
			}
		}
	}
	
	public void makeAccusation() {
		if(canMakeAccusation) {
			isMakingChoice = true;
			getHumanAccusation a = new getHumanAccusation(deck);
		}
		else {
			JOptionPane.showMessageDialog(null, "Wait for your turn!");
		}
	}
	
	public ArrayList<Card> generateGuess(String room, String person, String weapon){
		ArrayList<Card> guess = new ArrayList<Card>();
		for(Card c : deck){
			if(c.getName().equals(room)){
				guess.add(c);
			}
		}
		for(Card c : deck){
			if(c.getName().equals(person)){
				guess.add(c);
			}
		}
		for(Card c : deck){
			if(c.getName().equals(weapon)){
				guess.add(c);
			}
		}	
		return guess;
	}

	public void makeSuggestion(Player player) {
		Card result = new Card();
		// get current room
		String room = board.getRooms().get(((RoomCell) playerLocations.get(player)).getInitial());
		// Decide between human or computer player
		// Human player:
		if(player instanceof HumanPlayer){
			isMakingChoice = true;
			getHumanSuggestion t = new getHumanSuggestion(room, deck);
			return;
		}
		// Computer player:
		else{
			// Generate suggestion
			Suggestion s = ((ComputerPlayer) player).createSuggestion(room, seenCards, deck);
			// move player
			Card targetPlayer = s.getPerson();
			for(Player p : players) {
				if(targetPlayer.getName().equals(p.getName())) {
					playerLocations.put(p, playerLocations.get(player));
				}
			}
			board.repaint();
			result = handleSuggestion(s.getPerson(), s.getWeapon(), s.getRoom(), player);
		}
		if(result != null){
			seenCards.add(result);
			sPanel.guessResult.displayResult(result);
		}
		else{
			sPanel.guessResult.displayResult("no new clue");
		}
	}

	public class getHumanSuggestion extends JDialog{
		private ArrayList<String> suggestions;
		private JButton okButton;
		private JComboBox peopleBox;
		private JComboBox weaponBox;
		
		getHumanSuggestion(String room, ArrayList<Card> deck){
			suggestions = new ArrayList<String>();
			suggestions.add(room);
			setTitle("Human Suggestion");
			setSize(500, 500);
			setLayout(new GridLayout(4,2));
			add(new JLabel("Room"));
			JTextField roomField = new JTextField(room);
			roomField.setEditable(false);
			add(roomField);
			add(new JLabel("Person"));
			peopleBox = new JComboBox();
			for(Card c : deck) {
				if(c.getType() == CardType.PERSON) {
					peopleBox.addItem(c.getName());
				}
			}
			add(peopleBox);
			add(new JLabel("Weapon"));
			weaponBox = new JComboBox();
			for(Card c : deck) {
				if(c.getType() == CardType.WEAPON) {
					weaponBox.addItem(c.getName());
				}
			}
			add(weaponBox);
			okButton = new JButton("OK");
			okButton.addActionListener(new ButtonListener());
			add(okButton);
			setVisible(true);
		}
		
		public ArrayList<String> getSuggestions() {
			return suggestions;
		}
		
		public class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == okButton) {
					isMakingChoice = false;
					suggestions.add((String)peopleBox.getSelectedItem());
					suggestions.add((String)weaponBox.getSelectedItem());
					// move player
					String targetPlayer = suggestions.get(1);
					Player person = new Player();
					for(Player p : players) {
						if(targetPlayer.equals(p.getName())) {
							person = p;
							break;
						}
					}
					playerLocations.put(person, playerLocations.get(players.get(0)));
					board.repaint();
					setVisible(false);
					displaySuggestions(suggestions.get(0), suggestions.get(1), suggestions.get(2));
				}
			}
			
		}
		
		
	}
	
	public class getHumanAccusation extends JDialog{
		private ArrayList<String> accusations;
		private JButton submitButton;
		private JButton cancelButton;
		private JComboBox roomBox;
		private JComboBox peopleBox;
		private JComboBox weaponBox;
		
		getHumanAccusation(ArrayList<Card> deck){
			accusations = new ArrayList<String>();
			setTitle("Human Accusation");
			setSize(500, 500);
			setLayout(new GridLayout(4,2));
			add(new JLabel("Room"));
			roomBox = new JComboBox();
			for(Card c : deck) {
				if(c.getType() == CardType.ROOM) {
					roomBox.addItem(c.getName());
				}
			}
			add(roomBox);
			add(new JLabel("Person"));
			peopleBox = new JComboBox();
			for(Card c : deck) {
				if(c.getType() == CardType.PERSON) {
					peopleBox.addItem(c.getName());
				}
			}
			add(peopleBox);
			add(new JLabel("Weapon"));
			weaponBox = new JComboBox();
			for(Card c : deck) {
				if(c.getType() == CardType.WEAPON) {
					weaponBox.addItem(c.getName());
				}
			}
			add(weaponBox);
			submitButton = new JButton("Submit");
			submitButton.addActionListener(new ButtonListener());
			add(submitButton);
			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ButtonListener());
			add(cancelButton);
			setVisible(true);
		}
		
		public ArrayList<String> getSuggestions() {
			return accusations;
		}
		
		public class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == submitButton) {
					isMakingChoice = false;
					accusations.add((String)roomBox.getSelectedItem());
					accusations.add((String)peopleBox.getSelectedItem());
					accusations.add((String)weaponBox.getSelectedItem());
					setVisible(false);
					Solution solutionTest = new Solution(accusations.get(1), accusations.get(2), accusations.get(0));
					boolean isReal = checkAccusation(solutionTest);
					if(isReal) {
						JOptionPane.showMessageDialog(null, "You are correct. You win!");
						System.exit(0);
					}
					else {
						JOptionPane.showMessageDialog(null, "That's not correct.");
						turnFinished = true;
						board.removeTargets();
						board.repaint();
						return;
					}
				}
				else if(e.getSource() == cancelButton) {
					isMakingChoice = false;
					setVisible(false);
					return;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(isMakingChoice) {
			JOptionPane.showMessageDialog(null, "You haven't finished!");
		}
		if(!turnFinished) {
			for(BoardCell b : board.getTargets()) {
				if(b.isClicked(e.getX(), e.getY(), board)) {
					canMakeAccusation = false;
					playerLocations.put(players.get(0), b);
					board.removeTargets();
					board.repaint();
					if(b.isRoom()) {
						makeSuggestion(players.get(0));
					}
					turnFinished = true;
					return;
				}
			}
			JOptionPane.showMessageDialog(null, "Not a valid target!");
		}
		else {
			JOptionPane.showMessageDialog(null, "It's not your turn!");
		}

	}

	public void displaySuggestions(String room, String person, String weapon) {
		ArrayList<Card> theGuess = generateGuess(room, person, weapon);
		Card result = handleSuggestion(theGuess.get(0), theGuess.get(1), theGuess.get(2), players.get(0));
		if(result != null){
			sPanel.guessResult.displayResult(result);
			if(!seenCards.contains(result)){
				seenCards.add(result);
			}
		}
		else{
			sPanel.guessResult.displayResult("no new clue");
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	// handles player suggestions; returns matched card if a player can
	// disprove the suggestion, or null if no player can disprove it
	public Card handleSuggestion(Card person, Card weapon, Card room, Player accusingPlayer)
	{
		// Display guess
		sPanel.guess.displayGuess(person, weapon, room);
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
}
