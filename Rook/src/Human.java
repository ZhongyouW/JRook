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
}
