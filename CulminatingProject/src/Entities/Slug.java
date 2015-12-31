/**
 * 
 */
package Entities;

import java.awt.Color;
import java.awt.Graphics;

import QWithoutA.MovingObject;

/**
 * @author Jenny-Dell
 *
 */
public class Slug extends MovingObject {
	
	private int width;
	private int height;
	private boolean isTurning;
	private int paceCounter;
	private int shotCounter;
	private boolean isShooting;
	/**
	 * @param x
	 * @param y
	 * @param left
	 * @param right
	 * @param top
	 * @param bottom
	 */
	public Slug(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left, right, top, bottom);
		// TODO Auto-generated constructor stub
		isTurning = false;
		paceCounter = 0;
		shotCounter = 0;
		setHeight(30);
		setWidth(55);
		setShooting(true);
	}

	/* (non-Javadoc)
	 * @see QWithoutA.MovingObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		int drawX = (int) getX();
		int drawY = (int) getY();
		
		g.setColor(Color.GREEN);
		g.fillRect(drawX, drawY, width, height);
	}

	/* (non-Javadoc)
	 * @see QWithoutA.MovingObject#animateOneStep()
	 */
	@Override
	public void animateOneStep() {
		// TODO Auto-generated method stub
		shotCounter++;
		paceCounter++;
		if (shotCounter > 60){
			setShooting(true);
			shotCounter = 0;
		}
		if(paceCounter > 100){
			movingToBoundry(true);
			paceCounter = 0;
		}
	}
	public void setWidth(int x){
		width = x;
	}
	public int getWidth(){
		return width;
	}
	public void setHeight(int x){
		height = x;
	}
	public int getHeight(){
		return height;
	}
	
	public void movingToBoundry(boolean x){
		isTurning = x;
	}
	public boolean hitBoundry(){
		return isTurning;
	}
	
	public void setShooting(boolean x){
		isShooting = x;
	}
	public boolean isShooting(){
		return isShooting;
	}
}
