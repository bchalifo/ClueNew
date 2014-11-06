package clueGame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.jar.Attributes.Name;

public class ControlGUI extends JPanel {
	public static final int WIDTH = 700;
	public static final int HEIGHT = 150;
	private Board board;
	private ArrayList<Player> players;
	private Map<Player, BoardCell> playerLocations;
	private int playerIndex;
	private boolean turnFinished;
	private northPanel nPanel;
	private southPanel sPanel;

	
	public ControlGUI(Board board, ArrayList<Player> players, Map<Player, BoardCell> playerLocations) {
		super();
		this.board = board;
		this.players = players;
		this.playerLocations = playerLocations;
		playerIndex = 0;
		turnFinished = true;
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
			nextPlayer.addActionListener(new NextPlayerListener());
			makeAccusation = new Button("Make an Accusation");
			add(nextPlayer);
			add(makeAccusation);
		}
		
		public void displayPlayerTurn(Player player) {
			turn.setText(player.getName());
		}
		
		public class NextPlayerListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == nextPlayer) {
					nextPlayer(players.get(playerIndex));
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
			//display message
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
			//turnFinished = false;
			board.displayTargets();
			board.repaint();
			return;
		}
		// Computer Player
		else {
			board.removeTargets();
			BoardCell choice = player.pickLocation(board.getTargets());
			playerLocations.put(player, choice);
			board.repaint();
		}
	}
}
