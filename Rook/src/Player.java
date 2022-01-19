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
}
