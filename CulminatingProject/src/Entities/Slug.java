/**
 * 
 */
package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import QWithoutA.MovingObject;
import QWithoutA.SaltGamePanel;

/**
 * @author Glen Su
 * Jan 01, 2015
 */
public class Slug extends MovingObject {
	
	private int width;
	private int height;
	private int shotCounter;
	private boolean isTurning;
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
		shotCounter = 0;
		setHeight(30);
		setWidth(55);
		setShooting(false);
		movingToBoundry(false);
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
		if (shotCounter > 30){
			setShooting(true);
			shotCounter = 0;
		}
		else{
			setShooting(false);
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
	
	public boolean checkCollision(Player entity){
		//If the player touches a slug hitbox anywhere for now
		if(entity.getX() >= this.getX()+ this.getWidth()){ //entity on right side of slug
			if(entity.getX() > this.getX() + this.getWidth()){
				return false;
			}
			// top side of slug
			else if(entity.getY() + entity.getHeight() < this.getY()){
				return false;
			}
			// bottom side of slug
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
		}	
		else if(entity.getX() + entity.getWidth() <= this.getX()){// left side of slug
			if(entity.getX() < this.getX() - entity.getWidth()){
				return false;
			}
			// top side of slug
			else if(entity.getY() + entity.getHeight() < this.getY()){
				return false;
			}
			// bottom side of slug
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
		}
		// top side of slug
		else if(entity.getY() + entity.getHeight() < this.getY()){
			return false;
		}
		// bottom side of slug
		else if(entity.getY() > this.getY() + this.getHeight()){
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
	public static void main(String[] args){
		ArrayList<Slug> slugs = new ArrayList<Slug>();
		slugs.add(new Slug(500, 450, 400, 600, 0 , 1100));
		slugs.get(0).setXSpeed(7);
		ArrayList<Player> player = new ArrayList<Player>();
		player.add(new Player(550, 425, 400, 600, -0, 1100*2));
		if(slugs.get(0).checkCollision(player.get(0))){
			System.out.println("died");
		}
		System.out.println("running");
		System.exit(0);
	}
}
