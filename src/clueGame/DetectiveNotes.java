package clueGame;

import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {
	public DetectiveNotes() {
		// stuff
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
			setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
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
			add(new Checkbox("Dr. Phil"));
			add(new Checkbox("Popeye"));
			add(new Checkbox("Vlad"));
			add(new Checkbox("That Guy"));
			add(new Checkbox("Davey Jones"));
			add(new Checkbox("John Elway"));
		}
	}

	public class RoomBoxPanel extends JPanel{
		public RoomBoxPanel(){
			setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
			setLayout(new GridLayout(5,2));
			add(new Checkbox("Library"));
			add(new Checkbox("Bedroom"));
			add(new Checkbox("Kitchen"));
			add(new Checkbox("Dining Room"));
			add(new Checkbox("Bowling Alley"));
			add(new Checkbox("Water Closet"));
			add(new Checkbox("Office"));
			add(new Checkbox("Family Room"));
			add(new Checkbox("Observatory"));
		}
	}

	public class WeaponsBoxPanel extends JPanel{
		public WeaponsBoxPanel(){
			setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
			setLayout(new GridLayout(3,2));
			add(new Checkbox("Bear Hands"));
			add(new Checkbox("Bag of Rocks"));
			add(new Checkbox("Hot Soup"));
			add(new Checkbox("Fruit Cake Brick"));
			add(new Checkbox("Ray Gun"));
			add(new Checkbox("Really Depressing Thoughts"));
		}
	}

	private JComboBox<String> PeopleGuess(){
		//setBorder(new TitledBorder (new EtchedBorder(), "People Guess"));
		String[] people = {"Dr. Phil", "Popeye", "Vlad", "That Guy", 
				"Davey Jones", "John Elway"};
		JComboBox<String> PeopleCombo = new JComboBox<String>();
		for(int i = 0; i < 6; i++){
			PeopleCombo.addItem(people[i]);
		}
		return PeopleCombo;
	}
	private JComboBox<String> RoomGuess(){
		String[] rooms = {"Library", "Bedroom", "Kitchen", 
				"Dining Room", "Bowling Alley", "Water Closet", "Office",
				"Family Room", "Observatory"};
		JComboBox<String> RoomCombo = new JComboBox<String>();
		for(int i = 0; i < 6; i++){
			RoomCombo.addItem(rooms[i]);
		}
		return RoomCombo;
	}
	private JComboBox<String> WeaponGuess() {
		String[] weapons = {"Bear Hands", "Bag of Rocks", "Hot Soup", 
				"Fruit Cake Brick", "Ray Gun", "Really Depressing Thoughts"};
		JComboBox<String> WeaponCombo = new JComboBox<String>();
		for(int i = 0; i < 6; i++){
			WeaponCombo.addItem(weapons[i]);
		}
		return WeaponCombo;
	}
}
