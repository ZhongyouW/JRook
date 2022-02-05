import java.util.ArrayList;

/** A basic agent
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
public class PwnPawn extends Agent{
    double riskTolerance = 0;
    static int population = 0;
    boolean human = false;

    public PwnPawn() {
        super("PwnPawn "+population);
        riskTolerance = Math.random()*0.5 + 0.25;
        population++;
    }

    public PwnPawn(Hand hand) {
        super("PwnPawn "+population, hand);
        riskTolerance = Math.random();
        population++;
    }

    @Override
    public int getBid(int currentBid) {
        int value = hand.getValue();
        int preferredBid = ((int)Math.round((value * 4.5 * riskTolerance)/5))*5;
        //Clamp preferredBid between 200 and 70
        preferredBid = Math.max(70, Math.min(200, preferredBid));
        if(currentBid < preferredBid) {
            return currentBid += 5;
        }
        return -1;
    }

    @Override
    public Card getPlay(ArrayList<Card> history) {
        Card.Suit lead = Game.game.currentRound.currentTrick.lead;
        Card.Suit trump = Game.game.currentRound.currentTrick.trump;
        if(!hand.getSuit(lead).isEmpty()) {
            return hand.getSuit(lead).remove(hand.getSuit(lead).size()-1);
        }

        if(!hand.getSuit(trump).isEmpty()) {
            return hand.getSuit(trump).remove(hand.getSuit(trump).size()-1);
        }

        if(!hand.getSuit(Card.Suit.BLUE).isEmpty()) {
            return hand.getSuit(Card.Suit.BLUE).remove(hand.getSuit(Card.Suit.BLUE).size()-1);
        } else if(!hand.getSuit(Card.Suit.BLACK).isEmpty()) {
            return hand.getSuit(Card.Suit.BLACK).remove(hand.getSuit(Card.Suit.BLACK).size()-1);
        } else if(!hand.getSuit(Card.Suit.RED).isEmpty()) {
            return hand.getSuit(Card.Suit.RED).remove(hand.getSuit(Card.Suit.RED).size()-1);
        }
        return hand.getSuit(Card.Suit.GREEN).remove(hand.getSuit(Card.Suit.GREEN).size()-1);
    }
}