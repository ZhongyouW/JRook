import java.util.Scanner;

public class Human extends Player{
    Scanner kb = new Scanner(System.in);
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
        return kb.nextInt();
    }

    @Override
    public Card.Suit getPreferredColor() {
        return null;
    }
}
