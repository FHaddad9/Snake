import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener  {
	// screen dimensions
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	
	// object size
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT) / UNIT_SIZE;
	
	// game speed
	static final int DELAY = 75;
	
	// co-ordinates
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	
	// details about snake
	int bodyParts = 6;
	int foodsEaten;
	int foodX;
	int foodY;
	
	char start = 'N';
	char direction = 'R';
	boolean running = false;
	boolean begin = false;
	Timer timer;
	Random random;	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.white);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newFood();
		running = true;
		
		// start timer
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(running) {
			/*
			for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			*/
			
			// draw food
			g.setColor(Color.red);
			g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
			
			for(int i = 0; i < bodyParts; i++) {
				if(i == 0) {
					g.setColor(Color.green);
					g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(45, 180, 0));
					g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + foodsEaten, ((SCREEN_WIDTH - metrics.stringWidth("Game Over" + foodsEaten)) / 2), g.getFont().getSize());

		} else {
			gameEnd(g);
		}
	}

	public void newFood() {
		// set random position of food in coordinates
		foodX = random.nextInt( (int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		foodY = random.nextInt( (int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}
	
	public void move() {
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		// gets direction based on coordiantes: U is up, D is down, etc.
		switch(direction) {
			case 'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
			case 'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
			case 'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
		}
		
		switch(start) {
		case 'S':
			begin = true;
			break;
		}
	}
	
	public void checkFood() {
		if((x[0] == foodX) && (y[0] == foodY)) {
			bodyParts++;
			foodsEaten++;
			newFood();
		}
	}
	
	public void checkCollisions() {
		// check if body is hit with head
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		
		// check if head touches left border
		if(x[0] < 0) {
			running = false;
		}
		
		// check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		
		// check if head touches top border
		if(y[0] < 0) {
			running = false;
		}
		
		// check if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		// stop timer when snake touches border or body
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameStart(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Snake", ((SCREEN_WIDTH - metrics.stringWidth("Snake")) / 2), (SCREEN_HEIGHT / 4));
		
		g.setColor(Color.black);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metricPlay = getFontMetrics(g.getFont());
		g.drawString("Play Now", ((SCREEN_WIDTH - metricPlay.stringWidth("Play Now")) / 2), (SCREEN_HEIGHT / 2));
	}
	
	public void gameEnd(Graphics g) {
		// end of game text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", ((SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2), (SCREEN_HEIGHT / 4));

		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metricsScore = getFontMetrics(g.getFont());
		g.drawString("Score: " + foodsEaten, ((SCREEN_WIDTH - metricsScore.stringWidth("Score: " + foodsEaten)) / 2), (SCREEN_HEIGHT / 2));

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkFood();
			checkCollisions();
		}
		
		repaint();		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			// input movements of snake
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if(direction != 'R') {
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(direction != 'L') {
						direction = 'R';
					}
					break;
				case KeyEvent.VK_UP:
					if(direction != 'D') {
						direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN:
					if(direction != 'U') {
						direction = 'D';
					}
					break;
				case KeyEvent.VK_S:
					start = 'S';
					break;
			}
		}
	}

}
