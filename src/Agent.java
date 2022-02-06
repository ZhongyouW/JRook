import java.util.ArrayList;

public class Agent extends Player {
    protected enum ValueType {
        LOWEST, HIGHEST, RANDOM
    }
    public Agent(String name) {
        super(name, false);
    }

    @Override
    public Card getPlay(ArrayList<Card> history) {
        return null;
    }

    public Agent(String name, Hand hand) {
        super(name, hand);
    }

    @Override
    public int getBid(int currentBid) {
        return 0;
    }

    @Override
    public Card.Suit getPreferredColor() {
        Card.Suit[] suit = Card.Suit.values();
        return suit[(int)(Math.random() * suit.length)];
    }

    private Card typeCompare(Card one, Card two, ValueType vType) {
        switch (vType) {
            case LOWEST:
                if(one.value < two.value) {
                    return one;
                }
                return two;
            case HIGHEST:
                if(one.value > two.value) {
                    return one;
                }
                return two;
        }
        //If not LOWEST or HIGHEST, then its RANDOM
        if(Math.random() < 0.5) {
            return one;
        }
        return two;
    }

    protected Card getCardOfSuit(Card.Suit suit, ValueType vType) {
        ArrayList<Card> cards = hand.getSuit(suit);
        Card most = cards.get(0);

        for(Card card : cards) {
            most = typeCompare(most, card, vType);
        }

        return most;
    }
}