import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Game extends JFrame {
	Player[] players;
	public static Game game;

	public Game(Player[] players) {
		this.players = players;
	}

	public static void main(String[] args) throws IOException {
		if(game == null) {
			Player[] players = {new PwnPawn(), new PwnPawn(), new PwnPawn(), new PwnPawn()};
			game = new Game(players);
			game.startRound();
			game.initiate();
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
		this.setVisible(true);
	}

	private void startRound() {
		this.setLayout(new GridBagLayout());
		Round test = new Round(players);
		while(!test.started)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.gridy = 4;
		constraint.gridwidth = 3;
		System.out.println(players[0].hand);
		this.add(players[0].hand.panel, constraint);
	}
}