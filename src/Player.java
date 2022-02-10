import java.util.ArrayList;

/** Represents any entity playing the game, human or agent.
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
abstract class Player {
    String name;
    Hand hand;
    Team team;
    Boolean selected = false;
    Card selection;
    /**
     * Cards taken from tricks which will be tallied towards the points
     */
    boolean human;

    public Player(String name, Hand hand) {
        this.name = name;
        this.hand = hand;
    }
    public Player(String name, boolean human) {
        this(name, new Hand());
        this.human = human;
    }

    /**
     * @return the card which this player will be playing this turn.
     */
    public abstract Card getPlay(ArrayList<Card> history);

    public void setPlay(Card card){

    }
    /**
     * @return the bid which the player wishes to gamble on, between 70 and 120, return -1 to pass.
     */
    public abstract int getBid(int currentBid);

    /**
     * @return return the color which this player wish to be the trump card
     */
    public abstract Card.Suit getPreferredColor();
}