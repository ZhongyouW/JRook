import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Human extends Player{
    Scanner kb = new Scanner(System.in);
    final String regex = "^\\d+";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    boolean human = true;

    public Human(String name) {
        super(name, true);
    }

    public Human(String name, Hand hand) {
        super(name, hand);
    }
    @Override
    public Card getPlay(ArrayList<Card> history) {
        selected = false;
        while(!selected) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return selection;
    }

    @Override
    public void setPlay(Card card) {
        selection = card;
        selected = true;
    }


    @Override
    public int getBid(int currentBid) {
        int x = -1;
        do {
            String bid = Game.getBid(currentBid);
            if(bid == null)
                break;
            Matcher matcher = pattern.matcher(bid);
            if (matcher.find()) {
                x = Integer.parseInt(matcher.group(0));
                if(x > currentBid+4 && x <= 200)
                    break;
                else
                    Game.alert("Your bid must be between " + (currentBid+5) + " and 180.");
            } else {
                Game.alert("Your input was not a valid number, press cancel if you want to pass!");
            }
        } while (true);
        return x;
    }

    @Override
    public Card.Suit getPreferredColor() {
        return Game.getColor();
    }
}