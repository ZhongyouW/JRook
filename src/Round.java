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
	int bid;
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
		Player topBidder = startBid();
		Player winner = topBidder;
		// Set teams for point distribution
		Team offense;
		Team defense;
		if (Game.game.teams[0].team.contains(winner)) {
			offense = Game.game.teams[0];
			defense = Game.game.teams[1];
		} else {
			offense = Game.game.teams[1];
			defense = Game.game.teams[0];
		}

		trump = winner.getPreferredColor();
		if (trump == null)
			trump = Card.Suit.BLUE;
		Game.plays.add("The trump color is " + trump);
		Game.trump.setForeground(Card.suitColor.get(trump));
		trumpCard.suit = trump;

		trumpCard.owner.hand.getSuit(Card.Suit.BLUE).remove(trumpCard);
		trumpCard.owner.hand.getSuit(trump).add(trumpCard);
		// Give nest to topBidder and let them throw out 5 cards.
		// Play tricks until the players have no cards
		for (int i = 0; i < 14; i++) {
			Trick t = new Trick(players, trump, winner);
			currentTrick = t;
			winner = t.play();
			System.out.println(winner.name + " wins this trick!");
		}
		// Count points and add it to the team's point basket
		int points = 0;
		for (Player player : offense.team) {
			points += player.getPoints();
		}
		System.out.println("Offense team got " + points + " points");
		// Add bid according to if bid was reached
		offense.points += (points >= bid) ? bid : -bid;
		// Add points to losing team
		for (Player player : defense.team) {
			defense.points += player.getPoints();
		}
		// Reset
		for (Player player : players) {
			System.out.printf("%s have %d points\n", player.name, player.points);
			player.taken = new Hand();
		}
		
		String s = "<html><body>&emsp;Team 1: %d<br>&emsp;Team 2: %d<br/><br/></body></html>";
		Game.wins.setText(String.format(s, Game.game.teams[0].points, Game.game.teams[1].points));
		Game.wins.repaint();
	}

	Player startBid() {
		Player topBidder = players[0];

		ArrayList<Player> biddingPlayers = new ArrayList<Player>(Arrays.asList(players));
		bid = 70;
		while (biddingPlayers.size() > 1) {
			for (int i = 0; i < biddingPlayers.size(); i++) {
				int myBid;
				Player player = biddingPlayers.get(i);

				do {
					myBid = player.getBid(bid);
				} while (myBid <= bid && myBid != -1); // Repeat until the game gets valid input

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {

				}

				if (myBid > bid) {
					bid = myBid;
					topBidder = player;
					Game.plays.add(String.format("%s bid %d", player.name, bid));
					System.out.printf("%s bid %d\n", player.name, bid);
				} else {
					biddingPlayers.remove(i--);
					Game.plays.add(String.format("%s pass", player.name));
					System.out.printf("%s pass\n", player.name);
				}
				Game.plays.revalidate();
			}
		}
		Game.plays.repaint();
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