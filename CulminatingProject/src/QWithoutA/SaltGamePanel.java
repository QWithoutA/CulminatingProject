package QWithoutA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.awt.Rectangle;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Entities.Player;
import Entities.PlayerProjectile;
import Entities.RoamingEnemy;
import Entities.Slug;
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
	 * ArrayLists of player projectiles
	 */
	static ArrayList<PlayerProjectile> saltBalls = new ArrayList<PlayerProjectile>();
	/**
	 * ArrayLists of ranged-enemy projectiles
	 */
	static ArrayList<SlugProjectile> slimeBalls = new ArrayList<SlugProjectile>();
	/**
	 * Player entity
	 */
	public static Player[] player = new Player[1];
	/**
	 * Ranged-enemies commonly known as 'slugs'
	 */
	static ArrayList<RoamingEnemy> walkers = new ArrayList<RoamingEnemy>();
	/**
	 * Ranged-enemies commonly known as 'slugs'
	 */
	static ArrayList<Slug> slugs = new ArrayList<Slug>();

	/**
	 * The pause between repainting (should be set for about 30 frames per second).
	 */
	final int pauseDuration = 50;

	/**
	 * Radiuses of each player projectile 
	 */
	private int playerProjectileRadius = PlayerProjectile.getRadius();
	/**
	 * Speed of each projectile 
	 */
	private int speedCap = 10;
	/**
	 * Movement speed of each slug 
	 */
	private final int slugSpeed = 6;
	/**
	 * Movement speed of each walker 
	 */
	private final int walkerSpeed = 3;
	/**
	 * Variable value for inversing the horizontal speed 
	 */
	private int signX = 1;
	/**
	 * Variable value for inversing the vertical speed 
	 */
	private int signY = 1;
	/**
	 * X co-ordinate of the player entity 
	 */
	private int playerX;
	/**
	 * Y co-ordinate of the player entity 
	 */
	private int playerY;
	/**
	 * Variable value for inversing the player projectile speed 
	 */
	private int playerProjectileDirection = 1;
	/**
	 * Variable value for inversing the slug projectile speed 
	 */
	private int slugDirection = 1;
	/**
	 * Which key was pressed last
	 */
	char key = ' ';

	private int mouseX;
	/**
	 * X co-ordinate of the mouse 
	 */
	private int mouseY;
	/**
	 * Y co-ordinate of the mouse 
	 */

	public boolean checkState;

	public boolean isPlayerProjectileSpawned = false;
	public boolean isSlugProjectileSpawned = false;

	private final double playerProjectileMaxHeight = 20;
	private final double playerProjectileAcceleration = 9.8;

	public double initialVelocity = Math.sqrt(-2*playerProjectileAcceleration *playerProjectileMaxHeight);

	private MainMenu mainMenu;

	public enum STATE{
		MENU,
		GAME
	};

	public static STATE State = STATE.MENU;


	public static void main(String[] args) {

		// Set up main window (using Swing's Jframe)
		JFrame frame = new JFrame("Salt Man Adventures");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1100, 550));
		frame.setAutoRequestFocus(false);

		//		JOptionPane.showMessageDialog(frame, "To win this game, make your way past enemies and holes to get the item at the end." + "\n" 
		//			+ "If you happen to die, you will have to restart your journey.", "Welcome", JOptionPane.INFORMATION_MESSAGE);

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
		State = STATE.MENU;
		setFocusable(true);
		addKeyListener(this);
		addMouseListener (this);
		addMouseMotionListener(this);

		this.addMouseListener(new MouseInput());

		//adds main menu
		mainMenu = new MainMenu();

		// adds ground arraylist
		ground.add(new Ground(0, 525, 0, width, 0, height));

		// adds item block arraylist
		iBlock.add(new ItemBlock(250, 350, 0, width, 0, height));
		// adds regular platform blocks
		block.add(new Blocks(150, 350, 0, width, 0, height));
		block.add(new Blocks(350, 350, 0, width, 0, height));
		//ads a platform that moves on the x axis
		mPlat.add(new Platform(200, 300, 0, width, 0, height));
		mPlat.get(0).setXSpeed(14-7);
		//adds a platform that moves on the y axis
		mPlat.add(new Platform(700, 400, 0, width, 300, 500));
		mPlat.get(1).setYSpeed(14-10);

		//adds a block that falls 
		fBlock.add(new FallingBlock(500, 250, 0, width, 0, height));

		player[0] = new Player(30, 450, 0, width, 0, height);

		walkers.add(new RoamingEnemy(400, 500, 200, 600, 0, height));
		walkers.get(0).setXSpeed(walkerSpeed);

		slugs.add(new Slug(500, 500, 400, 600, 0 , height));
		slugs.get(0).setXSpeed(2*slugSpeed/3);
		//begins game
		Thread gameThread = new Thread(this);
		gameThread.start();

	}

	public void run() {
		while (true) {
			this.requestFocusInWindow();
			if (State == STATE.GAME) {			
				repaint();
				try{
					Thread.sleep(pauseDuration);

					//			if(collisionOfPlatformAndBoundriesLeftRight()){
					//				mPlat.get(0).setXSpeed(mPlat.get(0).getXspeed() *-1);
					//			}
					//			if(collisionOfPlatformAndBoundriesUpDown()){
					//				mPlat.get(1).setYSpeed(mPlat.get(1).getYspeed() *-1);
					//			}
					if(collisionOfPlayerAndGround() && player[0].getYspeed() > 0){
						player[0].setYSpeed(0);
						player[0].setY((int) (ground.get(0).getY() - player[0].getHeight()));
					}
					/*if(collisionOfSaltBallsAndGround()){
					saltBalls.get(0).setYSpeed((int)(saltBalls.get(0).getY() *-1));
				}*/
					if(collisionOfPlayerAndFallingBlock() && player[0].getYspeed() > 0){
						player[0].setYSpeed(0);
						player[0].setY((int) (ground.get(0).getY() - player[0].getHeight()));
					}
					if(collisionOfPlayerAndItemBlock() && player[0].getYspeed() > 0){
						player[0].setYSpeed(0);
						player[0].setY((int) (ground.get(0).getY() - player[0].getHeight()));
					}
					if(collisionOfPlayerAndNormalBlocks() && player[0].getYspeed() > 0){
						player[0].setYSpeed(0);
						player[0].setY((int) (ground.get(0).getY() - player[0].getHeight()));
					}
					if(collisionOfPlayerAndPlatform() && player[0].getYspeed() > 0){
						player[0].setYSpeed(0);
						player[0].setY((int) (ground.get(0).getY() - player[0].getHeight()));
					}

					if(Character.toString(key).equalsIgnoreCase("w") && player[0].getYspeed() == 0){
						//if(checkPlayerCollision()){
						player[0].setJumping(true);
						player[0].setY((int) (player[0].getY() -2));
						//}
					}

					if(player[0].getYspeed() > 0 && !player[0].isJumping()){
						player[0].setYSpeed((player[0].getYspeed() +  1.98)/ 1.0198);
					}
					//				if(player[0].getYspeed() < 0){
					//					player[0].setYSpeed(0);
					//				}
					if(player[0].getY() > height){
						player[0].setY((int) (player[0].getY() - height/2));
					}

					if(Character.toString(key).equalsIgnoreCase("a")){
						player[0].setXSpeed(-speedCap);
					}
					else if(Character.toString(key).equalsIgnoreCase("d")){
						player[0].setXSpeed(speedCap);
					}
					if(Character.toString(key).equalsIgnoreCase("s")){

						if(!player[0].isCrouching()){
							player[0].setCrouching(true);
						}

						player[0].setHeight(player[0].initialHeight/2);

						if(player[0].isCrouching()){
							player[0].setY((int) (player[0].getY() + player[0].initialHeight/2));
						}

					}
					else{ 
						player[0].setHeight(player[0].initialHeight);
						player[0].setCrouching(false);
					}
					if(player[0].getYspeed() > 0){
						player[0].setYSpeed((player[0].getYspeed() +  1.98)/ 1.0198);
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
					}

					if(walkers.size() > 0){
						for(int i = 0; i < walkers.size(); i++){
							if(walkers.get(i).hitBoundry()){
								walkers.get(i).setXSpeed(slugSpeed * - 1);
								walkers.get(i).movingToBoundry(false);
							}
						}
					}
					if(slugs.size() > 0){
						for(int i = 0; i < slugs.size(); i++){
							if(slugs.get(i).isShooting()){	
								if(slugs.get(i).getXspeed() >= 0){
									slugDirection = 1;
									slimeBalls.add(new SlugProjectile(slugs.get(i).getX() + slugs.get(i).getWidth(), slugs.get(i).getY() + slugs.get(i).getHeight()/4, 0, width, 0, height));							
								}
								else if(slugs.get(i).getXspeed() < 0){
									slugDirection = -1;
									slimeBalls.add(new SlugProjectile(slugs.get(i).getX(), slugs.get(i).getY() + slugs.get(i).getHeight()/4, 0, width, 0, height));
								}
								slimeBalls.get(slimeBalls.size() - 1).setDirection(slugDirection);
								slimeBalls.get(slimeBalls.size() - 1).setXSpeed(slugSpeed * slugDirection);
								slugs.get(i).setShooting(false);
							}
							if(slugs.get(i).hitBoundry()){
								slugs.get(i).setXSpeed(2*slugSpeed/3 * - 1);
								slugs.get(i).movingToBoundry(false);
							}
						}
					}

					deleteSlugProjectile();
					deletePlayerProjectile();

				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					System.out.println("Interrupted");
				} catch(ArrayIndexOutOfBoundsException e) {

				}
			}
			else{
				//in the main menu
			}
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (State == STATE.GAME) {
			g.drawString ("Number of salt balls: " + saltBalls.size(), 5, 20);
			g.drawString ("Number of slime balls: " + slimeBalls.size(), 5, 40);
			g.drawString ("Current Key: " + key, 5, 60);
			g.drawString ("Current Direction: " + playerProjectileDirection, 5, 80);
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
			player[0].draw(g);
			for (int i = 0; i < saltBalls.size(); i++) {
				saltBalls.get(i).draw(g);
			}
			for (int i = 0; i < walkers.size(); i++) {
				walkers.get(i).draw(g);
			}		
			for (int i = 0; i < slugs.size(); i++) {
				slugs.get(i).draw(g);
			}
			for (int i = 0; i < slimeBalls.size(); i++) {
				slimeBalls.get(i).draw(g);
			}
		}
		else if (State == STATE.MENU) {
			MainMenu.render(g);
		}
	}

	public boolean collisionOfPlatformAndBoundriesUpDown(){
		if(mPlat.get(1).getX() + mPlat.get(1).getHeight() > Math.abs(500) || mPlat.get(1).getX() < Math.abs(100)){
			return true;
		}
		return false;
	}

	//Collision for the boundaries of panels when they move from left to right
	public boolean collisionOfPlatformAndBoundriesLeftRight(){
		if(mPlat.get(0).getX() + mPlat.get(0).getWidth() > Math.abs(500) || mPlat.get(0).getX() < Math.abs(100)){
			return true;
		}
		return false;
	}

	public boolean isPlayerHit(){
		for (int i = 0; i < slugs.size(); i++){
			for(int j = 0; j < slugs.get(i).getWidth(); j++){
				for(int k = 0; k < player[0].getWidth(); k++){
					if(player[0].getY() + player[0].getHeight() + k == mPlat.get(i).getY() + j && player[0].getX() + k == mPlat.get(i).getX() + j){
						return true;
					}
					else if(player[0].getY() == mPlat.get(i).getY()){
						return true;
					}
				}
			}
		}
		return false;
	}

	//Collision for the player to the ground so the player does not go through the ground 
	public boolean collisionOfPlayerAndGround(){
		if(player[0].getY() + player[0].getHeight() == ground.get(0).getY()){
			return true;
		}
		else
			return false;
	}
	//Collision for the saltballs too the ground so the projectiles don't go through the ground 
	public boolean collisionOfSaltBallsAndGround(){
		if(saltBalls.get(0).getY() + saltBalls.get(0).getRadius() == ground.get(0).getY()){
			return true;
		}
		else
			return false;
	}      
	//collision for the player and the falling blocks so he can stand on the falling blocks 
	public boolean collisionOfPlayerAndFallingBlock(){
		if(player[0].getY() + player[0].getHeight() > fBlock.get(0).getY()&& (player[0].getX() + player[0].getWidth() < fBlock.get(0).getX() + fBlock.get(0).getWidth()|| player[0].getX() > fBlock.get(0).getX())){
			return true;
		}
		else
			return false;
	}
	//collision for the player to stand on the moving platforms
	public boolean collisionOfPlayerAndPlatform(){
		if(player[0].getY() + player[0].getHeight() > mPlat.get(0).getY() && (player[0].getX() + player[0].getWidth() < mPlat.get(0).getX() + mPlat.get(0).getWidth()|| player[0].getX() > mPlat.get(0).getX())){
			return true;
		}
		else
			return false;
	}
	//collision for the player to stand on a normal block 
	public boolean collisionOfPlayerAndNormalBlocks(){
		if(player[0].getY() + player[0].getHeight() < block.get(0).getY() && (player[0].getX() + player[0].getWidth() < block.get(0).getX() + block.get(0).getWidth() || player[0].getX() > block.get(0).getX())){
			return true;
		}
		else
			return false;
	}
	//for the player to stand on an item block
	public boolean collisionOfPlayerAndItemBlock(){
		if(player[0].getY() + player[0].getHeight() < iBlock.get(0).getY()&& (player[0].getX() + player[0].getWidth() < iBlock.get(0).getX() + iBlock.get(0).getWidth()|| player[0].getX() > iBlock.get(0).getX()) ){
			return true;
		}
		else
			return false;
	}

	/**
	 * sets the x direction the player's projectile is traveling towards
	 */
	public void keyPressed(KeyEvent e) {
		if (State == STATE.GAME) {
			key = e.getKeyChar();
			//limits the number of player projectiles that can spawn
			if(saltBalls.size() < 50){
				if(Character.toString(key).equalsIgnoreCase("a")){
					playerProjectileDirection = -1;
				}
				else if(Character.toString(key).equalsIgnoreCase("d")){
					playerProjectileDirection = 1;
				}
				else if(Character.toString(key).equalsIgnoreCase(" ")){
					if(playerProjectileDirection == 1){
						saltBalls.add(new PlayerProjectile(player[0].getX() + player[0].getWidth(), player[0].getY() + player[0].getHeight()/2, 0, width, 0, (int) ground.get(0).getY()));
					}
					else if(playerProjectileDirection == -1){
						saltBalls.add(new PlayerProjectile(player[0].getX(), player[0].getY() + player[0].getHeight()/2, 0, width, 0, (int) ground.get(0).getY()));
					}
					isPlayerProjectileSpawned = true;
				}
				//shoots slug projectiles
				//			else if(Character.toString(key).equalsIgnoreCase("e")){
				//				slimeBalls.get(slimeBalls.size() - 1).setDirection(playerProjectileDirection);
				//				slimeBalls.add(new SlugProjectile(playerX, playerY, 0, width, 0, height));
				//			}
			}
			else{
				isPlayerProjectileSpawned = false;
			}
		}
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
		playerX = e.getX ();
		playerY = e.getY ();
		//Spawns new saltballs upon mouse click
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
	private static void deletePlayerProjectile(){
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
	private static void deleteSlugProjectile(){
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