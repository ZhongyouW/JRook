import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Game extends JFrame {
	public Player[] players;
	public static Game game;
	public Round currentRound;
	public static JPanel gamePanel, sidePanel, leftHand, rightHand, topHand, bottomHand;
	static CenterPanel centerPanel;
	public Team[] teams;
	static JLabel trump, wins;
	static Commentary plays;

	public Game(Player[] players) {
		this.players = players;
		teams = new Team[] { new Team(), new Team() };
		teams[0].add(players[0]);
		teams[0].add(players[2]);
		teams[1].add(players[1]);
		teams[1].add(players[3]);
	}

	public static void main(String[] args) throws IOException {
		if (game == null) {
			// Player have to go first, will fix later if we add multiplayer
			String name = JOptionPane.showInputDialog("What is your name?");
			Player[] players = { new Human(name), new PwnPawn(), new PwnPawn(), new PwnPawn() };

			game = new Game(players);

			game.initiate();



			while ((game.getWinner() == null)) {
				game.startRound();
				game.setVisible(true);
				game.currentRound.start();
			}
			JOptionPane.showMessageDialog(null, game.getWinner() + " won the game!");
		} else {
			throw new IllegalStateException("Only one game instance can exist at a time!");
		}
	}

	public Team getWinner() {
		if((teams[0].points > teams[1].points) && teams[0].points >= 500) {
			return teams[0];
		} else if((teams[1].points > teams[0].points) && teams[1].points >= 500) {
			return teams[1];
		}
		return null;
	}

	public void initiate() throws IOException {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = size.width;
		final int height = size.height;

		Color background = new Color(231, 231, 231);

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
		// centerPanel.setBackground(Color.GREEN);
		gamePanel.add(centerPanel, BorderLayout.CENTER);

		// Right side of JFrame
		sidePanel = new JPanel();
		sidePanel.setBackground(Color.WHITE);
		sidePanel.setLayout(new BorderLayout());
		sidePanel.add(new JButton("stuff"), BorderLayout.SOUTH);
		this.add(sidePanel, BorderLayout.CENTER);

		this.add(gamePanel, BorderLayout.WEST);

		trump = new JLabel("TRUMP", SwingConstants.CENTER);
		trump.setForeground(Color.white);
		trump.setFont(new Font("Arial", Font.BOLD, width / 50));
		trump.setPreferredSize(new Dimension(width, height / 8));
		sidePanel.add(trump, BorderLayout.NORTH);

		plays = new Commentary(width);
		plays.setPreferredSize(new Dimension(width, height * 4 / 8));
		sidePanel.add(plays, BorderLayout.CENTER);

		wins = new JLabel("<html><body>&emsp;Team 1: 0<br>&emsp;Team 2: 0<br/><br/></body></html>",
				SwingConstants.LEFT);
		wins.setForeground(Color.black);
		wins.setPreferredSize(new Dimension(width, height*3 / 8));
		wins.setFont(new Font("Arial", Font.BOLD, width / 90));
		sidePanel.add(wins, BorderLayout.SOUTH);
		//Counter Panel
		JPanel counterPanel = new JPanel();
		sidePanel.setBackground(new Color(220,220,220));
		counterPanel.setLayout(new BorderLayout());
		CounterPanel team1Panel = new CounterPanel(teams[0]);
		CounterPanel team2Panel = new CounterPanel(teams[1]);
		counterPanel.add(team1Panel, BorderLayout.WEST);
		counterPanel.add(team2Panel, BorderLayout.EAST);
		sidePanel.add(counterPanel, BorderLayout.SOUTH);
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
				String.format("Your turn to bid! \nCurrent bid: %d \nMaximum bid = %d", currentBid, 200));
	}

	public static Card.Suit getColor() {
		Card.Suit[] options = Card.suits;
		return (Card.Suit) JOptionPane.showInputDialog(null, "Select the trump color", "Trump Color Selection",
				JOptionPane.QUESTION_MESSAGE, null, options, Card.Suit.RED);
	}

	public static void alert(String s) {
		JOptionPane.showMessageDialog(null, s);
	}
}

class CenterPanel extends JPanel {
	public ArrayList<AffineTransform> at = new ArrayList<>();
	public ArrayList<Image> img = new ArrayList<>();

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// TODO Auto-generated method stub
		for (int i = 0; i < at.size(); i++) {
			g2.drawImage(img.get(i), at.get(i), null);
		}
	}

	public void reset() {
		at = new ArrayList<>();
		img = new ArrayList<>();
		repaint();
	}
}

class Commentary extends JPanel {
	int width;
	ArrayList<JLabel> list = new ArrayList<>();

	public Commentary(int width) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentY(BOTTOM_ALIGNMENT);
		this.width = width;
		this.setBackground(new Color(200, 200, 200));
	}

	@Override
	public void paintComponent(Graphics g) {
		this.removeAll();
		for(JLabel x:list) {
			this.add(x);
		}
		super.paintComponent(g);
	}

	public void add(String s) {
		if(list.size() > 16) {
			list.remove(0);
		}
		s = "  " + s;
		JLabel t = new JLabel(s, SwingConstants.LEFT);
		t.setFont(new Font("Arial", Font.BOLD, width / 110));
		list.add(t);
		this.repaint();
		this.revalidate();
	}
}

class CounterPanel extends JPanel {
	private Image bgImage;
	JLabel counter, points;
	private Team team;

	public CounterPanel(Team team) {
		super();
		try {
			BufferedImage image = ImageIO.read(new File("resource\\card\\Back.png"));
			bgImage = image.getScaledInstance(Hand.cardWidth, Hand.cardHeight, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setPreferredSize(new Dimension(Hand.cardWidth, Hand.cardHeight));
		this.setLayout(new BorderLayout());
		this.team = team;

		String format = "<html>%s<br/>%s</html>";
		JLabel label = new JLabel(String.format(format,team.team.get(0).name,team.team.get(1).name), SwingConstants.CENTER);
		this.add(label, BorderLayout.NORTH);

		counter = new JLabel("",SwingConstants.CENTER);
		counter.setFont(new Font("Serif", Font.PLAIN, 36));
		this.add(counter, BorderLayout.CENTER);

		points = new JLabel();
		this.add(points, BorderLayout.SOUTH);
	}
	@Override
	protected void paintComponent(Graphics g) {
		counter.setText(""+team.taken.size);
		points.setText(" points: " + team.points);
		super.paintComponent(g);
		g.drawImage(bgImage, 0, 0, null);
	}
}