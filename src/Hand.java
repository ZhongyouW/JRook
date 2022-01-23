import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
/** Represents a collection of cards in the possession of a player
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
public class Hand {
    public HashMap<Card.Suit, ArrayList<Card>> hand;

    /**
     * Class constructor that initiates the hand.
     */
    public Hand() {
        hand = new HashMap<>();
        for(Card.Suit suit : Card.Suit.values()) {
            this.hand.put(suit, new ArrayList<Card>());
        }
    }

    /**
     * Class constructor that accept an arraylist argument
     * @param hand
     */
    public Hand(ArrayList<Card> hand)
    {
        this();
        for(Card card : hand) {
            addCard(card);
        }
        
    }

    /**
     * Class constructor that accept an array argument
     * @param hand
     */
    public Hand(Card[] hand) {
        this(new ArrayList<Card>(Arrays.asList(hand)));
    }

    public void addCard(Card card) {
        hand.get(card.suit).add(card);
    }

    /**
     * @return The hand as a map of arraylist. With each arraylist being all the card of the suit, and the suit as the key.
     */
    public HashMap<Card.Suit, ArrayList<Card>> getHand() {
        return hand;
    }

    /**
     * @param suit - the desired suit
     * @return All the cards of the given suit in hand.
     */
    public ArrayList<Card> getSuit(Card.Suit suit) {
        return hand.get(suit);
    }

    public int getValue() {
        int value = 0;
        for(ArrayList<Card> suit : hand.values()) {
            for (Card card : suit) {
                switch (card.value) {
                    case 1:
                        value += 15;
                        break;
                    case 5:
                        value += 5;
                        break;
                    case 10:
                    case 14:
                        value += 10;
                        break;
                    case 20:
                        value += 20;
                        break;
                }
            }
        }
        return value;
    }

    public short getSize() {
        short size = 0;
        for(ArrayList<Card> suit : hand.values()) {
            size += suit.size();
        }
        return size;
    }

    public String toString() {
        String result = "";
        for (ArrayList<Card> suit : hand.values()) {
            for (Card card : suit) {
                result += card + ", ";
            }
        }
        return result;
    }
}
