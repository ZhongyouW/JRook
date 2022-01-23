/** Represents the cards used in a card game
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
public class Card {
    public enum Suit {
        RED,
        GREEN,
        BLUE,
        BLACK,
    }

    boolean rook;
    Player owner;
    Suit suit;
    int value;

    public Card(Suit suit, int value) {
        this.value = value;
        this.suit = suit;
    }

    public Card(Suit suit, int value, Player owner) {
        this.value = value;
        this.suit = suit;
        this.owner = owner;
    }

    public Card(Suit suit, int value, boolean rook) {
        this(suit, value);
        this.rook = rook;
    }
    public String toString() {
        return suit + " " + value;
    }
}
