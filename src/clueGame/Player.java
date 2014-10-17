package clueGame;

import java.util.ArrayList;

public class Player {	
	// instance variables
	private String name;
	private ArrayList<Card> cards;
	private Color color;
	
	// constructor
	public Player(String name, String color){
		this.name = name;
		this.color = Color.valueOf(color);
	}
	
	// default constructor
	public Player() {
		
	}
	
	// disprove suggestion made by another player
	public boolean disproveSuggestion(String person, String card, String weapon) {
		return false;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}
}
