/** Represents any entity playing the game, human or agent.
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
abstract class Player {
    String name;
    short points;
    Hand hand;

    public Player(String name, Hand hand) {
        this.name = name;
        this.hand = hand;
    }
    public Player(String name) {
        this(name, new Hand());
    }

    /**
     * @return the card which this player will be playing this turn.
     */
    public abstract Card getPlay();
    /**
     * @return the bid which the player wishes to gamble on, between 70 and 120, return -1 to pass.
     */
    public abstract int getBid(int currentBid);

    /**
     * @return return the color which this player wish to be the trump card
     */
    public abstract Card.Suit getPreferredColor();
}
