/**
 * 
 */
package Entities;

import java.awt.Color;
import java.awt.Graphics;

import QWithoutA.MovingObject;

/**
 * @author Glen Su
 *	Dec 20, 2015
 */
public class Player extends MovingObject{

	/**
	 * Hitbox for the player model
	 */
	private int width = 35;
	private int height = 70;
	
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
		setHeight(70);
		setWidth(35);
		
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
		g.fillRect(drawX ,drawY, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(drawX ,drawY, width, height);
	}

	/* (non-Javadoc)
	 * @see QWithoutA.MovingObject#animateOneStep()
	 */
	@Override
	public void animateOneStep() {
		// TODO Auto-generated method stub

	}
	
	public void setHeight(int x) {
		// TODO Auto-generated method stub
		height = x;
	}
	
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}
	
	public void setWidth(int x) {
		// TODO Auto-generated method stub
		width = x;
	}
	
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}
}
