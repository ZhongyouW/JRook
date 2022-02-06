import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

public class Game extends JFrame {
	public Player[] players;
	public static Game game;
	public Round currentRound;
	public static JPanel gamePanel, sidePanel, leftHand, rightHand, topHand, bottomHand;
	static CenterPanel centerPanel;

	public Game(Player[] players) {
		this.players = players;
	}

	public static void main(String[] args) throws IOException {
		if (game == null) {
			// Player have to go first, will fix later if we add multiplayer
			Player[] players = { new Human("Jack"), new PwnPawn(), new PwnPawn(), new PwnPawn() };
			game = new Game(players);
			game.initiate();
			game.startRound();
			game.setVisible(true);
			game.currentRound.start();
		} else {
			throw new IllegalStateException("Only one game instance can exist at a time!");
		}
	}

	public void initiate() throws IOException {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = size.width;
		final int height = size.height;

		Color background = new Color(231, 231, 231);
		Color zone = new Color(219, 220, 221);

		this.setBackground(background);
		this.setIconImage(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(size);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setLayout(new BorderLayout());
		this.setVisible(true);
		// Set up Center Table
		// JPanel
		// Set up main player's hand

		// Left side of JFrame
		gamePanel = new JPanel();
		gamePanel.setBackground(Color.RED);
		gamePanel.setLayout(new BorderLayout());

		bottomHand = players[0].hand.panel;
		gamePanel.add(players[0].hand.panel, BorderLayout.SOUTH);

		// CPU #1 Left Panel
		leftHand = players[1].hand.panel;
		gamePanel.add(leftHand, BorderLayout.WEST);

		// CPU #2 Top Panel
		topHand = players[2].hand.panel;
		gamePanel.add(topHand, BorderLayout.NORTH);

		// CPU #3 Right Panel
		rightHand = players[3].hand.panel;
		gamePanel.add(rightHand, BorderLayout.EAST);

		// Center
		centerPanel = new CenterPanel();
		// centerPanel.setLayout(new FlowLayout());
		// centerPanel.add(new JButton("deez"));
		//centerPanel.setBackground(Color.GREEN);
		gamePanel.add(centerPanel, BorderLayout.CENTER);

		// Right side of JFrame
		sidePanel = new JPanel();
		sidePanel.setLayout(new BorderLayout());
		sidePanel.add(new JButton("stuff"), BorderLayout.SOUTH);
		this.add(sidePanel, BorderLayout.CENTER);

		this.add(gamePanel, BorderLayout.WEST);
	}

	private void startRound() {
		currentRound = new Round(players);
		while (!currentRound.started) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getBid(int currentBid) {
		return JOptionPane.showInputDialog(null,
				String.format("Input your bid: Min = %d, Max = %d", currentBid + 5, 200));
	}

	public static Card.Suit getColor() {
		Card.Suit[] options = Card.suits;
		return (Card.Suit)JOptionPane.showInputDialog(null,
				"Select the trump color",
				"Trump Color Selection",
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				Card.Suit.RED);
	}

	public static void alert(String s) {
		JOptionPane.showMessageDialog(null, s);
	}
}

class CenterPanel extends JPanel {
	public AffineTransform[] at = new AffineTransform[4];
	public Image[] img = new Image[4];

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// TODO Auto-generated method stub
		for (int i = 0; i < at.length; i++) {
			//System.out.print("SUS1");
			//if (img[i] != null && at[i] != null) {
				//System.out.print("SUS2");
				g2.drawImage(img[i], at[i], null);
			//}
		}
	}
	
	public void reset() {
		at = new AffineTransform[4];
		img = new Image[4];
	}
}