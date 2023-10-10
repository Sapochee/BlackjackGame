package deckOfCards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {

	//private variable of Arraylist
	private ArrayList<Card> deck;
	
	//constructor with no parameters and 52 card
	public Deck() {
		this.deck = new ArrayList<Card>(52);
		for(Suit suit: Suit.values()) {
			for(Rank rank: Rank.values()) {
				this.deck.add(new Card(rank, suit));
			}
		}
	}
	
	/*
	 * shuffles the deck randomly using a random number generator that was put 
	 * through as an argument for the collections.shuffle
	 */
	public void shuffle(Random randomNumberGenerator) {
		Collections.shuffle(deck, randomNumberGenerator);
	}
 
	/*
	 * returns the deck with the first card removed
	 */
	public Card dealOneCard() {
		return deck.remove(0);
	}
}
