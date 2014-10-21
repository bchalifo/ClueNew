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

	// Tests if targets has a door option that does not equal
	// last room visited.
	public int roomOption(Set<BoardCell> targets){
		int room = -1;
		ArrayList<BoardCell> targetList = new ArrayList<BoardCell>(targets);		
		for(int i = 0; i < targetList.size(); i++){
			if(targetList.get(i).isDoorway()){
				return i;
			}
		}
		return room;
	}
	
	// Computer selects a move based off of the input roll and possible moves
	// The selected BoardCell is returned.
	public BoardCell pickLocation(Set<BoardCell> targets){
		ArrayList<BoardCell> targetList = new ArrayList<BoardCell>(targets);
		int room = roomOption(targets);
		if(room != -1){
			BoardCell choice = targetList.get(room);
			return choice;
		}
		
		Random rando = new Random();		
		int randChoice = rando.nextInt(targets.size());
		BoardCell choice = targetList.get(randChoice);		
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
	
	public void setLastRoom(char room){
		this.lastRoomVisited = room;
	}
}
