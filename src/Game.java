import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Game extends JFrame {
	public Player[] players;
	public static Game game;
	public Round currentRound;

	public Game(Player[] players) {
		this.players = players;
	}

	public static void main(String[] args) throws IOException {
		if(game == null) {
			//Player have to go first, will fix later if we add multiplayer
			Player[] players = {new Human("Jack"), new PwnPawn(), new PwnPawn(), new PwnPawn()};
			game = new Game(players);
			game.initiate();
			game.startRound();
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
		//Set up Center Table
		//JPanel
		//Set up main player's hand
		this.add(players[0].hand.panel, BorderLayout.SOUTH);
	}

	private void startRound() {
		currentRound = new Round(players);
		while(!currentRound.started)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getBid(int currentBid) {
		return JOptionPane.showInputDialog(null,
				String.format("Input your bid: Min = %d, Max = %d", currentBid + 1, 200));
	}

	public static void alert(String s) {
		JOptionPane.showMessageDialog(null, s);
	}
}