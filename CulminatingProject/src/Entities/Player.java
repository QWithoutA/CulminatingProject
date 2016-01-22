/**
 * 
 */
package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

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
	}

	/* (non-Javadoc)
	 * @see QWithoutA.MovingObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

		int drawX = (int) getX();
		int drawY = (int) getY();
		g.setColor(Color.YELLOW);
		g.fillRect(drawX ,drawY, this.width, this.height);
		g.setColor(Color.BLACK);
		g.drawRect(drawX ,drawY, this.width, this.height);
		
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
			jumpCounter ++;
		}
		if(jumpCounter > 20){
			jumpCounter = 0;
		}
	}
	
	public void setHeight(int x) {
		// TODO Auto-generated metdhod stub
		this.height = x;
	}
	
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}
	
	public void setWidth(int x) {
		// TODO Auto-generated method stub
		this.width = x;
	}
	
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}
	public void setJumpCount(int x) {
		// TODO Auto-generated method stub
		this.jumpCounter = x;
	}
	public int getJumpCount() {
		// TODO Auto-generated method stub
		return this.jumpCounter;
	}
	
	public void setCrouching(boolean x){
		isDown = x;	
	}
	
	public boolean isCrouching(){
		return isDown;
	}
	
	public void setJumping(boolean x){
		isJumping = x;	
	}
	
	public boolean isJumping(){
		return isJumping;
	}
	
}
