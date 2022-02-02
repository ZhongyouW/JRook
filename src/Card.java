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

    public JButton getImageButton(int width, int height) {
        Image image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR).getScaledInstance(width, height,  Image.SCALE_SMOOTH);;
        try {
            File file = new File("resource\\card\\"+value + ".png");
            if(!file.exists())  {
                System.out.println(value + ".png image can't be found!");
                file = new File("resource\\card\\BlankCard.png");
            }
            image = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JButton button = new JButton(new ImageIcon(image));
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

    public String toString() {
        return suit + " " + value;
    }
}
