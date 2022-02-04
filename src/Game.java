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
		if (game == null) {
			//game.startServer();
			Player[] players = { new Human("Bob"), new PwnPawn()/*, new PwnPawn(), new PwnPawn()*/ };
			game = new Game(players);
			game.initiate();
			game.startRound();
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
	}

	private void startRound() {
		Round test = new Round(players);
		System.out.println(players[0].hand);
		this.setVisible(true);
	}

	public static String getBid(int currentBid) {
		return JOptionPane.showInputDialog(null,
				String.format("Input your bid: Min = %d, Max = %d", currentBid + 1, 200));
	}
	
	public static void alert(String s) {
		JOptionPane.showMessageDialog(null, s);
	}
	/*
	public void startServer() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("call node.exe socket.js");
		pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		pb.redirectError(ProcessBuilder.Redirect.INHERIT);
		Process p = pb.start();
	}*/
}
