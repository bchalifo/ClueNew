package clueGame;

public enum Color {
	PURPLE("Purple"), RED("Red"), YELLOW("Yellow"), BLACK("Black"), 
		BLUE("Blue"), ORANGE("Orange");
	private String value;
	
	Color(String avalue){
		value = avalue;
	}
	
	public String toString(){
		return value;
	}
}
