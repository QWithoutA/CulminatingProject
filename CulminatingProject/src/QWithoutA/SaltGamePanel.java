package QWithoutA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Entities.Player;
import Entities.PlayerProjectile;
import Entities.RoamingEnemy;
import Entities.Slug;
import Entities.SlugProjectile;
//import Images.Imageloader;

/**
 * 
 * @author Team QWithoutA
 *
 */
@SuppressWarnings("serial")
public class SaltGamePanel  extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {
	/**
     * The width and height of the Jpanel
     */
	static int width = 1100;
	static int height = 550;
	
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	
	/**
	 * an arraylist of the blocks
	 */
	ArrayList<Blocks> block = new ArrayList<Blocks>();    
	/**
	 * an arraylist of the ground (can also make hills)
	 */
	ArrayList<Ground> ground = new ArrayList<Ground>();
	/**
	 * an arraylist of the item blocks
	 */
	ArrayList<ItemBlock> iBlock = new ArrayList<ItemBlock>();
	/**
	 * an arraylist of horizontally moving platforms 
	 */
	ArrayList<Platform> mPlatHorizontal = new ArrayList<Platform>();
	/**
	 * an arraylist of vertically moving platforms 
	 */
	ArrayList<Platform> mPlatVertical = new ArrayList<Platform>();
	/**
	 * an array of falling blocks
	 */
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
    static ArrayList<Player> player = new ArrayList<Player>();
    /**
     * Ranged-enemies commonly known as 'slugs'
     */
    ArrayList<RoamingEnemy> walkers = new ArrayList<RoamingEnemy>();
    /**
     * Ranged-enemies commonly known as 'slugs'
     */
    ArrayList<Slug> slugs = new ArrayList<Slug>();
    
	/**
	 * The pause between repainting (should be set for about 30 frames per second).
	 */
	final int pauseDuration = 40;
	
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
	
	/**
	 * X co-ordinate of the mouse 
	 */
	private int mouseX;
	/**
	 * Y co-ordinate of the mouse 
	 */
	private int mouseY;
	
	private int level = 1;
	private int time = -1;
	
	public boolean checkState;
	
	public boolean isPlayerProjectileSpawned = false;
	public boolean isSlugProjectileSpawned = false;
	
	private final double playerProjectileMaxHeight = 20;
	private final double playerProjectileAcceleration = 9.8;

	public double initialVelocity = Math.sqrt(-2*playerProjectileAcceleration *playerProjectileMaxHeight);
	
	private MainMenu mainMenu;
	private BufferedImage image;

	public enum STATE{
		MENU,
		GAME,
		DEATH
	};
	public static STATE State = STATE.MENU;
	
	public static void main(String[] args) {

		// Set up main window (using Swing's Jframe)
		JFrame frame = new JFrame("Salt Man Adventures");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1100, 550));
		frame.setAutoRequestFocus(false);
		
		JOptionPane.showMessageDialog(frame, "This is a beta game. Expect many bugs and issues during gameplay." + "\n" 
		+ "If you happen to die, you will have to restart from your current level each time.", "Proceed With Caution!", JOptionPane.WARNING_MESSAGE);
		
		frame.setVisible(true);
		frame.setLocation(100, 100);
		Container c = frame.getContentPane();
		c.add(new SaltGamePanel());
		frame.pack();
		frame.setTitle("The Adventures of Salt Man");
		frame.setResizable(false);
	}
	/**
	 * this is the salt game panel, this is the stuff that will appere on the game panel for you to play
	 */
	public SaltGamePanel(){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.CYAN);
		try{
			image = ImageIO.read(Player.class.getResourceAsStream("/Images/Background.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		setFocusable(true);
		addKeyListener(this);
		addMouseListener (this);
		addMouseMotionListener(this);
		
		this.addMouseListener(new MouseInput());
		
		player.add(new Player(30, 250, 0, width, 0, height));
		this.levelGlen();
//		this.levelTimothy();
//		this.levelRosauro();
//		this.levelDavid();
		if (State == STATE.GAME) {
			executor.scheduleAtFixedRate(timer, 0, 1, TimeUnit.SECONDS);
		}
		//adds main menu
		mainMenu = new MainMenu();
		
		State = STATE.MENU;

		//begins game
		Thread gameThread = new Thread(this);
		gameThread.start();
		
	}	
	
	//the first level that glen created 
	public void levelGlen(){
		ground.add(new Ground(0, 525, 0, width, 0, height));
		ground.get(0).setWidth(ground.get(0).getWidth()/2);
		
		ground.add(new Ground(750, 525, 0, width, 0, height));
		ground.get(1).setWidth(ground.get(1).getWidth()/3);
		
		ground.add(new Ground(448, 490, 0, width, 0, height));
		ground.get(2).setWidth(ground.get(2).getWidth()/9);
		
		ground.add(new Ground(0, 200, 0, width, 0, height));
		ground.get(3).setWidth(ground.get(3).getWidth()/4);
		
		// adds item blocks
		iBlock.add(new ItemBlock(250, 350, 0, width, 0, height));
		//adds regular blocks
		block.add(new Blocks(150, 350, 0, width, 0, height));
		block.add(new Blocks(350, 350, 0, width, 0, height));
		for(int i = 0; i<5; i++){
		block.add(new Blocks(525 + (i * 35), 300, 0, width, 0, height));
		}
		//adds a platform that moves on the x axis
		mPlatHorizontal.add(new Platform(800, 300, 750, 1000, 0, height));
		mPlatHorizontal.get(0).setXSpeed(3);
		//adds a platform that moves on the y axis
		mPlatVertical.add(new Platform(600, 400, 0, width, 300, 500));
		mPlatVertical.get(0).setYSpeed(3);

		//adds a block that falls 
		fBlock.add(new FallingBlock(500, 300, 0, width, 0, height));
		
		walkers.add(new RoamingEnemy(200, 500, 200, 400, 0, height));
		walkers.get(0).setXSpeed(walkerSpeed);
		
		slugs.add(new Slug(500, 460, 400, 550, 0 , height));
		slugs.get(0).setXSpeed(2*slugSpeed/3);
		
		slugs.add(new Slug(750, 500, 700, 900, 0 , height));
		slugs.get(1).setXSpeed(2*slugSpeed/3);
	}
	
	// the first level that Timothy created 
	public void levelTimothy(){
		ground.add(new Ground(0, 525, 0, width, 0, height));
		ground.get(0).setWidth(ground.get(0).getWidth()/3);
		
		ground.add(new Ground(750, 525, 0, width, 0, height));
		ground.get(1).setWidth(ground.get(1).getWidth()/3);
		
		ground.add(new Ground(450, 450, 500, width, 0, height));
		ground.get(2).setWidth(ground.get(2).getWidth()/5);
		
		ground.add(new Ground(0, 200, 0, width, 0, height));
		ground.get(3).setWidth(ground.get(3).getWidth()/7);
		
		ground.add(new Ground(950, 200, 0, width, 0, height));
		ground.get(4).setWidth(ground.get(4).getWidth()/7);
		
		ground.add(new Ground(420, 270, 0, width, 0, height));
		ground.get(5).setWidth(ground.get(5).getWidth()/4);
			
		mPlatHorizontal.add(new Platform(500, 200, 250, 700, 0, height));
		mPlatHorizontal.get(0).setXSpeed(3);
		
		mPlatVertical.add(new Platform(900, 400, 0, width, 270, 500));
		mPlatVertical.get(0).setYSpeed(3);
		
		mPlatVertical.add(new Platform(100, 398, 0, width, 270, 500));
		mPlatVertical.get(1).setYSpeed(4);
		
		slugs.add(new Slug(50, 170, 20, 100, 0 , height));
		slugs.get(0).setXSpeed(2*slugSpeed/3);
		
		slugs.add(new Slug(950, 170, 940, 1050, 0 , height));
		slugs.get(1).setXSpeed(2*slugSpeed/3);
		
		slugs.add(new Slug(500, 250, 450, 600, 0 , height));
		slugs.get(2).setXSpeed(4*slugSpeed/3);
		
		walkers.add(new RoamingEnemy(50, 500, 0, 350, 0, height));
		walkers.get(0).setXSpeed(walkerSpeed*2);
		
		walkers.add(new RoamingEnemy(500, 425, 450, 650, 0, height));
		walkers.get(1).setXSpeed(walkerSpeed*3);
		
		walkers.add(new RoamingEnemy(800, 500, 750, 1050, 0, height));
		walkers.get(2).setXSpeed(walkerSpeed);
		
		block.add(new Blocks(350, 325, 0, width, 0, height));
		
		block.add(new Blocks(450, 325, 0, width, 0, height));
		
		block.add(new Blocks(550, 325, 0, width, 0, height));
		
		block.add(new Blocks(750, 325, 0, width, 0, height));
	
		block.add(new Blocks(350, 325, 0, width, 0, height));
		
		iBlock.add(new ItemBlock(650, 325, 0, width, 0, height));
		
		iBlock.add(new ItemBlock(50, 75, 0, width, 0, height));
		
		iBlock.add(new ItemBlock(1000, 75, 0, width, 0, height));
		level = 3;
	}
	
	// the first level that Rosauro created 
	public void levelRosauro(){
		ground.add(new Ground(0, 525, 0, width, 0, height));
		ground.get(0).setWidth(ground.get(0).getWidth()/7);
		
		ground.add(new Ground(350, 525, 0, width, 0, height));
		ground.get(1).setWidth(ground.get(1).getWidth()/8);
		
		ground.add(new Ground(650, 525, 0, width, 0, height));
		ground.get(2).setWidth(ground.get(2).getWidth()/8);
		
		ground.add(new Ground(950, 525, 0, width, 0, height));
		ground.get(3).setWidth(ground.get(3).getWidth()/8);
		
		ground.add(new Ground(250, 450, 0, width, 0, height));
		ground.get(4).setWidth(ground.get(4).getWidth()/6);
		
		ground.add(new Ground(300, 250, 0, width, 0, height));
		ground.get(5).setWidth(ground.get(5).getWidth()/6);
		
		ground.add(new Ground(600, 250, 0, width, 0, height));
		ground.get(6).setWidth(ground.get(6).getWidth()/6);
		
		ground.add(new Ground(900, 350, 0, width, 0, height));
		ground.get(7).setWidth(ground.get(7).getWidth()/6);
		
		ground.add(new Ground(900, 150, 0, width, 0, height));
		ground.get(8).setWidth(ground.get(8).getWidth()/6);
		
		ground.add(new Ground(0, 150, 0, width, 0, height));
		ground.get(9).setWidth(ground.get(9).getWidth()/6);
		
		ground.add(new Ground(600, 400, 0, width, 0, height));
		ground.get(10).setWidth(ground.get(10).getWidth()/8);
		
		fBlock.add(new FallingBlock(100, 350, 0, width, 0, height));
		
		slugs.add(new Slug(300, 425, 300, 425, 0 , height));
		slugs.get(0).setXSpeed(2*slugSpeed/6);
		
		slugs.add(new Slug(620, 230, 600, 750, 0 , height));
		slugs.get(1).setXSpeed(2*slugSpeed/3);
		
		slugs.add(new Slug(600, 375, 600, 720, 0 , height));
		slugs.get(2).setXSpeed(2*slugSpeed/3);
		
		slugs.add(new Slug(320, 500, 320, 450, 0 , height));
		slugs.get(3).setXSpeed(2*slugSpeed/3);
		
		walkers.add(new RoamingEnemy(0, 120, 0, 150, 0, height));
		walkers.get(0).setXSpeed(walkerSpeed*2);
		
		walkers.add(new RoamingEnemy(650, 495, 650, 760, 0, height));
		walkers.get(1).setXSpeed(walkerSpeed*3);
		
		walkers.add(new RoamingEnemy(950, 495, 950, 1050, 0, height));
		walkers.get(2).setXSpeed(walkerSpeed);
		
		walkers.add(new RoamingEnemy(950, 120, 950, 1050, 0, height));
		walkers.get(3).setXSpeed(walkerSpeed);
		level = 2;
	}
	// the first level that David created 
	public void levelDavid(){
		ground.add(new Ground(0, 525, 0, width, 0, height));
		ground.get(0).setWidth(ground.get(0).getWidth()/3);
		
		ground.add(new Ground(750, 525, 0, width, 0, height));
		ground.get(1).setWidth(ground.get(1).getWidth()/3);
		
		ground.add(new Ground(450, 525, 500, width, 0, height));
		ground.get(2).setWidth(ground.get(2).getWidth()/5);
		
		ground.add(new Ground(0, 250, 0, width, 0, height));
		ground.get(3).setWidth(ground.get(3).getWidth()/7);
		
		ground.add(new Ground(950, 250, 0, width, 0, height));
		ground.get(4).setWidth(ground.get(4).getWidth()/7);
		
		ground.add(new Ground(100, 450, 0, width, 0, height));
		ground.get(5).setWidth(ground.get(5).getWidth()/8);
		
		ground.add(new Ground(200, 400, 0, width, 0, height));
		ground.get(6).setWidth(ground.get(6).getWidth()/8);

		ground.add(new Ground(300, 350, 0, width, 0, height));
		ground.get(7).setWidth(ground.get(7).getWidth()/6);
		
		ground.add(new Ground(650, 350, 0, width, 0, height));
		ground.get(8).setWidth(ground.get(8).getWidth()/6);
		
		ground.add(new Ground(900, 450, 0, width, 0, height));
		ground.get(9).setWidth(ground.get(9).getWidth()/8);
		
		ground.add(new Ground(800, 400, 0, width, 0, height));
		ground.get(10).setWidth(ground.get(10).getWidth()/8);
		
		ground.add(new Ground(250, 175, 0, width, 0, height));
		ground.get(11).setWidth(ground.get(11).getWidth()/8);
		
		ground.add(new Ground(450, 175, 0, width, 0, height));
		ground.get(12).setWidth(ground.get(12).getWidth()/4);
		
		ground.add(new Ground(800, 175, 0, width, 0, height));
		ground.get(13).setWidth(ground.get(13).getWidth()/8);
	
		mPlatVertical.add(new Platform(525, 400, 0, width, 270, 500));
		mPlatVertical.get(0).setYSpeed(3);
		
		slugs.add(new Slug(300, 325, 300, 425, 0 , height));
		slugs.get(0).setXSpeed(2*slugSpeed/3);
		
		slugs.add(new Slug(450, 150, 450, 650, 0 , height));
		slugs.get(1).setXSpeed(2*slugSpeed/3);
		
		walkers.add(new RoamingEnemy(50, 500, 0, 350, 0, height));
		walkers.get(0).setXSpeed(walkerSpeed*2);
		
		walkers.add(new RoamingEnemy(650, 325, 650, 800, 0, height));
		walkers.get(1).setXSpeed(walkerSpeed*3);
		
		walkers.add(new RoamingEnemy(800, 500, 750, 1050, 0, height));
		walkers.get(2).setXSpeed(walkerSpeed);
		
		//block.add(new Blocks(350, 325, 0, width, 0, height));
		
		//block.add(new Blocks(450, 325, 0, width, 0, height));
		
		//block.add(new Blocks(750, 325, 0, width, 0, height));
	
		//block.add(new Blocks(350, 325, 0, width, 0, height));
		
		iBlock.add(new ItemBlock(550, 225, 0, width, 0, height));
		
		block.add(new Blocks(50, 75, 0, width, 0, height));
		
		block.add(new Blocks(1000, 75, 0, width, 0, height));
		level = 4;
	}
	
	
	/**
	 * this is the run method, this is what happens when the game is run such as checking the collisions and making stuff appear.
	 */
	public void run() {
		while (true) {
			this.requestFocusInWindow();
			if (State == STATE.GAME) {
				if(time == -1)
				executor.scheduleAtFixedRate(timer, 0, 1, TimeUnit.SECONDS);
				this.setBackground(Color.CYAN);
				repaint();
				try{
					Thread.sleep(pauseDuration);
					for(int i = 0; i<ground.size(); i++){
						if(ground.get(i).checkStandingCollision(player.get(0)) && player.get(0).getYspeed() > 0){
							player.get(0).setYSpeed(0);
							player.get(0).setY((int) (ground.get(i).getY() - player.get(0).getHeight()));
							//System.out.println("on top");
						}
						if(ground.get(i).checkBottomCollision(player.get(0)) && player.get(0).getYspeed() < 0){
							player.get(0).setY((int) ground.get(i).getY() + ground.get(i).getHeight());
							player.get(0).setJumping(false);
							//System.out.println("bottom");
						}
						if(ground.get(i).checkLeftSideCollision(player.get(0))){
							player.get(0).setX((int) ground.get(i).getX() - player.get(0).getWidth() - 5);
							//System.out.println("left side hit");
						}
						else if(ground.get(i).checkRightSideCollision(player.get(0))){
							player.get(0).setX((int) ground.get(i).getX() + ground.get(i).getWidth() + 5);
							//System.out.println("right side hit");
						}
					}
					
					for(int i = 0; i<mPlatHorizontal.size(); i++){
						if(mPlatHorizontal.get(i).checkStandingCollision(player.get(0)) && player.get(0).getYspeed() > 0){
							player.get(0).setYSpeed(mPlatHorizontal.get(i).getXspeed());
							player.get(0).setY((int) (mPlatHorizontal.get(i).getY() - player.get(0).getHeight()));
							//System.out.println("on top");
						}
					}

					for(int i = 0; i<mPlatVertical.size(); i++){
						if(mPlatVertical.get(i).checkStandingCollision(player.get(0)) && player.get(0).getYspeed() > 0){
							player.get(0).setYSpeed(mPlatVertical.get(i).getYspeed());
							player.get(0).setY((int) (mPlatVertical.get(i).getY() - player.get(0).getHeight()));
							//System.out.println("on top");
						}
					}	
					
					for(int i = 0; i<fBlock.size(); i++){
						if(fBlock.get(i).checkStandingCollision(player.get(0)) && player.get(0).getYspeed() > 0){
							player.get(0).setYSpeed(0);
							player.get(0).setY((int) (fBlock.get(i).getY() - player.get(0).getHeight()));
							fBlock.get(i).setFalling(true);
							if(fBlock.get(i).getCountFalling() > 25){
								fBlock.get(0).setYSpeed(3);
								fBlock.get(i).setFalling(false);
							}
						}
						if(fBlock.get(i).getY() + fBlock.get(i).getHeight() > height){
								fBlock.remove(i);
						}
					}
					for(int i = 0; i<iBlock.size(); i++){
						if(iBlock.get(i).checkStandingCollision(player.get(0)) && player.get(0).getYspeed() > 0){
							player.get(0).setYSpeed(0);
							player.get(0).setY((int) (iBlock.get(i).getY() - player.get(0).getHeight()));
							//System.out.println("on top");
						}
						if(iBlock.get(i).checkBottomCollision(player.get(0)) && player.get(0).getYspeed() < 0){
							player.get(0).setY((int) iBlock.get(i).getY() + iBlock.get(i).getHeight());
							player.get(0).setJumping(false);
							player.get(0).setYSpeed(player.get(0).getYspeed() *-1);
							//System.out.println("broke");

						}
						if(iBlock.get(i).checkLeftSideCollision(player.get(0))){
							player.get(0).setX((int) iBlock.get(i).getX() - player.get(0).getWidth() - 5);
							//System.out.println("left side hit");
						}
						else if(iBlock.get(i).checkRightSideCollision(player.get(0))){
							player.get(0).setX((int) iBlock.get(i).getX() + iBlock.get(0).getWidth() + 5);
							//System.out.println("right side hit");
						}
					}
					if (block.size() > 0){
					for(int i = 0; i < block.size(); i++){
						if(block.get(i).checkStandingCollision(player.get(0)) && player.get(0).getYspeed() > 0){
							player.get(0).setYSpeed(0);
							player.get(0).setY((int) (block.get(i).getY() - player.get(0).getHeight()));
							//System.out.println("on top");
						}
						if(block.get(i).checkBottomCollision(player.get(0)) && player.get(0).getYspeed() < 0){
							block.remove(i);
							//System.out.println("broke");
							break;
						}
						
						if(block.get(i).checkLeftSideCollision(player.get(0))){
							player.get(0).setX((int) block.get(i).getX() - player.get(0).getWidth() - 5);
							//System.out.println("left side hit");
						}
						
						else if(block.get(i).checkRightSideCollision(player.get(0))){
							player.get(0).setX((int) block.get(i).getX() + block.get(0).getWidth() + 5);
							//System.out.println("right side hit");
						}
					}
				}

					if(Character.toString(key).equalsIgnoreCase("w") && player.get(0).getYspeed() == 0){
						player.get(0).setJumping(true);
					}
					if(player.get(0).isJumping()){
						player.get(0).setYSpeed((player.get(0).getYspeed() +  3.98)/ -1.0198 - 40);
					}
					if(player.get(0).getJumpCount() > 20){
						player.get(0).setY((int) (player.get(0).getY() -2));
					}
					else if(player.get(0).getJumpCount() > 10){
						player.get(0).setYSpeed(((player.get(0).getYspeed() + 1.98)/ 1.0198));
						player.get(0).setJumping(false);
					}
					if(!player.get(0).isJumping()){
						player.get(0).setYSpeed((player.get(0).getYspeed() +  1.98)/ 1.0198);
					}
					if(player.get(0).getY() > height){
						player.get(0).setY((int) (player.get(0).getY() - height/4));
					}

					if(Character.toString(key).equalsIgnoreCase("a")){
						player.get(0).setXSpeed(-speedCap);
					}
					else if(Character.toString(key).equalsIgnoreCase("d")){
						player.get(0).setXSpeed(speedCap);
					}

					if(Character.toString(key).equalsIgnoreCase("s")){

						if(!player.get(0).isCrouching()){
							player.get(0).setCrouching(true);
							player.get(0).setY((int) (player.get(0).getY() + player.get(0).initialHeight/2));
							player.get(0).setHeight(player.get(0).initialHeight/2);
						}

					}
					if(!Character.toString(key).equalsIgnoreCase("s")){ 
						if(player.get(0).isCrouching()){
							player.get(0).setY((int) (player.get(0).getY() - player.get(0).initialHeight/2));
							player.get(0).setCrouching(false);
						}
						player.get(0).setHeight(player.get(0).initialHeight);
					}

					if(player.get(0).getYspeed() < 0){ 
						player.get(0).setYSpeed((player.get(0).getYspeed() +  1.98)/ 1.0198);
					}

					//				if(isPlayerHit()){
					//					System.out.println("dead");
					//				}
					if(player.get(0).getY() >= height-player.get(0).getHeight()){
						State = STATE.DEATH;
						if(level == 1)
							time = 0;
						else if(level == 2)
							time = 120;
						else if(level == 3)
							time = 240;
						else if(level == 4)
							time = 360;
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
							if(saltBalls.get(i).getY() >= ground.get(0).getY()){
								saltBalls.get(i).setY((int) ground.get(0).getY() - 2);
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
								walkers.get(i).setYSpeed((walkers.get(i).getYspeed() +  1.98)/ 1.0198);
								walkers.get(i).movingToBoundry(false);
							}
							if(walkers.get(i).getYspeed() < 0){ 
								walkers.get(i).setYSpeed((walkers.get(i).getYspeed() +  1.98)/ 1.0198);
							}
							if(walkers.get(i).checkCollision(player.get(0))){
								State = STATE.DEATH;
								if(level == 1)
									time = 0;
								else if(level == 2)
									time = 120;
								else if(level == 3)
									time = 240;
								else if(level == 4)
									time = 360;
								if(time == 0){
									groundRemover(ground, ground.size());
									blockRemover(block, block.size());
									itemBlockRemover(iBlock, iBlock.size());
									platformRemover(mPlatHorizontal, mPlatHorizontal.size());
									platformRemover(mPlatVertical, mPlatVertical.size());
									fallingBlockRemover(fBlock, fBlock.size());
									walkersRemover(walkers, walkers.size());
									slugRemover(slugs, slugs.size());
									slimeBallRemover(slimeBalls, slimeBalls.size());
									playerProjectileRemover(saltBalls, saltBalls.size());
									player.get(0).setX(30);
									player.get(0).setY(250);
									levelGlen();
								}
								else if(time == 120){
									groundRemover(ground, ground.size());
									blockRemover(block, block.size());
									itemBlockRemover(iBlock, iBlock.size());
									platformRemover(mPlatHorizontal, mPlatHorizontal.size());
									platformRemover(mPlatVertical, mPlatVertical.size());
									fallingBlockRemover(fBlock, fBlock.size());
									walkersRemover(walkers, walkers.size());
									slugRemover(slugs, slugs.size());
									slimeBallRemover(slimeBalls, slimeBalls.size());
									playerProjectileRemover(saltBalls, saltBalls.size());
									player.get(0).setX(30);
									player.get(0).setY(250);
									levelRosauro();
								}
								else if(time == 240){
									groundRemover(ground, ground.size());
									blockRemover(block, block.size());
									itemBlockRemover(iBlock, iBlock.size());
									platformRemover(mPlatHorizontal, mPlatHorizontal.size());
									platformRemover(mPlatVertical, mPlatVertical.size());
									fallingBlockRemover(fBlock, fBlock.size());
									walkersRemover(walkers, walkers.size());
									slugRemover(slugs, slugs.size());
									slimeBallRemover(slimeBalls, slimeBalls.size());
									playerProjectileRemover(saltBalls, saltBalls.size());
									player.get(0).setX(30);
									player.get(0).setY(250);
									levelTimothy();
								}
								else if(time == 360){
									groundRemover(ground, ground.size());
									blockRemover(block, block.size());
									itemBlockRemover(iBlock, iBlock.size());
									platformRemover(mPlatHorizontal, mPlatHorizontal.size());
									platformRemover(mPlatVertical, mPlatVertical.size());
									fallingBlockRemover(fBlock, fBlock.size());
									walkersRemover(walkers, walkers.size());
									slugRemover(slugs, slugs.size());
									slimeBallRemover(slimeBalls, slimeBalls.size());
									playerProjectileRemover(saltBalls, saltBalls.size());
									player.get(0).setX(30);
									player.get(0).setY(250);
									levelDavid();
								}
							}
						}
					}

					if(slugs.size() > 0){
						for(int i = 0; i < slugs.size(); i++){
							if(slugs.get(i).isShooting()){	
								if(slugs.get(i).getXspeed() >= 0){
									slugDirection = 1;
									slimeBalls.add(new SlugProjectile(slugs.get(i).getX() + slugs.get(i).getWidth(), slugs.get(i).getY() + slugs.get(i).getHeight()/4, 0 - width, width *2, 0, height));							
								}
								else if(slugs.get(i).getXspeed() < 0){
									slugDirection = -1;
									slimeBalls.add(new SlugProjectile(slugs.get(i).getX(), slugs.get(i).getY() + slugs.get(i).getHeight()/4, 0 - width, width*2, 0, height));
								}
								slimeBalls.get(slimeBalls.size() - 1).setDirection(slugDirection);
								slimeBalls.get(slimeBalls.size() - 1).setXSpeed(slugSpeed * slugDirection);
								slugs.get(i).setShooting(false);
							}
							if(slugs.get(i).hitBoundry()){
								slugs.get(i).setXSpeed(2*slugSpeed/3 * - 1);
								slugs.get(i).movingToBoundry(false);
							}
							if(slugs.get(i).checkCollision(player.get(0))){
								State = STATE.DEATH;
								if(level == 1)
									time = 0;
								else if(level == 2)
									time = 120;
								else if(level == 3)
									time = 240;
								else if(level == 4)
									time = 360;
								if(time == 0){
									groundRemover(ground, ground.size());
									blockRemover(block, block.size());
									itemBlockRemover(iBlock, iBlock.size());
									platformRemover(mPlatHorizontal, mPlatHorizontal.size());
									platformRemover(mPlatVertical, mPlatVertical.size());
									fallingBlockRemover(fBlock, fBlock.size());
									walkersRemover(walkers, walkers.size());
									slugRemover(slugs, slugs.size());
									slimeBallRemover(slimeBalls, slimeBalls.size());
									playerProjectileRemover(saltBalls, saltBalls.size());
									player.get(0).setX(30);
									player.get(0).setY(250);
									levelGlen();
								}
								else if(time == 120){
									groundRemover(ground, ground.size());
									blockRemover(block, block.size());
									itemBlockRemover(iBlock, iBlock.size());
									platformRemover(mPlatHorizontal, mPlatHorizontal.size());
									platformRemover(mPlatVertical, mPlatVertical.size());
									fallingBlockRemover(fBlock, fBlock.size());
									walkersRemover(walkers, walkers.size());
									slugRemover(slugs, slugs.size());
									slimeBallRemover(slimeBalls, slimeBalls.size());
									playerProjectileRemover(saltBalls, saltBalls.size());
									player.get(0).setX(30);
									player.get(0).setY(250);
									levelRosauro();
								}
								else if(time == 240){
									groundRemover(ground, ground.size());
									blockRemover(block, block.size());
									itemBlockRemover(iBlock, iBlock.size());
									platformRemover(mPlatHorizontal, mPlatHorizontal.size());
									platformRemover(mPlatVertical, mPlatVertical.size());
									fallingBlockRemover(fBlock, fBlock.size());
									walkersRemover(walkers, walkers.size());
									slugRemover(slugs, slugs.size());
									slimeBallRemover(slimeBalls, slimeBalls.size());
									playerProjectileRemover(saltBalls, saltBalls.size());
									player.get(0).setX(30);
									player.get(0).setY(250);
									levelTimothy();
								}
								else if(time == 360){
									groundRemover(ground, ground.size());
									blockRemover(block, block.size());
									itemBlockRemover(iBlock, iBlock.size());
									platformRemover(mPlatHorizontal, mPlatHorizontal.size());
									platformRemover(mPlatVertical, mPlatVertical.size());
									fallingBlockRemover(fBlock, fBlock.size());
									walkersRemover(walkers, walkers.size());
									slugRemover(slugs, slugs.size());
									slimeBallRemover(slimeBalls, slimeBalls.size());
									playerProjectileRemover(saltBalls, saltBalls.size());
									player.get(0).setX(30);
									player.get(0).setY(250);
									levelDavid();
								}
							}
						}
						for(int i = 0; i< slimeBalls.size(); i++){
							if(slimeBalls.get(i).checkCollision(player.get(0))){
								State = STATE.DEATH;
								if(level == 1)
									time = 0;
								else if(level == 2)
									time = 120;
								else if(level == 3)
									time = 240;
								else if(level == 4)
									time = 360;
								if(time == 0){
									groundRemover(ground, ground.size());
									blockRemover(block, block.size());
									itemBlockRemover(iBlock, iBlock.size());
									platformRemover(mPlatHorizontal, mPlatHorizontal.size());
									platformRemover(mPlatVertical, mPlatVertical.size());
									fallingBlockRemover(fBlock, fBlock.size());
									walkersRemover(walkers, walkers.size());
									slugRemover(slugs, slugs.size());
									slimeBallRemover(slimeBalls, slimeBalls.size());
									playerProjectileRemover(saltBalls, saltBalls.size());
									player.get(0).setX(30);
									player.get(0).setY(250);
									levelGlen();
								}
								else if(time == 120){
									groundRemover(ground, ground.size());
									blockRemover(block, block.size());
									itemBlockRemover(iBlock, iBlock.size());
									platformRemover(mPlatHorizontal, mPlatHorizontal.size());
									platformRemover(mPlatVertical, mPlatVertical.size());
									fallingBlockRemover(fBlock, fBlock.size());
									walkersRemover(walkers, walkers.size());
									slugRemover(slugs, slugs.size());
									slimeBallRemover(slimeBalls, slimeBalls.size());
									playerProjectileRemover(saltBalls, saltBalls.size());
									player.get(0).setX(30);
									player.get(0).setY(250);
									levelRosauro();
								}
								else if(time == 240){
									groundRemover(ground, ground.size());
									blockRemover(block, block.size());
									itemBlockRemover(iBlock, iBlock.size());
									platformRemover(mPlatHorizontal, mPlatHorizontal.size());
									platformRemover(mPlatVertical, mPlatVertical.size());
									fallingBlockRemover(fBlock, fBlock.size());
									walkersRemover(walkers, walkers.size());
									slugRemover(slugs, slugs.size());
									slimeBallRemover(slimeBalls, slimeBalls.size());
									playerProjectileRemover(saltBalls, saltBalls.size());
									player.get(0).setX(30);
									player.get(0).setY(250);
									levelTimothy();
								}
								else if(time == 360){
									groundRemover(ground, ground.size());
									blockRemover(block, block.size());
									itemBlockRemover(iBlock, iBlock.size());
									platformRemover(mPlatHorizontal, mPlatHorizontal.size());
									platformRemover(mPlatVertical, mPlatVertical.size());
									fallingBlockRemover(fBlock, fBlock.size());
									walkersRemover(walkers, walkers.size());
									slugRemover(slugs, slugs.size());
									slimeBallRemover(slimeBalls, slimeBalls.size());
									playerProjectileRemover(saltBalls, saltBalls.size());
									player.get(0).setX(30);
									player.get(0).setY(250);
									levelDavid();
								}
							}
						}
					}

					deleteSlugProjectile();
					deletePlayerProjectile();

					if(time == 120){
						groundRemover(ground, ground.size());
						blockRemover(block, block.size());
						itemBlockRemover(iBlock, iBlock.size());
						platformRemover(mPlatHorizontal, mPlatHorizontal.size());
						platformRemover(mPlatVertical, mPlatVertical.size());
						fallingBlockRemover(fBlock, fBlock.size());
						walkersRemover(walkers, walkers.size());
						slugRemover(slugs, slugs.size());
						slimeBallRemover(slimeBalls, slimeBalls.size());
						playerProjectileRemover(saltBalls, saltBalls.size());
						player.get(0).setX(30);
						player.get(0).setY(250);
						levelRosauro();
					}
					else if(time == 240){
						groundRemover(ground, ground.size());
						blockRemover(block, block.size());
						itemBlockRemover(iBlock, iBlock.size());
						platformRemover(mPlatHorizontal, mPlatHorizontal.size());
						platformRemover(mPlatVertical, mPlatVertical.size());
						fallingBlockRemover(fBlock, fBlock.size());
						walkersRemover(walkers, walkers.size());
						slugRemover(slugs, slugs.size());
						slimeBallRemover(slimeBalls, slimeBalls.size());
						playerProjectileRemover(saltBalls, saltBalls.size());
						player.get(0).setX(30);
						player.get(0).setY(250);
						levelTimothy();
					}
					else if(time == 360){
						groundRemover(ground, ground.size());
						blockRemover(block, block.size());
						itemBlockRemover(iBlock, iBlock.size());
						platformRemover(mPlatHorizontal, mPlatHorizontal.size());
						platformRemover(mPlatVertical, mPlatVertical.size());
						fallingBlockRemover(fBlock, fBlock.size());
						walkersRemover(walkers, walkers.size());
						slugRemover(slugs, slugs.size());
						slimeBallRemover(slimeBalls, slimeBalls.size());
						playerProjectileRemover(saltBalls, saltBalls.size());
						player.get(0).setX(30);
						player.get(0).setY(250);
						levelDavid();
					}
					else if(time == 480){
						JFrame frame = new JFrame("Salt Man Adventures");
						//if the player wins
						JOptionPane.showMessageDialog(frame, "Congratulations, you have beaten the game!" + "\n" 
						+ "The game will now end once this text box closes.", "WINNER!", JOptionPane.INFORMATION_MESSAGE);

					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					System.out.println("Interrupted");
				} catch(ArrayIndexOutOfBoundsException e) {
				
				}
		} 
			else if (State == STATE.DEATH)
				repaint();
	}
}
	/**
	 * this is the paintComponent method and it creats the images that are outputed onto the screen
	 */
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		if (State == STATE.GAME) {
			//g.drawString ("Number of salt balls: " + saltBalls.size(), 5, 20);
			//g.drawString ("Number of slime balls: " + slimeBalls.size(), 5, 40);
			//g.drawString ("Current Key: " + key, 5, 60);
			//g.drawString ("Current Direction: " + playerProjectileDirection, 5, 80);
			g.setColor(Color.WHITE);
			g.drawString("Current Time Remaining: " + time, 5, 20);
			
			
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


			for (int i = 0; i < mPlatHorizontal.size(); i++) {  
				g.setColor(Color.BLACK);  
				mPlatHorizontal.get(i).draw(g);
			  }
			
			for (int i = 0; i < mPlatVertical.size(); i++) {  
					g.setColor(Color.BLACK);  
				mPlatVertical.get(i).draw(g);
			}

			for (int i = 0; i < fBlock.size(); i++) {  
				g.setColor(Color.RED);  
				fBlock.get(i).draw(g);
			}

			//Draws the player's projectiles
			player.get(0).draw(g);
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
		else if (State == STATE.DEATH) {
			this.setBackground(Color.RED);
			DeathScreen.render(g);
		}
	}

	/**
	 * collision for the player to stand on the moving platforms	
	 * @param i
	 * @return
	 */
	public boolean collisionOfPlayerAndPlatformHorizontal(int i){
	if((player.get(0).getY() + player.get(0).getHeight() > mPlatHorizontal.get(i).getY() && player.get(0).getY() + player.get(0).getHeight() < mPlatHorizontal.get(i).getY() + mPlatHorizontal.get(i).getHeight()) && (player.get(0).getX() < mPlatHorizontal.get(i).getX() + mPlatHorizontal.get(i).getWidth() && player.get(0).getX() + player.get(0).getWidth() > mPlatHorizontal.get(i).getX())){
		return true;
	}
//	else if ((player.get(0).getY() + player.get(0).getHeight() < mPlatHorizontal.get(i).getY() + mPlatHorizontal.get(i).getHeight()) && player.get(0).getX() < mPlatHorizontal.get(i).getX() + mPlatHorizontal.get(i).getWidth() && player.get(0).getX() + player.get(0).getWidth() > mPlatHorizontal.get(i).getX()){
//		return true;
//	}
	else
		return false;
	}
	
	/**
	 * collision for the player to stand on the moving platforms	
	 * @param i
	 * @return
	 */
	public boolean collisionOfPlayerAndPlatformVertical(int i){
	if((player.get(0).getY() + player.get(0).getHeight() > mPlatVertical.get(i).getY() && player.get(0).getY() + player.get(0).getHeight() < mPlatVertical.get(i).getY() + mPlatVertical.get(i).getHeight()) && (player.get(0).getX() < mPlatVertical.get(i).getX() + mPlatVertical.get(i).getWidth() && player.get(0).getX() + player.get(0).getWidth() > mPlatVertical.get(i).getX())){
			return true;
	}
//	else if (player.get(0).getY() + player.get(0).getHeight() < mPlatVertical.get(i).getY()  + mPlatVertical.get(i).getHeight()&& player.get(0).getX() < mPlatVertical.get(i).getX() + mPlatVertical.get(i).getWidth() && player.get(0).getX() + player.get(0).getWidth() > mPlatVertical.get(i).getX()){
//		return true;
//	}
	else
		return false;
	}
	/**
	 * The game timer
	 */
	Runnable timer = new Runnable() {
		public void run() {
			if(State != STATE.DEATH){
				time++;
			}
		}
	};
	
	/**
	 * sets the x direction the player's projectile is traveling towards
	 */
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (State == STATE.GAME) {
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
					saltBalls.add(new PlayerProjectile(player.get(0).getX() + player.get(0).getWidth(), player.get(0).getY() + player.get(0).getHeight()/2,  0 - width, width*2, 0, height));
				}
				else if(playerProjectileDirection == -1){
					saltBalls.add(new PlayerProjectile(player.get(0).getX(), player.get(0).getY() + player.get(0).getHeight()/2,  0 - width, width*2, 0, height));
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
	/**
	 * Resets key value
	 */
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		key = KeyEvent.CHAR_UNDEFINED;
		player.get(0).setXSpeed(0);
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

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
	/**
	 * removes all objects from the ground arraylist
	 * @param x - arraylist to empty
	 * @param size - total amount of objects stored
	 */
	private static void groundRemover(ArrayList<Ground> x, int size){
		for(int i = size - 1; i>=0; i--)
			x.remove(i);
	}
	/**
	 * removes all objects from the block arraylist
	 * @param x - arraylist to empty
	 * @param size - total amount of objects stored
	 */
	private static void blockRemover(ArrayList<Blocks> x, int size){
		for(int i = size - 1; i>=0; i--)
			x.remove(i);
	}
	/**
	 * removes all objects from the itemBlock arraylist
	 * @param x - arraylist to empty
	 * @param size - total amount of objects stored
	 */
	private static void itemBlockRemover(ArrayList<ItemBlock> x, int size){
		for(int i = size - 1; i>=0; i--)
			x.remove(i);
	}
	/**
	 * removes all objects from platform arraylists
	 * @param x - arraylist to empty
	 * @param size - total amount of objects stored
	 */
	private static void platformRemover(ArrayList<Platform> x, int size){
		for(int i = size - 1; i>=0; i--)
			x.remove(i);
	}
	/**
	 * removes all objects from the fallingBlock arraylist
	 * @param x - arraylist to empty
	 * @param size - total amount of objects stored
	 */
	private static void fallingBlockRemover(ArrayList<FallingBlock> x, int size){
		for(int i = size - 1; i>=0; i--)
			x.remove(i);
	}
	/**
	 * removes all objects from the playerProjectile arraylist
	 * @param x - arraylist to empty
	 * @param size - total amount of objects stored
	 */
	private static void playerProjectileRemover(ArrayList<PlayerProjectile> x, int size){
		for(int i = size - 1; i>=0; i--)
			x.remove(i);
	}
	/**
	 * removes all objects from the walkers arraylist
	 * @param x - arraylist to empty
	 * @param size - total amount of objects stored
	 */
	private static void walkersRemover(ArrayList<RoamingEnemy> x, int size){
		for(int i = size - 1; i>=0; i--)
			x.remove(i);
	}
	/**
	 * removes all objects from the slugs arraylist
	 * @param x - arraylist to empty
	 * @param size - total amount of objects stored
	 */
	private static void slugRemover(ArrayList<Slug> x, int size){
		for(int i = size - 1; i>=0; i--)
			x.remove(i);
	}
	/**
	 * removes all objects from the slimeBalls arraylist
	 * @param x - arraylist to empty
	 * @param size - total amount of objects stored
	 */
	private static void slimeBallRemover(ArrayList<SlugProjectile> x, int size){
		for(int i = size - 1; i>=0; i--)
			x.remove(i);
	}
	public void setKey(char x) {
		key = x;
	}
	
	public char getKey() {
		return key;
	}
	
}