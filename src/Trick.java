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
        ArrayList<Card> cards = new ArrayList<>();

        //Get input from player
        ArrayList<Card> history = new ArrayList<>();
        for(int i = 0; i < players.length; i++) {
            System.out.println(players[i].name + "'s turn!");
            boolean valid = false;
            Card play;
            do {
                play = players[i].getPlay(history);
                //Make sure leader is the first player to play a card
                //Set first player's card suit to the lead suit
                if (i == 0) {
                    lead = play.suit;
                }
                //Check if the input is valid (ie. its following the lead card, or player does not have lead card)
                if (lead == play.suit || players[i].hand.getSuit(lead).isEmpty()) {
                    cards.add(play);
                    history.add(play);
                    valid = true;
                } else {
                    /**TODO
                     * Alert the player that the move isn't valid
                     */
                }
            } while (!valid);

            //Make sure to remove card from player's hand
            players[i].hand.removeCard(play);
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
