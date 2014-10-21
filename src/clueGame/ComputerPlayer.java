package clueGame;

import java.util.ArrayList;
import java.util.Random;

import clueGame.Card.CardType;

public class ComputerPlayer extends Player {
	// instance variables
	char lastRoomVisited;
	
	// default constructor
	public ComputerPlayer() {
		super();
	}

	// constructor with fields
	public ComputerPlayer(String name, String color) {
		super(name, color);
	}

	// picks a location from a list of targets
	public void pickLocation() {
		
	}
	
	// create suggestion
	public Suggestion createSuggestion(String room, ArrayList<Card> seenCards) {
		ArrayList<Card> personSuggestions = new ArrayList<Card>();
		ArrayList<Card> weaponSuggestions = new ArrayList<Card>();
		Card roomSuggestion = new Card(room, CardType.ROOM);
		for (Card card : this.getHand()) {
			if (!(seenCards.contains(card))) {
				if (card.getType() == CardType.PERSON) {
					personSuggestions.add(card);
				}
				else if (card.getType() == CardType.WEAPON) {
					weaponSuggestions.add(card);
				}
			}
			
		}
		Random rand = new Random();
		Card personSuggestion = personSuggestions.get(rand.nextInt(personSuggestions.size()));
		Card weaponSuggestion = weaponSuggestions.get(rand.nextInt(weaponSuggestions.size()));
		Suggestion s = new Suggestion(personSuggestion, weaponSuggestion, roomSuggestion);
		return s;
	}
	
	// mark card as seen
	void updateSeen(Card seen) {
		
	}
}
