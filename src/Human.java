import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Human extends Player {
	Scanner kb = new Scanner(System.in);
	final String regex = "^\\d+";
	final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

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
		int x = -1;
		do {
			String bid = Game.getBid(currentBid);
			if(bid == null)
				break;
			Matcher matcher = pattern.matcher(bid);
			if (matcher.find()) {
				x = Integer.parseInt(matcher.group(0));
				if(x > currentBid && x <= 200)
					break;
				else
					Game.alert("Your bid must be between " + (currentBid+1) + " and 200.");
			} else {
				Game.alert("Your input was not a valid number, press cancel if you want to pass!");
			}
		} while (true);
		return x;
	}

	@Override
	public Card.Suit getPreferredColor() {
		return null;
	}
}
