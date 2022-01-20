/** Represents a round of rook where players play tricks until the deck is empty
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
public class Round {
    Player[] players;
    Deck deck;
    Card.Suit trump;

    public Round(Player[] players) {
        this.players = players;
        deck = new Deck(5, 14);
        Card trumpCard = new Card(Card.Suit.BLUE, 20, true);
        deck.addCard(trumpCard);
        distributeCard(5);
        //Prototype game with only text input
        //Pre-round bidding
        Player topBidder = startBid();
        trump = topBidder.getPreferredColor();
        trumpCard.suit = trump;
        //Give nest to topBidder and let them throw out 5 cards.
        //Play tricks until the players have no cards
    }

    Player startBid() {
        boolean bidding = true;
        Player topBidder = players[0];
        int bid = 70;
        while(bidding) {
            bidding = false;
            for (Player player : players) {
                int myBid = bid;

                do {
                    myBid = player.getBid(bid);
                } while(myBid > bid || myBid == -1); //Repeat until the game gets valid input

                if(myBid > bid) {
                    bid = myBid;
                    topBidder = player;
                }

                bidding |= myBid != -1;
            }
        }
        return topBidder;
    }

    void distributeCard(int remainder) {
        int index = 0;
        while(deck.size() > remainder) {
            players[index % players.length].hand.addCard(deck.draw());
            index++;
        }
    }
}
