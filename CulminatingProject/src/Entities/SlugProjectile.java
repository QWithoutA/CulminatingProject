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
 *	Jan 01, 2015
 */
public class SlugProjectile extends MovingObject {

	private static int radiusSetter = 6;
	private int radius;
	private int triangleTip = -15;
	private int triangleSides = 3;
	private int counter = 0;
	private int direction;
	private boolean isDecayed;
	
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
		x = (int) getX() - (radius);
		y = (int) getY() - (radius);
		isDecayed = false;
		
	}

	/* (non-Javadoc)
	 * @see QWithoutA.MovingObject#draw(java.awt.Graphics)
	 */
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		 xCoords[0] = (int) (getX() + radius);
		 xCoords[1] = (int) (getX() + radius);
		 xCoords[2] = (int) (getX() + (radius + triangleTip * direction));
		 
		 yCoords[0] = (int) getY();
		 yCoords[1] = (int) (getY() + (radius*2));
		 yCoords[2] = (int) (getY() + radius);
		g.setColor(new Color((int) (16), (int)  (255), (int) (16)));
		g.fillOval((int) getX(), (int) getY(), getRadius() * 2, getRadius() * 2);
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

	public void setDirection(int x){
		direction = x;
	}
	public boolean checkCollision(Player entity){
		//If the player touches a slug projectile hitbox anywhere for now
		if(entity.getX() >= this.getX() + this.getRadius()){ //entity on right side of slug
			if(entity.getX() > this.getX() + this.getRadius()){
				return false;
			}
			// top side of a slug projectile
			else if(entity.getY() + entity.getHeight() < this.getY()){
				return false;
			}
			// bottom side of a slug projectile
			else if(entity.getY() > this.getY() + this.getRadius()){
				return false;
			}
		}	
		else if(entity.getX() + entity.getWidth() <= this.getX()){// left side of slug
			if(entity.getX() < this.getX() - entity.getWidth()){
				return false;
			}
			// top side of a slug projectile
			else if(entity.getY() + entity.getHeight() < this.getY()){
				return false;
			}
			// bottom side of a slug projectile
			else if(entity.getY() > this.getY() + this.getRadius()){
				return false;
			}
		}
		// top side of a slug projectile
		else if(entity.getY() + entity.getHeight() < this.getY()){
			if(entity.getX() > this.getX() + this.getRadius()){
				return false;
			}
			else if(entity.getX() < this.getX() - entity.getWidth()){
				return false;
			}
			else if(entity.getY() < this.getY() - entity.getHeight()){
				return false;
			}
		}
		// bottom side of a slug projectile
		else if(entity.getY() > this.getY() + this.getRadius()){
			if(entity.getX() + entity.getHeight() < this.getX()){
				return false;
			}
			else if(entity.getX() < this.getX() - entity.getWidth()){
				return false;
			}
			else if(entity.getY() > this.getY() + entity.getHeight()){
				return false;
			}
		}
	
		return true;
	}
}
