/**
 * 
 */
package Entities;

import java.awt.Color;
import java.awt.Graphics;

import QWithoutA.MovingObject;

/**
 * 
 * 
 * @author Glen Su
 *	Dec 16, 2015
 */
public class SlugProjectile extends MovingObject {

	private static int radiusSetter = 6;
	private int radius;
	private static int triangleTip = -15;
	private int triangleSides = 3;
	private int counter = 0;
	private static int direction;
	private boolean isDecayed;
	
	int drawX;
	int drawY;
	
	int[] xCoords = new int[3];
	int[] yCoords = new int[3];
	
	/**
	 * @param x
	 * @param y
	 * @param left
	 * @param right
	 * @param top
	 * @param bottom
	 */
	public SlugProjectile(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left + radiusSetter, right - radiusSetter, top + radiusSetter, bottom - radiusSetter);
		// TODO Auto-generated constructor stub
		this.setRadius(radiusSetter);
		drawX = (int) getX() - (radius);
		drawY = (int) getY() - (radius);
		
		 xCoords[0] = drawX + radius;
		 xCoords[1] = drawX + radius;
		 xCoords[2] = drawX + radius + triangleTip * direction;
		 
		 yCoords[0] = drawY;
		 yCoords[1] = drawY + (radius*2);
		 yCoords[2] = drawY + radius;
	}

	/* (non-Javadoc)
	 * @see QWithoutA.MovingObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		g.setColor(new Color((int) (16), (int)  (255), (int) (16)));
		g.fillOval(drawX, drawY, getRadius() * 2, getRadius() * 2);
		g.fillPolygon(xCoords, yCoords, triangleSides);
		
//		g.setColor(color);
//		g.drawPolygon(xCoords, yCoords, triangleSides);
//		g.drawOval(drawX ,drawY, getRadius() * 2, getRadius() * 2);

	}

	/* (non-Javadoc)
	 * @see QWithoutA.MovingObject#animateOneStep()
	 */
	@Override
	public void animateOneStep() {
		// TODO Auto-generated method stub
		counter++;
		if(counter > 100){
			setDecayed(true);
		}
	}

	private void setRadius(int radius) {
		// TODO Auto-generated method stub
		this.radius = radius;
	}

	public int getRadius() {
		// TODO Auto-generated method stub
		return this.radius;
	}

	/**
	 * @return the isDecayed
	 */
	public boolean isDecayed() {
		return isDecayed;
	}

	/**
	 * @param isDecayed the isDecayed to set
	 */
	public void setDecayed(boolean isDecayed) {
		this.isDecayed = isDecayed;
	}

	public static void setDirection(int x){
		direction = x;
	}
}
