package clueGame;

import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class DetectiveNotes extends JDialog {
	private ArrayList<Card> cards;
	private ArrayList<String> players;
	private ArrayList<String> rooms;
	private ArrayList<String> weapons;
	
	public DetectiveNotes(ArrayList<Card> cards) {
		this.cards = cards;
		players = new ArrayList<String>();
		rooms = new ArrayList<String>();
		weapons = new ArrayList<String>();
		for(Card c : cards) {
			if(c.getType() == CardType.PERSON){
				players.add(c.getName());
			}
			else if(c.getType() == CardType.ROOM){
				rooms.add(c.getName());
			}
			else {
				weapons.add(c.getName());
			}
		}
		// setup
		setTitle("Detective Notes");
		setSize(750, 400);
		setLayout(new GridLayout(3, 2));
		// create components
		PeopleBoxPanel pCheck = new PeopleBoxPanel();
		RoomBoxPanel rCheck = new RoomBoxPanel();
		WeaponsBoxPanel wCheck = new WeaponsBoxPanel();		
		PeopleGuessBox pGuess = new PeopleGuessBox();
		RoomGuessBox rGuess = new RoomGuessBox();
		WeaponGuessBox wGuess = new WeaponGuessBox();

		add(pCheck);
		add(pGuess);
		add(rCheck);
		add(rGuess);
		add(wCheck);
		add(wGuess);
	}

	public class PeopleGuessBox extends JPanel{
		public PeopleGuessBox(){
			setBorder(new TitledBorder (new EtchedBorder(), "People Guess"));
			setLayout(new GridLayout(1,1));
			JComboBox<String> box = PeopleGuess();
//			box.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent e){
//					//Do Stuff Later
//				}
//			});			
			add(box);
		}
	}
	
	public class RoomGuessBox extends JPanel{
		public RoomGuessBox(){
			setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
			setLayout(new GridLayout(1,1));
			JComboBox<String> box = RoomGuess();
//			box.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent e){
//					//Do Stuff Later
//				}
//			});			
			add(box);
		}
	}
	
	public class WeaponGuessBox extends JPanel{
		public WeaponGuessBox(){
			setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
			setLayout(new GridLayout(1,1));
			JComboBox<String> box = WeaponGuess();
//			box.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent e){
//					//Do Stuff Later
//				}
//			});			
			add(box);
		}
	}

	public class PeopleBoxPanel extends JPanel{

		public PeopleBoxPanel(){
			setBorder(new TitledBorder (new EtchedBorder(), "People"));
			setLayout(new GridLayout(3,2));
			for(String s : players) {
				add(new Checkbox(s));
			}
		}
	}

	public class RoomBoxPanel extends JPanel{
		public RoomBoxPanel(){
			setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
			setLayout(new GridLayout(5,2));
			for(String s : rooms) {
				add(new Checkbox(s));
			}
		}
	}

	public class WeaponsBoxPanel extends JPanel{
		public WeaponsBoxPanel(){
			setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
			setLayout(new GridLayout(3,2));
			for(String s : weapons) {
				add(new Checkbox(s));
			}
		}
	}

	private JComboBox<String> PeopleGuess(){
		JComboBox<String> PeopleCombo = new JComboBox<String>();
		PeopleCombo.addItem("Unknown");
		for(String s : players){
			PeopleCombo.addItem(s);
		}
		return PeopleCombo;
	}
	private JComboBox<String> RoomGuess(){
		JComboBox<String> RoomCombo = new JComboBox<String>();
		RoomCombo.addItem("Unknown");
		for(String s : rooms){
			RoomCombo.addItem(s);
		}
		return RoomCombo;
	}
	private JComboBox<String> WeaponGuess() {
		JComboBox<String> WeaponCombo = new JComboBox<String>();
		WeaponCombo.addItem("Unknown");
		for(String s : weapons){
			WeaponCombo.addItem(s);
		}
		return WeaponCombo;
	}
}
