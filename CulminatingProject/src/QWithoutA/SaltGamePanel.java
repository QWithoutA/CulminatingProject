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

import Entities.Player;
import Entities.PlayerProjectile;
import Entities.SlugProjectile;

/**
 * 
 * @author David
 *
 */
@SuppressWarnings("serial")
public class SaltGamePanel  extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {
	/**
     * The width and height of the Jpanel
     */
	static int width = 1100;
	static int height = 550;
	
	//an arraylist of the blocks
	ArrayList<Blocks> block = new ArrayList<Blocks>();
	//an arraylist of the ground (can also make hills)
	ArrayList<Ground> ground = new ArrayList<Ground>();
	//an arraylist of the item blocks
	ArrayList<ItemBlock> iBlock = new ArrayList<ItemBlock>();
	//an arraylist of movingplatforms 
	ArrayList<Platform> mPlat = new ArrayList<Platform>();
	//an array of falling blocks
	ArrayList<FallingBlock> fBlock = new ArrayList<FallingBlock>();


	
	/**
     * ArrayLists of player projectiles and ranged enemy projectiles
     */
    static ArrayList<PlayerProjectile> saltBalls = new ArrayList<PlayerProjectile>();
    static ArrayList<SlugProjectile> slimeBalls = new ArrayList<SlugProjectile>();
    
    Player[] player = new Player[1];
    
	/**
	 * The pause between repainting (should be set for about 30 frames per
	 * second).
	 */
	final int pauseDuration = 50;
	
	/**
	 * Radiuses of each player projectile 
	 */
    int playerProjectileRadius;
    
    private int speedCap = 10;

	private int signX = -1;

	private int signY = 1;

	private int playerX;

	private int playerY;
	
	private int playerProjectileDirection = 1;
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
		frame.setLocation(100, 100);
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
		//ads a platform that moves on the x axis
		mPlat.add(new Platform(200, 250, 0, width, 0, height));
		mPlat.get(0).setXSpeed(14-7);
		//adds a platform that moves on the y axis
		mPlat.add(new Platform(800, 250, 0, width, 0, height));
		mPlat.get(1).setYSpeed(14-10);
		//adss a block that falls 
		fBlock.add(new FallingBlock(500, 250, 0, width, 0, height));
		player[0] = new Player(30, 400, 0, width, 0, height);
		
		//begins game
		Thread gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	public void run() {
		while (true) {
			repaint();
			checkCollision();
			if(checkCollision()){
				mPlat.get(0).setXSpeed(mPlat.get(0).getXspeed() *-1);
				mPlat.get(1).setYSpeed(mPlat.get(1).getYspeed() *-1);
			}

			try {
				Thread.sleep(pauseDuration);

				if(Character.toString(key).equalsIgnoreCase("a")){
					player[0].setXSpeed(-speedCap);
				}
				else if(Character.toString(key).equalsIgnoreCase("d")){
					player[0].setXSpeed(speedCap);
				}
				
				if(Character.toString(key).equalsIgnoreCase("w")){
					player[0].setYSpeed(-speedCap);
				}
				else if(Character.toString(key).equalsIgnoreCase("s")){
					player[0].setHeight(player[0].initialHeight/2);
				}
				else{
					player[0].setHeight(player[0].initialHeight);
				}
				
				player[0].setYSpeed((player[0].getYspeed() +  1.98)/ 1.0198);
				
				if(player[0].getYspeed() < 0){
					player[0].setYSpeed(0);
				}
				if(player[0].getY() > height){
					player[0].setY((int) (player[0].getY() - height/2));
				}
				if(playerProjectileDirection == -1){
					signX = -1;
				}
				else if(playerProjectileDirection == 1){
					signX = 1;
				}
				if(saltBalls.size() > 0){
						if(isPlayerProjectileSpawned){
							saltBalls.get(saltBalls.size()-1).setColor(new Color((int) (16), (int)  (16), (int) (16)));
							saltBalls.get(saltBalls.size()-1).setXSpeed(speedCap * signX);
							saltBalls.get(saltBalls.size()-1).setYSpeed(speedCap);
							isPlayerProjectileSpawned = false;
						}
						for(int i = 0; i<saltBalls.size(); i++){
							if(saltBalls.get(i).getBouncing() && saltBalls.get(i).getYspeed() == 0){
								saltBalls.get(i).setYSpeed(saltBalls.get(i).getYspeed());
							}
							else{
								saltBalls.get(i).setYSpeed((saltBalls.get(i).getYspeed() + saltBalls.get(i).getProjectileAcceleration())
										/ saltBalls.get(i).getGravityConstant());
							}
							if(saltBalls.get(i).getY() > height){
								saltBalls.get(i).setY((int) saltBalls.get(i).getY() - 10);
							}
							if(saltBalls.get(i).getYspeed() > -1 && saltBalls.get(i).getYspeed() < 1){
								saltBalls.get(i).setHasBounced(true);
							}
						}
//					if((saltBalls.get(i).getY() <= height) || (saltBalls.get(i).getY() >= 0)){
//						saltBalls.get(i).setYSpeed(speedCap * -1);
//					}
				}
				if(slimeBalls.size() > 0){
					for(int i = 0; i < slimeBalls.size(); i++){
						slimeBalls.get(i).setXSpeed(speedCap * signX);
						slimeBalls.get(i).setYSpeed(0);
						System.out.println(speedCap * signX);
					}
				}

				deletePlayerProjectile();
				deleteSlugProjectile();
				
//				for(int i = 0; i<slimeBalls.size(); i++){
//					if(Math.sqrt(Math.pow(circle[j].getX()-circle[i].getX(),2) + Math.pow(circle[j].getY()-circle[i].getY(),2)) <= circle[i].getRadius() + slimeBalls.get(i).getRadius() + ){
//
//					}
//				}
			} catch (InterruptedException e) {
				
			} catch(ArrayIndexOutOfBoundsException e) {
				
			}
			
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString ("Number of salt balls: " + saltBalls.size(), 5, 20);
		g.drawString ("Current Key: " + key, 5, 40);
		g.drawString ("Current Direction: " + playerProjectileDirection, 5, 60);
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
		
		for (int i = 0; i < mPlat.size(); i++) {  
			g.setColor(Color.BLACK);  
			mPlat.get(i).draw(g);
			  }
		for (int i = 0; i < fBlock.size(); i++) {  
			g.setColor(Color.RED);  
			fBlock.get(i).draw(g);
			  }
		//Draws the player's projectiles
		
		for (int i = 0; i < saltBalls.size(); i++) {
			saltBalls.get(i).draw(g);
		}
		player[0].draw(g);
		for (int i = 0; i < slimeBalls.size(); i++) {
			slimeBalls.get(i).draw(g);
		}
	}
	
	public boolean checkCollision(){
		if(mPlat.get(0).getX() + mPlat.get(0).getWidth() > Math.abs(500) || mPlat.get(0).getX() < Math.abs(100)){
			return true;
			}
		else 
			return false;
		
		/*
		 	if( if player comes in contact with block then block falls.){
			return true;
		}
		else 
			return false;
			*/
		
		}


	/**
	 * sets the x direction the player's projectile is traveling towards
	 */
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		key = e.getKeyChar();
		//limits the number of player projectiles that can spawn
		if(saltBalls.size() < 10){
			if(Character.toString(key).equalsIgnoreCase("a")){
				playerProjectileDirection = -1;
			}
			else if(Character.toString(key).equalsIgnoreCase("d")){
				playerProjectileDirection = 1;
			}
			else if(Character.toString(key).equalsIgnoreCase(" ")){
				if(playerProjectileDirection == 1){
					saltBalls.add(new PlayerProjectile(player[0].getX() + player[0].getWidth(), player[0].getY() + player[0].getHeight()/2, 0, width, 0, height));
				}
				else if(playerProjectileDirection == -1){
					saltBalls.add(new PlayerProjectile(player[0].getX(), player[0].getY() + player[0].getHeight()/2, 0, width, 0, height));
				}
				isPlayerProjectileSpawned = true;
			}
			//shoots slug projectiles
			else if(Character.toString(key).equalsIgnoreCase("e")){
				SlugProjectile.setDirection(playerProjectileDirection);
				slimeBalls.add(new SlugProjectile(playerX, playerY, 0, width, 0, height));
					
			}
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
		key = KeyEvent.CHAR_UNDEFINED;
		player[0].setXSpeed(0);
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 */	
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
	
	/**
	 * Deletes the player projectile after a set amount of bounces 
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
	/**
	 * Deletes the enemy projectile after a set amount of time 
	 */
	public static void deleteSlugProjectile(){
		if(slimeBalls.size() > 0){
			for(int i = 0; i < slimeBalls.size(); i++){
				if(slimeBalls.get(i).isDecayed()){
					slimeBalls.remove(i);
				}
			}
		}
	}
	
	public void setKey(char x) {
		key = x;
	}
	
	public char getKey() {
		return key;
	}
}