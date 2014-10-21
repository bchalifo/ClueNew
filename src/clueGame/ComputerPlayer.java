package clueGame;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
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

	// Computer selects a move based off of the input roll and possible moves
	// The selected BoardCell is returned.
	public BoardCell pickLocation(Set<BoardCell> targets){
		Random rando = new Random();		
		int randChoice = rando.nextInt(targets.size());
		ArrayList<BoardCell> targeto = new ArrayList<BoardCell>(targets);
		BoardCell choice = targeto.get(randChoice);		
		return choice;
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
