import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/** Represents the cards used in a card game
 * @author Zhongyou Wu
 * @author Cromlechs#5019
 */
public class Card {
    public enum Suit {
        RED,
        GREEN,
        BLUE,
        BLACK,
    }

    boolean rook;
    Player owner;
    Suit suit;
    int value;

    public Card(Suit suit, int value) {
        this.value = value;
        this.suit = suit;
    }

    public Card(Suit suit, int value, Player owner) {
        this.value = value;
        this.suit = suit;
        this.owner = owner;
    }

    public Card(Suit suit, int value, boolean rook) {
        this(suit, value);
        this.rook = rook;
    }

    public JLabel getImage(Graphics g) {
        BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_3BYTE_BGR);
        try {
            image = ImageIO.read(new File("resource\\card\\"+value + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JLabel(new ImageIcon(image));
    }

    public String toString() {
        return suit + " " + value;
    }
}
