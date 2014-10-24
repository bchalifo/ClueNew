package clueGame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.util.jar.Attributes.Name;

public class ControlGUI extends JFrame{
	public ControlGUI(){
		setSize(new Dimension(700,200));
		setTitle("Clue - Control GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		northPanel nPanel = new northPanel();
		add(nPanel, BorderLayout.NORTH);

		southPanel sPanel = new southPanel();
		add(sPanel, BorderLayout.SOUTH);
	}

	public class northPanel extends JPanel{
		private Button nextPlayer, makeAccusation;
		private JTextField turn;

		public northPanel(){
			JLabel label = new JLabel("Whose Turn?");
			turn = new JTextField(20);
			turn.setFont(new Font("SansSerif", Font.BOLD, 12));
			add(label);
			add(turn);

			nextPlayer = new Button("Next Player");
			makeAccusation = new Button("Make an Accusation");
			add(nextPlayer);
			add(makeAccusation);
		}		
	}

	public class dieRoll extends JPanel{
		private JTextField dieRoll;
		public dieRoll(){
			setBorder(new TitledBorder (new EtchedBorder(), "Die"));
			JLabel labelRoll = new JLabel("Roll");
			dieRoll = new JTextField(3);
			dieRoll.setFont(new Font("SansSerif", Font.BOLD, 12));
			dieRoll.setEditable(false);
			add(labelRoll);
			add(dieRoll);
		}
	}
	
	public class guess extends JPanel{
		private JTextField guess;
		public guess(){
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
		public guessResult(){
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
		private dieRoll roll;
		private guess guess;
		private guessResult guessResult;

		public southPanel(){
			roll = new dieRoll();
			guess = new guess();
			guessResult = new guessResult();
			
			add(roll);
			add(guess);
			add(guessResult);
		}
	}

	public static void main(String[] args){
		ControlGUI gui = new ControlGUI();
		gui.setVisible(true);
	}
}
