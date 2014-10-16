package clueGame;

import java.util.ArrayList;

public class Player {
	// player color
	private enum Color {
		
	}
	
	// instance variables
	private String name;
	private ArrayList<Card> cards;
	private Color color;
	
	// default constructor
	public Player() {
		
	}
	
	// disprove suggestion made by another player
	public boolean disproveSuggestion(String person, String card, String weapon) {
		return false;
	}
}
