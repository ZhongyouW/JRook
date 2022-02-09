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
        preferredBid = Math.max(70, Math.min(180, preferredBid));
        if(currentBid < preferredBid) {
            return currentBid += 5;
        }
        return -1;
    }

    private Card.Suit getBiggestSuit() {
        Card.Suit biggest = Card.Suit.BLUE;
        Card.Suit[] options = Card.suits;
        for(Card.Suit suit : options) {
            if(hand.getSuit(suit).size() > hand.getSuit(biggest).size()) {
                biggest = suit;
            }
        }
        return biggest;
    }

    private Card.Suit getSmallestSuit() {
        Card.Suit smallest = Card.Suit.BLUE;
        Card.Suit[] options = Card.suits;
        for(Card.Suit suit : options) {
            if(hand.getSuit(suit).size() < hand.getSuit(smallest).size()) {
                smallest = suit;
            }
        }
        return smallest;
    }

    @Override
    public Card getPlay(ArrayList<Card> history) {
    	try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //This means trick haven't started and PwnPawn needs to discard a card
        if(Game.game.currentRound.currentTrick == null) {
            return getCardOfSuit(getSmallestSuit(), ValueType.RANDOM);
        }

        Card.Suit lead = Game.game.currentRound.currentTrick.lead;
        Card.Suit trump = Game.game.currentRound.currentTrick.trump;

        if(history.isEmpty()) {
            return getCardOfSuit(getBiggestSuit(), ValueType.HIGHEST);
        } else if(!hand.getSuit(lead).isEmpty()) {
            return getCardOfSuit(lead, ValueType.RANDOM);
        } else if(!hand.getSuit(trump).isEmpty()) {
            return getCardOfSuit(trump, ValueType.RANDOM);
        } else {
            return getCardOfSuit(getBiggestSuit(), ValueType.LOWEST);
        }
    }
}