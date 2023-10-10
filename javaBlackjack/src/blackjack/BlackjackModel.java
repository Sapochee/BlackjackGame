package blackjack;

import java.util.ArrayList;
import java.util.Random;

import deckOfCards.*;

public class BlackjackModel {

	//dealerCards - ArrayList of card for dealer
	private ArrayList<Card> dealerCards;
	//playerCards - ArrayList of card for player
	private ArrayList<Card> playerCards;
	//deck - deck variable for the game
	private Deck deck;
	
	//getters
	public ArrayList<Card> getDealerCards() {
		return new ArrayList<>(dealerCards);
	}
	public ArrayList<Card> getPlayerCards() {
		return new ArrayList<>(playerCards);
	}

	//setters
	public void setDealerCards(ArrayList<Card> cards) {
		dealerCards = new ArrayList<>(cards);
	}
	public void setPlayerCards(ArrayList<Card> cards) {
		playerCards = new ArrayList<>(cards);
	}
	
	/*
	 * creates a new instance of deck and shuffles the deck
	 */
	public void createAndShuffleDeck(Random random) {
		deck = new Deck();
		deck.shuffle(random);
	}

	/*
	 * deals the initial two cards for the dealer to start the game
	 */
	public void initialDealerCards() {
		dealerCards = new ArrayList<>();
		dealerCards.add(deck.dealOneCard());
		dealerCards.add(deck.dealOneCard());
	}

	/*
	 * deals the initial two cards for the player to start the game
	 */
	public void initialPlayerCards() {
		playerCards = new ArrayList<>();
		playerCards.add(deck.dealOneCard());
		playerCards.add(deck.dealOneCard());
	}

	/*
	 * takes one card from the deck and adds it to the player's hand/cards
	 */
	public void playerTakeCard() {
		playerCards.add(deck.dealOneCard());
	}

	/*
	 * takes one card from the deck and adds it to the dealer's hand/cards
	 */
	public void dealerTakeCard() {
		dealerCards.add(deck.dealOneCard());
	}

	/*
	 * calculates the possible values that exists for each hand depending on the
	 * card combinations
	 */
	public static ArrayList<Integer> possibleHandValues(ArrayList<Card> hand){
		ArrayList<Integer> values = new ArrayList<>();
		int currentValue = 0;
		for(Card card:hand) {
			currentValue += card.getRank().getValue();
		}
		values.add(currentValue);
		for(Card card:hand) {
			if(card.getRank()==Rank.ACE && currentValue+10<=21) {
				values.add(currentValue+10);
				break;
			}
		}
		return values;
	}

	/*
	 * checks the hand in order to come to see if any of the conditions for
	 * any of the results has been reached - in this case, the result of a
	 * natural blackjack, a hand busting, insufficient cards, or if the hand is
	 * normal
	 */
	public static HandAssessment assessHand(ArrayList<Card> hand) {
		if(hand==null || hand.size()<2) {
			return HandAssessment.INSUFFICIENT_CARDS;
		}
		ArrayList<Integer> handValues = possibleHandValues(hand);
		if(handValues.contains(21) && hand.size()==2) {
			return HandAssessment.NATURAL_BLACKJACK;
		}
		if(handValues.get(handValues.size()-1)>21) {
			return HandAssessment.BUST;
		}
		return HandAssessment.NORMAL;
	}

	/*
	 * method determines the outcome of the game by comparing the dealer's hand
	 * with the player's hand to see which hand has won in the case where the 
	 * dealer or player did not bust
	 */
	public GameResult gameAssessment() {
		ArrayList<Integer> dealerValue = possibleHandValues(dealerCards);
		ArrayList<Integer> playerValue = possibleHandValues(playerCards);
		if((playerValue.get(playerValue.size()-1)==21) &&
				(playerCards.size()==2)) {
			return GameResult.NATURAL_BLACKJACK;
		} else if((playerValue.get(playerValue.size()-1)>21)) {
			return GameResult.PLAYER_LOST;
		} else if((dealerValue.get(dealerValue.size()-1)>21)) {
			return GameResult.PLAYER_WON;
		} else if((playerValue.get(playerValue.size()-1)<
				dealerValue.get(dealerValue.size()-1))) {
			return GameResult.PLAYER_LOST;
		} else if((playerValue.get(playerValue.size()-1)>
				dealerValue.get(dealerValue.size()-1))) {
			return GameResult.PLAYER_WON;
		} else {
			return GameResult.PUSH;
		}
	}

	/*
	 * returns true in the case where the dealer should draw another card and
	 * false in the case where the dealer should stop drawing from the deck.
	 */
	public boolean dealerShouldTakeCard() {
		ArrayList<Integer> dealerValue = possibleHandValues(dealerCards);
		if(gameAssessment()==GameResult.PLAYER_LOST ||
				gameAssessment()==GameResult.NATURAL_BLACKJACK) {
			return false;
		} else if(dealerValue.get(dealerValue.size()-1)<17) {
			return true;
		} else {
		return false;
		}
	}
}