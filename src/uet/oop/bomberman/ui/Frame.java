package uet.oop.bomberman.ui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
	
	public GamePanel panel;

	public Frame() {

		JPanel container = new JPanel(new BorderLayout());
		panel = new GamePanel(this);
		container.add(panel, BorderLayout.PAGE_END);

		Game game = panel.getGame();
		
		add(container);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		
		game.start();
	}


}
