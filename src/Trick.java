import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//TODO: Test this class!
public class Trick {
    Player[] players;
    Player leader;
    Card.Suit lead = null;
    Card.Suit trump;
    static int player;
    
    public Trick(Player[] players, Card.Suit trump, Player leader) {
        this.players = players;
        this.trump = trump;
        this.leader = leader;
    }

    public Player play() {
        ArrayList<Card> cards = new ArrayList<>();
        //Offset so the winner goes first
        int offset = searchPlayer(leader);
        //Get input from player
        ArrayList<Card> history = new ArrayList<>();
        for(int i = 0; i < players.length; i++) {
            System.out.println(players[i].name + "'s turn!");
            boolean valid = false;
            Card play;
            player = (i + offset) % players.length;
            do {
                play = players[player].getPlay(history);
                //Make sure leader is the first player to play a card
                //Set first player's card suit to the lead suit
                if (i == 0) {
                    lead = play.suit;
                }
                //Check if the input is valid (ie. its following the lead card, or player does not have lead card)
                if (lead == play.suit || players[player].hand.getSuit(lead).isEmpty()) {
                    cards.add(play);
                    history.add(play);
                    valid = true;
                } else {
                    JOptionPane.showMessageDialog(null, "You can't play this card! Pick a card with the lead suit or any card if you don't have a lead suit");
                }
            } while (!valid);

            //Make sure to remove card from player's hand
            players[player].hand.removeCard(play);
            //Fill cards
        }

        Player winner = getMostValuble(cards).owner;
        //Give the card to the Winner's pile
        for(Card card : cards) {
            winner.taken.addCard(card);
        }

        //self destruct?
        //Return winner so the next trick can start with the winner
        Game.centerPanel.reset();
        return winner;
    }

    private int searchPlayer(Player target) {
        for(int i = 0; i < players.length; i++) {
            if(players[i] == target) {
                return i;
            }
        }
        return -1;
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