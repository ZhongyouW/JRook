public class Human extends Player{
    public Human(String name) {
        super(name);
    }

    public Human(String name, Hand hand) {
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
        return null;
    }
}
