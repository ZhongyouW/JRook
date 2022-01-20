public class Agent extends Player {
    public Agent(String name) {
        super(name);
    }

    public Agent(String name, Hand hand) {
        super(name, hand);
    }

    @Override
    public Card getPlay() {
        return null;
    }

    @Override
    public int getBid(int currentBid) {
        return 0;
    }

    @Override
    public Card.Suit getPreferredColor() {
        Card.Suit[] suit = Card.Suit.values();
        return suit[(int)(Math.random() * (suit.length + 1))];
    }
}