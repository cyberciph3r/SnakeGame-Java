package snakegame;

import javax.swing.JFrame;

public class Snake extends JFrame {
	Snake(){
		
		add(new Board());
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Snake Game");		
	}

	public static void main(String[] args) {
		new Snake().setVisible(true);
	}

}
