package QWithoutA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class SaltGamePanel  extends JPanel implements Runnable, KeyListener {
	
	int width = 1100;
	int height = 550;
	
	//an arraylist of blocks
	ArrayList<NormalBlock> block = new ArrayList<NormalBlock>();
	//an arraylist of the ground (can also make hills)
	ArrayList<Ground> ground = new ArrayList<Ground>();
	//an arraylist of itemblocks
	ArrayList<ItemBlock> iBlock = new ArrayList<ItemBlock>();
	//an arraylist of movingplatforms 
	ArrayList<MovingPlatform> mPlat = new ArrayList<MovingPlatform>();
	ArrayList<FallingBlock> fallBlock = new ArrayList<FallingBlock>();
	
    static ArrayList<PlayerProjectile> saltBalls = new ArrayList<PlayerProjectile>();
    static ArrayList<SlugProjectile> slimeBalls = new ArrayList<SlugProjectile>();
    
    ArrayList<Player> player = new ArrayList<Player>();
    
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


	/**
	 * The pause between repainting (should be set for about 30 frames per
	 * second).
	 */
	final int pauseDuration = 50;
	int screenCount = 0; 

	public static void main(String[] args) {

		// Set up main window (using Swing's Jframe)
		JFrame frame = new JFrame("The Adventures of Salt Man");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1100, 550));
		frame.setAutoRequestFocus(false);
		JOptionPane.showMessageDialog(frame, "To win this game, make your way past enemies and holes to get the item at the end." + "\n" 
				+ "If you happen to die, you will have to restart your journey.", 
				"Welcome", JOptionPane.INFORMATION_MESSAGE);
		frame.setVisible(true);
		//frame.setLayout(new BorderLayout());
		Container c = frame.getContentPane();
		c.add(new SaltGamePanel());
		frame.setResizable(false);
		frame.pack();
	}

	public SaltGamePanel(){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.CYAN);
		setFocusable(true);
		addKeyListener(this);
		
		if (screenCount == 0) {
			ground.add(new Ground(0, 525, 0, width, 0, height));
			
			iBlock.add(new ItemBlock(250, 300, 0, width, 0, height));
			
			block.add(new NormalBlock(150, 300, 0, width, 0, height));
			block.add(new NormalBlock(350, 300, 0, width, 0, height));
			
			fallBlock.add(new FallingBlock(500, 400, 0, width, 0, height));
			
			mPlat.add(new MovingPlatform(200, 250, 0, width, 0, height));
			mPlat.get(0).setXSpeed(14-7);
			
			mPlat.add(new MovingPlatform(800, 250, 0, width, 0, height));
			mPlat.get(1).setYSpeed(14-10);
			
			player.add(new Player(30, 400, 0, width, 0, height));

			Thread gameThread = new Thread(this);
			gameThread.start();
			
		}

	}
	
	public void run() {
		while (true) {
			repaint();
			checkCollision();
			if(checkCollision()){
				mPlat.get(0).setXSpeed(mPlat.get(0).getXSpeed() *-1);
				mPlat.get(1).setYSpeed(mPlat.get(1).getYSpeed() *-1);
				for (double fall = fallBlock.get(0).getY(); fall <= height; fall--) {
					fallBlock.get(0).setY(fall); 
				}
			}
			
			try {
				Thread.sleep(pauseDuration);

				if(Character.toString(key).equalsIgnoreCase("a")){
					player.get(0).setXSpeed(-speedCap);
				}
				else if(Character.toString(key).equalsIgnoreCase("d")){
					player.get(0).setXSpeed(speedCap);
				}
				else if(Character.toString(key).equalsIgnoreCase("w")){
					player.get(0).setYSpeed(-speedCap);
				}
				else if(Character.toString(key).equalsIgnoreCase("s")){
					player.get(0).setHeight(player.get(0).getHeight()/2);
				}
				player.get(0).setYSpeed((player.get(0).getYSpeed() +  1.98)/ 1.0198);
				if(player.get(0).getYSpeed() < 0){
					player.get(0).setYSpeed(0);
				}
				if(player.get(0).getY() > height){
					player.get(0).setY((int) (player.get(0).getY() - height/2));
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
							if(saltBalls.get(i).getBouncing() && saltBalls.get(i).getYSpeed() == 0){
								saltBalls.get(i).setYSpeed(saltBalls.get(i).getYSpeed());
							}
							else{
								saltBalls.get(i).setYSpeed((saltBalls.get(i).getYSpeed() + saltBalls.get(i).getProjectileAcceleration())
										/ saltBalls.get(i).getGravityConstant());
							}
							if(saltBalls.get(i).getY() > height){
								saltBalls.get(i).setY((int) saltBalls.get(i).getY() - 10);
							}
							if(saltBalls.get(i).getYSpeed() > -1 && saltBalls.get(i).getYSpeed() < 1){
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
					}
				}

				deletePlayerProjectile();
				deleteSlugProjectile();
				System.out.println(saltBalls.size());
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
		
		for (int i = 0; i < fallBlock.size(); i++) {  
			g.setColor(Color.red);  
			fallBlock.get(i).draw(g);
			  }
		//Draws the player's projectiles
		
		for (int i = 0; i < saltBalls.size(); i++) {
			saltBalls.get(i).draw(g);
		}
		player.get(0).draw(g);
		for (int i = 0; i < slimeBalls.size(); i++) {
			slimeBalls.get(i).draw(g);
		}
	}
	
	// collision method to determine when a certain object (currently moving platform) impacts something (currently a set of coordinates) 
	public boolean checkCollision(){
		if(player.get(0).getX() + player.get(0).getWidth() >= fallBlock.get(0).getX() && player.get(0).getY() - player.get(0).getHeight() == fallBlock.get(0).getY()){
			return true;
		}
		
		if(mPlat.get(0).getX() + mPlat.get(0).getWidth() > Math.abs(500) || mPlat.get(0).getX() < Math.abs(100)){
		return true;
		}
	else {
		return false;
		}
	}
	
	
	
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
					saltBalls.add(new PlayerProjectile(player.get(0).getX() + player.get(0).getWidth(), player.get(0).getY() + player.get(0).getHeight()/2, 0, width, 0, height));
				}
				else if(playerProjectileDirection == -1){
					saltBalls.add(new PlayerProjectile(player.get(0).getX(), player.get(0).getY() + player.get(0).getHeight()/2, 0, width, 0, height));
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
		player.get(0).setXSpeed(0);
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		playerX = e.getX ();
		playerY = e.getY ();
		repaint ();
	}
	
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

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
	
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
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