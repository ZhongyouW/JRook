
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a collection of cards in the possession of a player
 * 
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
public class Hand {
	public enum Orientation {
		NORTH, EAST, SOUTH, WEST
	}

	public HashMap<Card.Suit, ArrayList<Card>> hand;
	public JPanel panel;
	public int size;

	static Dimension screenSizesize = Toolkit.getDefaultToolkit().getScreenSize();
	static final int screenWidth = screenSizesize.width;
	static final int screenHeight = screenSizesize.height;
	static final int height = screenHeight / 5;
	static final int width = screenWidth * 5 / 6;
	static final double ratio = 0.722;
	public static final int cardHeight = height * 3 / 4;
	public static final int cardWidth = (int) (cardHeight * ratio);
	boolean side;
	Orientation orientation;

	/**
	 * Class constructor that initiates the hand.
	 */
	public Hand() {
		panel = new JPanel(new FlowLayout(FlowLayout.CENTER, -cardHeight / 3, (height - cardHeight) / 2));
		panel.setPreferredSize(new Dimension(width, height));
		panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		panel.setBackground(new Color(210, 210, 210));

		hand = new HashMap<>();
		size = 0;
		for (Card.Suit suit : Card.Suit.values()) {
			this.hand.put(suit, new ArrayList<Card>());
		}
		orientation = Orientation.NORTH;
	}

	public void sidePanel() {
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int) (-cardHeight / 3)));
		panel.setPreferredSize(new Dimension(height, Game.game.getHeight()));
		side = true;
	}

	public void addCard(Card card) {
		hand.get(card.suit).add(card);
		if (side)
			panel.add(card.getImageButton(cardHeight, cardWidth, orientation));
		else
			panel.add(card.getImageButton(cardWidth, cardHeight, orientation));
		Game.game.repaint();
		size++;
	}

	public void removeCard(Card card) {
		hand.get(card.suit).remove(card);
		panel.remove(card.button);
		
		panel.repaint();
		
		CenterPanel cp = Game.centerPanel;
		Image img = card.img;
		BufferedImage buffImg = card.img;
		if (orientation == Hand.Orientation.EAST) {
			img = card.rotateImageByDegrees(buffImg, 90);
		} else if (orientation == Hand.Orientation.WEST) {
			img = card.rotateImageByDegrees(buffImg, 270);
		} else if (orientation == Hand.Orientation.SOUTH) {
			img = card.rotateImageByDegrees(buffImg, 180);
		}

		if (side)
			img = img.getScaledInstance(cardHeight, cardWidth, Image.SCALE_SMOOTH);
		else
			img = img.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
		AffineTransform at = new AffineTransform();
		at.translate(cp.getWidth() / 2 - img.getWidth(null) / 2, cp.getHeight() / 2 - img.getHeight(null) / 2);
		cp.img.add(img);
		cp.at.add(at);
		cp.repaint();
		//g2d.drawImage(img, at, null);
		size--;
	}

	/**
	 * @return The hand as a map of arraylist. With each arraylist being all the
	 *         card of the suit, and the suit as the key.
	 */
	public HashMap<Card.Suit, ArrayList<Card>> getHand() {
		return hand;
	}

	/**
	 * @param suit - the desired suit
	 * @return All the cards of the given suit in hand.
	 */
	public ArrayList<Card> getSuit(Card.Suit suit) {
		return hand.get(suit);
	}

	public int getValue() {
		int value = 0;
		for (ArrayList<Card> suit : hand.values()) {
			for (Card card : suit) {
				switch (card.value) {
				case 15:
					value += 15;
					break;
				case 5:
					value += 5;
					break;
				case 10:
				case 14:
					value += 10;
					break;
				case 0:
					value += 20;
					break;
				}
			}
		}
		return value;
	}

	public short getSize() {
		short size = 0;
		for (ArrayList<Card> suit : hand.values()) {
			size += suit.size();
		}
		return size;
	}

	public JPanel getPanel() {
		return panel;
	}

	public String toString() {
		String result = "";
		for (ArrayList<Card> suit : hand.values()) {
			for (Card card : suit) {
				result += card + ", ";
			}
		}
		return result;
	}
}