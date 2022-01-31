import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Game {
	private JPanel top, bottom, left, right, center;
	private Process serverProcess;

	public static void main(String[] args) throws IOException {
		Game g = new Game();
		g.panel();
	}
	
	public void startServer() throws IOException {
		if(serverProcess != null)
			return;
		ProcessBuilder pb = new ProcessBuilder("node", "deez.js");
		pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		pb.redirectError(ProcessBuilder.Redirect.INHERIT);
		serverProcess = pb.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("Program Ended Successfully.");
				if(serverProcess.isAlive())
					serverProcess.destroy();
			}
		}, "Shutdown-thread"));
	}

	public void panel() throws IOException {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

		final int width = size.width;
		final int height = size.height;
		Color casino = new Color(3, 125, 98);
		Color casinoDarker = new Color(133, 54, 14);

		JFrame frame = new JFrame("JRook Host");
		
		frame.setIconImage(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(size);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		top = new JPanel();
		top.setBackground(casinoDarker);
		top.setPreferredSize(new Dimension(width, height / 5));
		top.setLayout(new GridBagLayout());
		
		JLabel title = new JLabel("Welcome to JRook!");
		title.setFont(new Font("Arial", 1, 75));
		title.setForeground(new Color(230, 230, 230));
		top.add(title, new GridBagConstraints());

		bottom = new JPanel();
		bottom.setBackground(casino);
		bottom.setPreferredSize(new Dimension(width, height / 4));

		left = new JPanel();
		left.setPreferredSize(new Dimension(width / 6, height));
		left.setBackground(casino);

		right = new JPanel();
		right.setPreferredSize(new Dimension(width / 6, height));
		right.setBackground(casino);

		center = new JPanel();
		center.setBackground(casino);
		GridLayout g = new GridLayout(2, 1);
		g.setVgap(height / 15);
		center.setLayout(g);
		center.setBorder(new EmptyBorder(100, 200, 100, 200));
		
		JButton host = new JButton("Host a Game");
		host.setBackground(Color.WHITE);
		host.setFocusPainted(false);
		host.setFont(new Font("Arial", 1, 75));
		host.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				try {
					startServer();
					JOptionPane.showMessageDialog(null, "Server has been started!");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton join = new JButton("Join a Game");
		join.setBackground(Color.WHITE);
		join.setFocusPainted(false);
		join.setFont(new Font("Arial", 1, 75));
		
		center.add(host);
		center.add(join);

		frame.add(top, BorderLayout.PAGE_START);
		frame.add(left, BorderLayout.LINE_START);
		frame.add(right, BorderLayout.LINE_END);
		frame.add(center, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.PAGE_END);
	}
}
