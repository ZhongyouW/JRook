import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

public class Game {
	private JPanel top, bottom, left, right, center;

	public static void main(String[] args) throws IOException {
		new Game().panel();
	}

	public void panel() throws IOException {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

		final int width = size.width;
		final int height = size.height;
		Color casino = new Color(3, 125, 98);
		Color casinoDarker = new Color(133, 54, 14);

		JFrame frame = new JFrame("BorderLayoutDemo");
		
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.jpg"));
		frame.setIconImage(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		top = new JPanel();
		top.setBackground(casinoDarker);
		top.setPreferredSize(new Dimension(width, height / 5));

		bottom = new JPanel();
		bottom.setBackground(casinoDarker);
		bottom.setPreferredSize(new Dimension(width, height / 4));

		left = new JPanel();
		left.setPreferredSize(new Dimension(width / 6, height));
		left.setBackground(casinoDarker);

		right = new JPanel();
		right.setPreferredSize(new Dimension(width / 6, height));
		right.setBackground(casinoDarker);

		center = new JPanel();
		center.setBackground(casino);

		frame.add(top, BorderLayout.PAGE_START);
		frame.add(left, BorderLayout.LINE_START);
		frame.add(right, BorderLayout.LINE_END);
		frame.add(center, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.PAGE_END);
	}
}
