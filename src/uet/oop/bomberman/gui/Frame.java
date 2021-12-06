package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Swing Frame chứa toàn bộ các component
 */
public class Frame extends JFrame {
	
	public GamePanel _gamepane;
	private JPanel _containerpane;

	private Game _game;

	public Frame() {
		
		_containerpane = new JPanel(new BorderLayout());
		_gamepane = new GamePanel(this);
		_containerpane.add(_gamepane, BorderLayout.PAGE_END);
		
		_game = _gamepane.getGame();
		
		add(_containerpane);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		
		_game.start();
	}


}
