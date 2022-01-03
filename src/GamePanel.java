import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
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
	
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
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
		for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
		}
		
		g.setColor(Color.red);
		g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
	}

	public void newFood() {
		// set random position of food in coordinates
		foodX = random.nextInt( (int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		foodY = random.nextInt( (int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}
	
	public void move() {
		
	}
	
	public void checkFood() {
		
	}
	
	public void checkCollision() {
		
	}
	
	public void gameEnd(Graphics g) {
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			
		}
	}

}
