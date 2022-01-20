import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
/** Represents a complete collection of cards
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
public class Deck {
    LinkedList<Card> deck;
    //Start and end of the card numbers, standard card deck is 1-13
    int end;
    int start;

    /**
     * Class constructor, creates a deck with card #1-13 for each suit from Card.Suit
     * The deck will not be shuffled
     */
    public Deck() {
        this(1, 13);
    }
    /**
     * Class constructor, creates a deck with card #start-end for each suit from Card.Suit
     * ie. start = 1 and end = 13 will create a standard deck with 52 cards
     * The deck will not be shuffled
     */
    public Deck(int start, int end) {
        deck = new LinkedList<Card>();
        this.start = start;
        this.end = end;
        initDeck();
    }

    /**
     * Intiates the deck with (end-start) * #Card.Suit amount of cards.
     */
    public void initDeck() {
        deck.clear();
        for(Card.Suit suit : Card.Suit.values()) {
            for(int v = start; v <= end; v++) {
                deck.add(new Card(suit, v));
            }
        }
    }

    public void addCard(Card card) {
        deck.add((int)(Math.random()*deck.size()+1),card);
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * @return the card on the top of the deck
     */
    public Card draw() {
        return deck.poll();
    }

    public int size() {
        return deck.size();
    }
}
