package clueGame;

public class Card {
	// card type
	public enum CardType {
		PERSON, WEAPON, ROOM
	}
	
	// instance variables
	private String name;
	private CardType type;
	
	// default constructor
	public Card() {
		
	}
	
	@Override
	public String toString() {
		return "Card [type=" + type + "]";
	}

	// constructor with fields
	public Card(String name, CardType type) {
		super();
		this.name = name;
		this.type = type;
	}
	
	// getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CardType getType() {
		return type;
	}
	public void setType(CardType type) {
		this.type = type;
	}

	// overrides for equals and hashcode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
