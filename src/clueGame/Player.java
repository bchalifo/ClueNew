package clueGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class Player {	
	// instance variables
	private String name;
	private ArrayList<Card> cards;
	private Color color;
	
	// default constructor
	public Player() {
		cards = new ArrayList<Card>();
	}

	// constructor with fields
	public Player(String name, String color){
		this.name = name;
		this.color = convertColor(color);
		this.cards = new ArrayList<Card>();
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
	
	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Color.class.getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}
	
	// draw the player on the board GUI
	public void draw(Graphics g, BoardCell cell, Board board) {
		int x = cell.getColumn() * Board.CELL_WIDTH;
		int y = cell.getRow() * Board.CELL_HEIGHT;
		
		g.setColor(Color.BLACK);
		g.drawOval(x, y, Board.CELL_WIDTH, Board.CELL_HEIGHT);
		g.setColor(color);
		g.fillOval(x, y, Board.CELL_WIDTH, Board.CELL_HEIGHT);
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
