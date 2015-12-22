package QWithoutA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Entities.PlayerProjectile;
import Entities.SlugProjectile;


@SuppressWarnings("serial")
public class SaltGamePanel  extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {
	/**
     * The width and height of the Jpanel
     */
	static int width = 1100;
	static int height = 550;
	
	//an arraylist of blocks
	ArrayList<Blocks> block = new ArrayList<Blocks>();
	//an arraylist of the ground (can also make hills)
	ArrayList<Ground> ground = new ArrayList<Ground>();
	//an arraylist of the item blocks
	ArrayList<ItemBlock> iBlock = new ArrayList<ItemBlock>();
	
	/**
     * ArrayLists of player projectiles and ranged enemy projectiles
     */
    static ArrayList<PlayerProjectile> saltBalls = new ArrayList<PlayerProjectile>();
    static ArrayList<SlugProjectile> slimeBalls = new ArrayList<SlugProjectile>();
    
	/**
	 * The pause between repainting (should be set for about 30 frames per
	 * second).
	 */
	final int pauseDuration = 50;
	
	/**
	 * Radiuses of each player projectile 
	 */
    int playerProjectileRadius;

	private int signX = -1;

	private int signY = 1;

	private int playerX;

	private int playerY;
	/**
	 * Which key was pressed last
	 */
	char key = ' ';
	
	public boolean isPlayerProjectileSpawned = false;
	private final double playerProjectileMaxHeight = 20;
	private final double playerProjectileAcceleration = 9.8;
	
	public double initialVelocity = Math.sqrt(-2*playerProjectileAcceleration *playerProjectileMaxHeight);

	public static void main(String[] args) {

		// Set up main window (using Swing's Jframe)
		JFrame frame = new JFrame("Salt Man Adventures");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1100, 550));
		frame.setAutoRequestFocus(false);
		JOptionPane.showMessageDialog(frame, "To win this game, make your way past enemies and holes to get the item at the end." + "\n" 
				+ "If you happen to die, you will have to restart your journey.", 
				"Welcome", JOptionPane.INFORMATION_MESSAGE);
		frame.setVisible(true);
		frame.setLocation(100, 50);
		Container c = frame.getContentPane();
		c.add(new SaltGamePanel());
		frame.pack();
		frame.setTitle("The Adventures of Salt Man");
		frame.setResizable(false);
	}

	public SaltGamePanel(){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.CYAN);
		//this.setFocusable(true);
		
		setFocusable(true);
		addKeyListener(this);
		addMouseListener (this);
		addMouseMotionListener(this);
		
		// adds ground arraylist
		ground.add(new Ground(0, 525, 0, width, 0, height));
		// adds item block arraylist
		iBlock.add(new ItemBlock(250, 300, 0, width, 0, height));
		// adds regular platform blocks
		block.add(new Blocks(150, 300, 0, width, 0, height));
		block.add(new Blocks(350, 300, 0, width, 0, height));
		//begins game
		Thread gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	public void run() {
		while (true) {
			repaint();
			try {
				Thread.sleep(pauseDuration);
				if(saltBalls.size() > 0){
					if(isPlayerProjectileSpawned){
						if(Character.toString(key).equalsIgnoreCase("a")){
							signX = -1;
						}
						else if(Character.toString(key).equalsIgnoreCase("d")){
							signX = 1;
						}
						saltBalls.get(saltBalls.size()-1).setColor(new Color((int) (16), (int)  (16), (int) (16)));
						saltBalls.get(saltBalls.size()-1).setXSpeed(initialVelocity * signX);
						if(saltBalls.get(saltBalls.size()-1).getYspeed() <= height - saltBalls.get(saltBalls.size()-1).getYspeed())
							saltBalls.get(saltBalls.size()-1).setYSpeed((saltBalls.get(saltBalls.size()-1).getYspeed() + saltBalls.get(saltBalls.size()-1).getProjectileAcceleration()));

						//					if((saltBalls.get(i).getY() <= height) || (saltBalls.get(i).getY() >= 0)){
						//						saltBalls.get(i).setYSpeed(speedCap * -1);
						//					}
					}
				}
				deletePlayerProjectile();
				System.out.println(saltBalls.size());
			} catch (InterruptedException e) {
				
			}catch(ArrayIndexOutOfBoundsException e){
				
			}
			
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// paints initial ground of main menu/first screen
		for (int i = 0; i < ground.size(); i++) {
			g.setColor(Color.GREEN);   
			ground.get(i).draw(g);
		}
		// paints test platform blocks on main menu/first screen
		for (int i = 0; i < block.size(); i++) {
			g.setColor(Color.BLACK);   
			block.get(i).draw(g);
		}
		// paints test item blocks on main menu/first screen
		for (int i = 0; i < iBlock.size(); i++) {  
			g.setColor(Color.MAGENTA);  
			iBlock.get(i).draw(g);
		}
		//Draws the player projectiles
		g.drawString ("number of salt balls: " + saltBalls.size(), 5, 20);
		g.drawString ("Current Key: " + key, 5, 40);
		for (int i = 0; i < saltBalls.size(); i++) {
			saltBalls.get(i).draw(g);
		}
	}

	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		key = e.getKeyChar();
		//limits the number of player projectiles that can spawn
		if(saltBalls.size() < 2){
			saltBalls.add(new PlayerProjectile(playerX, playerY, 0, width, 0, height));
			isPlayerProjectileSpawned = true;
		}
		else{
			isPlayerProjectileSpawned = false;
		}
		
		
		
		
		/*
		if(e.getKeyCode() == 38){
			paddle[1].setY((int) (paddle[1].getY()-20));
		}
		else if(e.getKeyCode() == 40){
			paddle[1].setY((int) (paddle[1].getY()+20));
		}	
	*/
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		key = e.getKeyChar();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		playerX = e.getX ();
		playerY = e.getY ();
		repaint ();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
//		if(saltBalls.size() < 2)
		playerX = e.getX ();
		playerY = e.getY ();
//			saltBalls.add(new PlayerProjectile(playerX, playerY, 0, width, 0, height));
//			for(int i = 0; i < saltBalls.size(); i++){
//				saltBalls.get(i).setXSpeed(initialVelocity * signX);
//				saltBalls.get(i).setYSpeed((saltBalls.get(i).getYspeed() + saltBalls.get(i).getProjectileAcceleration()));
//				saltBalls.get(i).setColor(new Color((int) (0), (int)  (0), (int) (0)));
//			}

	}
	/**
	 * Deletes the player projectile after a set amount of time 
	 */
	public static void deletePlayerProjectile(){
		if(saltBalls.size() > 0){
			for(int i = 0; i < saltBalls.size(); i++){
				if(saltBalls.get(i).isDecayed()){
					saltBalls.remove(i);
				}
			}
		}
	}
//saltBalls.get(i).getX() > width || saltBalls.get(i).getX() < 0 || 
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		playerX = e.getX ();
		playerY = e.getY ();
	}
	
	public void setKey(char x) {
		key = x;
	}
	
	public char getKey() {
		return key;
	}
}