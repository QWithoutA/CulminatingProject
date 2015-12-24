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
    
    private static int radiusSetter = 5;
	private int radius;
	private int counter;
	private boolean isDecayed = false;
	
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
		g.drawOval(drawX ,drawY, getRadius()*2, getRadius()*2);
		g.setColor(Color.WHITE);
		g.fillOval(drawX, drawY, radius * 2, radius * 2);
	}

	@Override
	public void animateOneStep() {
		// TODO Auto-generated method stub
		counter++;
			if(counter > 500){
				
			}
	}
	
	public boolean isDecayed() {
		return isDecayed = true;		
	}
}
