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
}
