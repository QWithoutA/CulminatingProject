/**
 * 
 */
package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import QWithoutA.MovingObject;
import QWithoutA.SaltGamePanel;


/**
 * 
 * 
 * @author Glen Su
 *	Jan 01, 2015
 */
public class Player extends MovingObject{

	/**
	 * Hitbox for the player model
	 */
	private int width;
	final public int  initialHeight = 70;
	private int height;
	
	private boolean isDown;
	private boolean isJumping;
	private int jumpCounter;
	/**
	 * Contains the image used for the player
	 */
	private BufferedImage image;
	/**
	 * @param x
	 * @param y
	 * @param left
	 * @param right
	 * @param top
	 * @param bottom
	 */
	public Player(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left, right, top, bottom);
		// TODO Auto-generated constructor stub
		setHeight(initialHeight);
		setWidth(35);
		isDown = false;
		isJumping = false;
		jumpCounter = 0;
		
		try{
			image = ImageIO.read(Player.class.getResourceAsStream("/Images/Saltman.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see QWithoutA.MovingObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

		int drawX = (int) getX();
		int drawY = (int) getY();
		g.drawImage(image, drawX, drawY, this.width, this.height, null);
//		g.setColor(Color.YELLOW);
//		g.fillRect(drawX ,drawY, this.width, this.height);
//		g.setColor(Color.BLACK);
//		g.drawRect(drawX ,drawY, this.width, this.height);
	}

	/* (non-Javadoc)
	 * @see QWithoutA.MovingObject#animateOneStep()
	 */
	@Override
	public void animateOneStep() {
		// TODO Auto-generated method stub
		if(isJumping){
			jumpCounter++;
		}
		if(jumpCounter > 10){
//			jumpCounter ++;
		}
		if(jumpCounter > 20){
			jumpCounter = 0;
		}
	}/**
	 * this sets the widheightth of the palyer
	 * @return
	 */
	
	public void setHeight(int x) {
		// TODO Auto-generated metdhod stub
		this.height = x;
	}
	/**
	 * this gets the height of the palyer
	 * @return
	 */
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}
	
	/**
	 * this sets the width of the palyer
	 * @return
	 */
	public void setWidth(int x) {
		// TODO Auto-generated method stub
		this.width = x;
	}
	
	/**
	 * this gets the width of the palyer
	 * @return
	 */
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}
	/**
	 * this gets the jump count for how long the player is in the air for
	 * @return
	 */
	public int getJumpCount() {
		// TODO Auto-generated method stub
		return this.jumpCounter;
	}
	
	/**
	 * this sets the crouching 
	 * @return
	 */
	public void setCrouching(boolean x){
		isDown = x;	
	}
	
	/**
	 * this gets the crouching to test if the player is crouching 
	 * @return
	 */
	public boolean isCrouching(){
		return isDown;
	}
	
	/**
	 * this sets the jump 
	 * @return
	 */
	public void setJumping(boolean x){
		isJumping = x;	
	}
	/**
	 * this gets the jump for if the palyer is jumping 
	 * @return
	 */
	public boolean isJumping(){
		return isJumping;
	}
	
}
