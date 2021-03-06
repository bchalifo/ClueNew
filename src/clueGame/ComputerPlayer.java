package clueGame;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.Random;

import clueGame.Card.CardType;

public class ComputerPlayer extends Player {
	// instance variables
	char lastRoomVisited;
	ArrayList<BoardCell> targetList;

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
	public BoardCell pickLocation(Set<BoardCell> targets) {
		this.targetList = new ArrayList<BoardCell>(targets);
		int room = -1;

		for(int i = 0; i < targetList.size(); i++){
			if(targetList.get(i).isDoorway()){
				RoomCell door = (RoomCell) targetList.get(i);
				char targetDoor = door.getInitial();
				if(!(targetDoor == lastRoomVisited)){
					room = i;
					break;
				}
				else{
					targetList.remove(i);
				}
			}
		}

		if(room != -1){
			BoardCell choice = targetList.get(room);
			return choice;
		}
		else{
			Random rando = new Random();		
			int randChoice = rando.nextInt(targetList.size());
			BoardCell choice = targetList.get(randChoice);
			return choice;
		}
	}

	// create suggestion
	public Suggestion createSuggestion(String room, ArrayList<Card> seenCards, ArrayList<Card> deck) {
		ArrayList<Card> personSuggestions = new ArrayList<Card>();
		ArrayList<Card> weaponSuggestions = new ArrayList<Card>();
		Card roomSuggestion = new Card(room, CardType.ROOM);
		for (Card card : deck) {
			if(this.getHand().contains(card)){
				continue;
			}
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
		Card personSuggestion = new Card();
		Card weaponSuggestion = new Card();
		if(personSuggestions.size() > 0) {
			personSuggestion = personSuggestions.get(rand.nextInt(personSuggestions.size()));
		}
		if(weaponSuggestions.size() > 0) {
			weaponSuggestion = weaponSuggestions.get(rand.nextInt(weaponSuggestions.size()));
		}
		Suggestion s = new Suggestion(personSuggestion, weaponSuggestion, roomSuggestion);
		return s;
	}

	public void setLastRoom(char room){
		this.lastRoomVisited = room;
	}
}
