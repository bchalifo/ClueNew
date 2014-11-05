package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class CardDisplay extends JPanel {
	public static final int WIDTH = 200;
	
	public CardDisplay(ArrayList<Card> cards) {
		super();
		setLayout(new GridLayout(4,1));
		
		add(new JLabel("My Cards"));
		
		add(new PeoplePanel(cards));
		add(new RoomsPanel(cards));
		add(new WeaponsPanel(cards));
	}
	
	private class PeoplePanel extends JPanel {
		
		public PeoplePanel(ArrayList<Card> cards) {
			super();
			setBorder(new TitledBorder (new EtchedBorder(), "People"));
			setLayout(new GridLayout(3, 1));
			for(Card c : cards) {
				if(c.getType() == CardType.PERSON) {
					JTextArea text = new JTextArea(c.getName());
					text.setEditable(false);
					add(text);				}
			}
		}
	}
	
	private class RoomsPanel extends JPanel {
		public RoomsPanel(ArrayList<Card> cards) {
			super();
			setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
			setLayout(new GridLayout(3, 1));
			for(Card c : cards) {
				if(c.getType() == CardType.ROOM) {
					JTextArea text = new JTextArea(c.getName());
					text.setEditable(false);
					add(text);
				}
			}
		}
	}
	
	private class WeaponsPanel extends JPanel {
		public WeaponsPanel(ArrayList<Card> cards) {
			super();
			setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
			setLayout(new GridLayout(3, 1));
			for(Card c : cards) {
				if(c.getType() == CardType.WEAPON) {
					JTextArea text = new JTextArea(c.getName());
					text.setEditable(false);
					add(text);				}
			}
		}
	}

}
