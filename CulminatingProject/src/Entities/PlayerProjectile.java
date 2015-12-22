/**
 * 
 */
package Entities;

import java.awt.Color;
import java.awt.Graphics;

import QWithoutA.MovingObject;
import QWithoutA.SaltGamePanel;

/**
 * 
 * 
 * @author Glen Su 
 *	Dec 16, 2015
 */
public class PlayerProjectile extends MovingObject {

	
	public double gravityConstant = 1.0198, initialAcc = 1.98;
	
    private static int radiusSetter = 6;
	private int radius;
	private int counter;
	private boolean isDecayed = false;
	private boolean isBouncing = false;
	
	public PlayerProjectile(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left + radiusSetter, right - radiusSetter, top + radiusSetter, bottom - radiusSetter);
		// TODO Auto-generated constructor stub
		this.setRadius(radiusSetter);
	}

	private void setRadius(int radius) {
		// TODO Auto-generated method stub
		this.radius = radius;
	}
	
	public int getRadius() {
		// TODO Auto-generated method stub
		return this.radius;
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		int drawX = (int) getX() - (radius);
		int drawY = (int) getY() - (radius);
		
		g.setColor(color);
		g.drawOval(drawX ,drawY, getRadius() * 2, getRadius() * 2);
		g.setColor(Color.WHITE);
		g.fillOval(drawX, drawY, getRadius() * 2, getRadius() * 2);
	}

	@Override
	public void animateOneStep() {
		// TODO Auto-generated method stub
		counter++;
			if(counter > 50){
				isDecayed = true;
			}
	}
	
	public boolean isDecayed() {
		return isDecayed;		
	}
	public void setProjectileAcceleration(double x) {
		initialAcc = x;
	}
	public double getProjectileAcceleration() {
		return initialAcc;
	}
	public void setBouncing(boolean x) {
		isBouncing = x;
	}
	public boolean getIsBouncing(){
		return isBouncing;
	}
}
