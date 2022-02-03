import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.io.File;
import java.util.HashMap;

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
    HashMap<Suit, Color> suitColor;

    boolean rook;
    Player owner;
    Suit suit;
    int value;

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

    public JButton getImageButton(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        //Get Image file
        try {
            File file = new File("resource\\card\\"+value + ".png");
            if(!file.exists())  {
                System.out.println(value + ".png image can't be found!");
                file = new File("resource\\card\\Crow.png");
            }
            image = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Recolor and rescale the image
        Image img = recolor(image, new Color(218, 62, 40), suitColor.get(suit)).getScaledInstance(width, height,  Image.SCALE_SMOOTH);
        //Create the button
        JButton button = new JButton(new ImageIcon(img));
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(new Color(0,0,0,1));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        //On Click
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(suit + " of " + value + " is played.");
            }
        });
        //On Hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                System.out.println("Hovering over " + suit + " of " + value);
                button.setSize(new Dimension(width*2, height));
                Game.game.repaint();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setSize(new Dimension(width, height));
                Game.game.repaint();
            }
        });
        return button;
    }

    public String toString() {
        return suit + " of " + value;
    }
}
