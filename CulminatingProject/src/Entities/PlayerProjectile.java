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

	//
	public double constantOfGravity = 1.0198, initialAcceleration = 1.98;
	
    private static int radiusSetter = 6;
	private int radius;
	private int counter;
	private boolean isDecayed = false;
	private boolean isBouncing = false;

	private boolean Bounced = false;
	
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
		
		g.setColor(Color.WHITE);
		g.fillOval(drawX, drawY, getRadius() * 2, getRadius() * 2);
		g.setColor(color);
		g.drawOval(drawX ,drawY, getRadius() * 2, getRadius() * 2);
		
	}

	@Override
	public void animateOneStep() {
		// TODO Auto-generated method stub
		if(Bounced){
			counter++;
			Bounced = false;
		}
		if(counter > 4){
			isDecayed = true;
		}
	}
	
	public boolean isDecayed() {
		return isDecayed;		
	}
	public void setProjectileAcceleration(double x) {
		initialAcceleration = x;
	}
	public double getProjectileAcceleration() {
		return initialAcceleration;
	}
	public void setGravityConstant(double x) {
		constantOfGravity = x;
	}
	public double getGravityConstant() {
		return constantOfGravity;
	}
	public void setBouncing(boolean x) {
		isBouncing = x;
	}
	public boolean getBouncing(){
		return isBouncing;
	}
	public void setHasBounced(boolean x) {
		Bounced = x;
	}
	public boolean getHasBounced(){
		return Bounced;
	}
}
