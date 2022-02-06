import javax.swing.*;
import java.util.*;

/**
 * Represents a round of rook where players play tricks until the deck is empty
 * 
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
public class Round {
	Player[] players;
	Deck deck;
	Card.Suit trump;
	Card trumpCard;
	public Trick currentTrick;
	public Boolean started = false;

	public Round(Player[] players) {
		this.players = players;
		deck = new Deck(1, 14);
		deck.shuffle();
		trumpCard = new Card(Card.Suit.BLUE, 0, true);
		deck.addCard(trumpCard);
		distributeCard(1);

		started = true;
	}

	public void start() {
		Player winner= startBid();
		trump = winner.getPreferredColor();
		JOptionPane.showMessageDialog(null, "The trump color is " + trump);
		Game.trump.setForeground(Card.suitColor.get(trump));
		trumpCard.suit = trump;
		// Give nest to topBidder and let them throw out 5 cards.
		// Play tricks until the players have no cards
		for (int i = 0; i < 14; i++) {
			Trick t = new Trick(players, trump, winner);
			currentTrick = t;
			winner = t.play();
			System.out.println(winner.name + " wins this trick!");
		}
	}

	Player startBid() {
		Player topBidder = players[0];

		ArrayList<Player> biddingPlayers = new ArrayList<Player>(Arrays.asList(players));
		int bid = 70;
		while (biddingPlayers.size() > 1) {
			for (int i = 0; i < biddingPlayers.size(); i++) {
				int myBid;
				Player player = biddingPlayers.get(i);

				do {
					myBid = player.getBid(bid);
				} while (myBid <= bid && myBid != -1); // Repeat until the game gets valid input

				if (myBid > bid) {
					bid = myBid;
					topBidder = player;
					System.out.printf("%s bid %d\n", player.name, bid);
				} else {
					biddingPlayers.remove(i--);
					System.out.printf("%s pass\n", player.name);
				}
			}
		}
		return topBidder;
	}

	void distributeCard(int remainder) {
		int index = 0;
		players[1].hand.sidePanel();
		players[1].hand.orientation = Hand.Orientation.EAST;
		players[2].hand.orientation = Hand.Orientation.SOUTH;
		players[3].hand.sidePanel();
		players[3].hand.orientation = Hand.Orientation.WEST;
		while (deck.size() > remainder) {
			Card drawn = deck.draw();
			Player player = players[index % players.length];
			drawn.owner = player;
			player.hand.addCard(drawn);

			index++;
		}
	}

}