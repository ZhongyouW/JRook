import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.io.File;
import java.util.HashMap;

/**
 * Represents the cards used in a card game
 * 
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
public class Card {
	public enum Suit {
		RED, GREEN, BLUE, BLACK,
	}

	HashMap<Suit, Color> suitColor;

	boolean rook, hovered;
	Player owner;
	JButton button;
	Suit suit;
	int value;
	int initialY;
	int finalY;
	BufferedImage img;

	public Card(Suit suit, int value) {
		this.value = value;
		this.suit = suit;
		suitColor = new HashMap<>();
		suitColor.put(suit.RED, new Color(231, 70, 52));
		suitColor.put(suit.GREEN, new Color(92, 171, 52));
		suitColor.put(suit.BLUE, new Color(53, 107, 171));
		suitColor.put(suit.BLACK, new Color(20, 20, 20));
	}

	public Card(Suit suit, int value, Player owner) {
		this(suit, value);
		this.owner = owner;
	}

	public Card(Suit suit, int value, boolean rook) {
		this(suit, value);
		this.rook = rook;
	}

	public BufferedImage recolor(BufferedImage image, Color from, Color to) {
		BufferedImageOp lookup = new LookupOp(new ColorMapper(from, to), null);
		BufferedImage convertedImage = lookup.filter(image, null);
		return convertedImage;
	}

	public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
		double rads = Math.toRadians(angle);
		double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
		int w = img.getWidth();
		int h = img.getHeight();
		int newWidth = (int) Math.floor(w * cos + h * sin);
		int newHeight = (int) Math.floor(h * cos + w * sin);

		BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = rotated.createGraphics();
		AffineTransform at = new AffineTransform();
		at.translate((newWidth - w) / 2, (newHeight - h) / 2);

		int x = w / 2;
		int y = h / 2;

		at.rotate(rads, x, y);
		g2d.setTransform(at);
		g2d.drawImage(img, null, 0, 0);
		g2d.setColor(Color.RED);
		g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
		g2d.dispose();

		return rotated;
	}

	public JButton getImageButton(int width, int height, Hand.Orientation orientation) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		// Get Image file
		try {
			System.out.println(owner.human);
			File file = null;
			File file2 = null;
			if (!owner.human) {
				file = new File("resource\\card\\Back.png");
				file2 = new File("resource\\card\\" + value + ".png");
			} else {
				file = new File("resource\\card\\" + value + ".png");
			}
			if (!file.exists()) {
				System.out.println(value + ".png image can't be found!");
				file = new File("resource\\card\\Crow.png");
			}
			if(file2 != null && file2.exists()) {
				BufferedImage x = ImageIO.read(file2);
				this.img = recolor(x, new Color(218, 62, 40), suitColor.get(suit));
			}
			image = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (orientation == Hand.Orientation.EAST) {
			image = rotateImageByDegrees(image, 90);
		} else if (orientation == Hand.Orientation.WEST) {
			image = rotateImageByDegrees(image, 270);
		} else if (orientation == Hand.Orientation.SOUTH) {
			image = rotateImageByDegrees(image, 180);
		}
		// Recolor and rescale the image
		image = recolor(image, new Color(218, 62, 40), suitColor.get(owner.human ? suit : Suit.RED));
		Image img = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		if(this.img == null)
			this.img = image;
		// Create the button
		JButton button = new JButton(new ImageIcon(img));
		button.setPreferredSize(new Dimension(width, height));
		button.setBackground(new Color(0, 0, 0, 1));
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		initialY = button.getY();
		finalY = (int) (initialY - button.getSize().getHeight() / 4);
		// On Click
		Card instance = this;
		if (owner.human) {
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Trick.player == 0)
						owner.setPlay(instance);
				}
			});
			// On Hover

			button.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseEntered(java.awt.event.MouseEvent evt) {
					hovered = true;
					button.setLocation(button.getX() - button.getWidth() / 3, finalY);
					Game.game.repaint();
				}

				public void mouseExited(java.awt.event.MouseEvent evt) {
					if (hovered) {
						button.setLocation(button.getX() + button.getWidth() / 3,
								(int) (finalY + button.getSize().getHeight() / 6));
						Game.game.repaint();
						hovered = false;
					}
				}
			});
		}
		this.button = button;
		return button;
	}

	public String toString() {
		return suit + " of " + value;
	}
}