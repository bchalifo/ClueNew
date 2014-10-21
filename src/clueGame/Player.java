package clueGame;

import java.util.ArrayList;
import java.util.Random;

public class Player {	
	// instance variables
	private String name;
	private ArrayList<Card> cards;
	private Color color;
	
	// constructor
	public Player(String name, String color){
		this.name = name;
		this.color = Color.valueOf(color);
		this.cards = new ArrayList<Card>();
	}
	
	// default constructor
	public Player() {
		cards = new ArrayList<Card>();
	}
	
	// disprove suggestion made by another player
	public Card disproveSuggestion(Card person, Card weapon, Card room) {
		ArrayList<Card> matches = new ArrayList<Card>();
		ArrayList<Card> hand = this.getHand();
		// get matches
		for (Card card : hand) {
			if (card.equals(person) || card.equals(weapon) || card.equals(room)) {
				matches.add(card);
			}
		}
		// return random match or null if no matches
		if (matches.size() > 0) {
			Random rand = new Random();
			int randomIndex = rand.nextInt(matches.size());
			return matches.get(randomIndex);
		}
		return null;
	}

	// getters
	public String getName() {
		return name;
	}
	public Color getColor() {
		return color;
	}
	public ArrayList<Card> getHand() {
		return this.cards;
	}
	public void addCard(Card card){
		this.cards.add(card);
	}
}
