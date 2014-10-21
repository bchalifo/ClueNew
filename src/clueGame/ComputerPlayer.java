package clueGame;

import java.util.ArrayList;

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
		Card personSuggestion = new Card(), weaponSuggestion = new Card();
		Card roomSuggestion = new Card(room, CardType.ROOM);
		for (Card card : this.getHand()) {
			if (!(seenCards.contains(card))) {
				if (card.getType() == CardType.PERSON) {
					personSuggestion = card;
				}
				else if (card.getType() == CardType.WEAPON) {
					weaponSuggestion = card;
				}
			}
			
		}
		Suggestion s = new Suggestion(personSuggestion, weaponSuggestion, roomSuggestion);
		return s;
	}
	
	// mark card as seen
	void updateSeen(Card seen) {
		
	}
}
