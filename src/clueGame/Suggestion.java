package clueGame;

public class Suggestion {
	private Card person;
	private Card weapon;
	private Card room;
	
	public Suggestion(Card person, Card weapon, Card room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	
	public Card getPerson() {
		return this.person;
	}
	
	public Card getWeapon() {
		return this.weapon;
	}
	
	public Card getRoom() {
		return this.room;
	}
}
