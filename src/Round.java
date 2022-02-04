import java.awt.BorderLayout;

/** Represents a round of rook where players play tricks until the deck is empty
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
public class Round {
    Player[] players;
    Deck deck;
    Card.Suit trump;
    public static Boolean started = false, bidding = false;

    public Round(Player[] players) {
        this.players = players;
        deck = new Deck(1, 14);
        deck.shuffle();
        Card trumpCard = new Card(Card.Suit.BLUE, 0, true);
        deck.addCard(trumpCard);
        distributeCard(1);

        //Prototype game with only text input
        //Pre-round bidding
        started = true;
        Game.game.add(players[0].hand.panel, BorderLayout.SOUTH);
        Game.game.setVisible(true);
        Player topBidder = startBid();
        trump = topBidder.getPreferredColor();
        trumpCard.suit = trump;
        //Give nest to topBidder and let them throw out 5 cards.
        //Play tricks until the players have no cards
    }

    Player startBid() {
        bidding = true;
        Player topBidder = players[0];
        int bid = 70;
        int continuousPass = 0;
        while(bidding) {
            for (Player player : players) {
                int myBid = bid;

                do {
                    myBid = player.getBid(bid);
                } while(myBid <= bid && myBid != -1); //Repeat until the game gets valid input

                if(myBid > bid) {
                    bid = myBid;
                    topBidder = player;
                    System.out.printf("%s bid %d\n", player.name, bid);
                    continuousPass = 0;
                } else {
                    System.out.printf("%s pass\n", player.name);
                    continuousPass++;
                }
                if(continuousPass == players.length - 1) {
                    bidding = false;
                    break;
                }
            }
        }
        return topBidder;
    }

    void distributeCard(int remainder) {
        int index = 0;
        while(deck.size() > remainder) {
            Card drawn = deck.draw();
            Player player = players[index % players.length];

            drawn.owner = player;
            player.hand.addCard(drawn);

            index++;
        }
    }
}
