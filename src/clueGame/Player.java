package clueGame;

import java.util.ArrayList;

public class Player {
	
	// instance variables
	private String name;
	private ArrayList<Card> cards;
	
	// default constructor
	public Player() {
		
	}
	
	// disprove suggestion made by another player
	public boolean disproveSuggestion() {
		return false;
	}
}
