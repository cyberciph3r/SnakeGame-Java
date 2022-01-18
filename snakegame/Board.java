package snakegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Board extends JPanel implements ActionListener{
	int SCREEN_WIDTH = 1300;
	int SCREEN_HEIGHT = 750;
	int UNIT_SIZE = 25;
	int DELAY = 77;
	int GAME_UNITS = (SCREEN_WIDTH/UNIT_SIZE)*(SCREEN_HEIGHT/UNIT_SIZE);
	int x[] = new int[GAME_UNITS];
	int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	Random random = new Random();
	boolean running = false;
	Timer timer;// = new Timer();
	char direction = 'R';
	
	Board(){
		
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		
		setFocusable(true);
		addKeyListener(new KeyRead());
		startGame();
	}
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	public void move() {
		for(int i = bodyParts; i>0 ; i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch(direction) {
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(running) {
			g.setColor(Color.red);
			g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
			
			for(int i = 0 ;  i< bodyParts ; i++) {
				if(i==0) {
					g.setColor(Color.red);
					g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
					
				}
				else {
					g.setColor(Color.green);
					g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
					
				}
				
			}
			g.setColor(Color.red);
			g.setFont( new Font(" ",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());    //g.getFont().getSize() = 40
			
		}
		
		
		else {
			gameOver(g);
			
		}

	}
	public void gameOver(Graphics g) {
		//Score
		g.setColor(Color.red);
		g.setFont( new Font(" ",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	public void checkApple() {
		
		if(x[0]==appleX && y[0]==appleY) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
		
	}
	
	public void checkCollision() {
		for(int i = 1; i<bodyParts;i++) {
			if((x[0]==x[i])&&(y[0]==y[i])) {
				running = false;
			}
		}
		if(x[0]<0) {
			running = false;
		}
		if(x[0]>SCREEN_WIDTH) {
			running = false;
		}
		if(y[0]<0) {
			running = false;
		}
		if(y[0]>SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running)
			timer.stop();
	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollision();
		}
		repaint();
						
	}
	class KeyRead extends KeyAdapter{
		public void keyPressed ( KeyEvent e) {
			int key = e.getKeyCode();
			
			switch(key) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R')
					direction = 'L';
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L')
					direction = 'R';
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D')
					direction = 'U';
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U')
					direction = 'D';
				break;
			
			}
			
		}
		
	}

}
