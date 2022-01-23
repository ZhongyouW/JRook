import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//TODO: Test this class!
public class Trick {
    Player[] players;
    Player leader;
    Card.Suit lead = null;
    Card.Suit trump;
    public Trick(Player[] players, Card.Suit trump, Player leader) {
        this.players = players;
        this.trump = trump;
        this.leader = leader;
    }

    public Player play() {
        Card.Suit lead = null;
        ArrayList<Card> cards = new ArrayList<>();

        //Get input from player
        for(int i = 0; i < players.length; i++) {
            //Make sure leader is the first player to play a card
            //Set first player's card suit to the lead suit
            //Check if the input is valid (ie. its following the lead card, or its a rook)
            //Make sure to remove card from player's hand
            //Fill cards
        }

        Player winner = getMostValuble(cards).owner;
        //Give the card to the Winner's pile
        for(Card card : cards) {
            winner.taken.addCard(card);
        }

        //self destruct?
        //Return winner so the next trick can start with the winner
        return winner;
    }

    private Card getMostValuble(ArrayList<Card> cards) {
        Card winCard = cards.get(0);
        for(Card card : cards) {
            int comparison = getSuitValue(card) - getSuitValue(winCard);

            if(comparison > 0) {
                //More powerful suit
                winCard = card;
            } else if(comparison == 0) {
                //Same power suit
                if(card.value - winCard.value > 0) {
                    winCard = card;
                }
            }
        }
        return winCard;
    }

    private int getSuitValue(Card card) {
        if(card.suit == trump) {
            return 2;
        } else if(card.suit == lead) {
            return 1;
        } else {
            return 0;
        }
    }
}
