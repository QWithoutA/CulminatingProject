/**
 * 
 */
package Entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics;

import QWithoutA.MovingObject;

/**
 * 
 * @author Glen Su
 *  Jan 01, 2015
 */
public class RoamingEnemy extends MovingObject {
	
	private int width;
	private int height;
	private int paceCounter;
	private boolean isTurning;
	private BufferedImage image;
	public RoamingEnemy(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left, right, top, bottom);
		// TODO Auto-generated constructor stub
		paceCounter = 0;
		setHeight(30);
		setWidth(35);
		movingToBoundry(false);
		try{
			image = ImageIO.read(Player.class.getResourceAsStream("/Images/Regular Enemy.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		int drawX = (int) getX();
		int drawY = (int) getY();
		g.drawImage(image, drawX, drawY, this.width, this.height, null);
//		g.setColor(Color.RED);
//		g.fillRect(drawX, drawY, width, height);
	}

	@Override
	public void animateOneStep() {
		// TODO Auto-generated method stub
		if(paceCounter > 100){
			movingToBoundry(true);
			paceCounter = 0;
		}
		else{
			movingToBoundry(false);
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
	public boolean checkCollision(Player entity){
		//If the player touches a slug hitbox anywhere for now
		if(entity.getX() >= this.getX() + this.getWidth()){ //entity on right side of slug
			if(entity.getX() > this.getX() + this.getWidth()){
				return false;
			}
			// top side of a roaming enemy
			else if(entity.getY() + entity.getHeight() < this.getY()){
				return false;
			}
			// bottom side of a roaming enemy
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
		}	
		else if(entity.getX() + entity.getWidth() <= this.getX()){// left side of slug
			if(entity.getX() < this.getX() - entity.getWidth()){
				return false;
			}
			// top side of a roaming enemy
			else if(entity.getY() + entity.getHeight() < this.getY()){
				return false;
			}
			// bottom side of a roaming enemy
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
		}
		// top side of a roaming enemy
		else if(entity.getY() + entity.getHeight() < this.getY()){
			if(entity.getX() > this.getX() + this.getWidth()){
				return false;
			}
			else if(entity.getX() > this.getX() - entity.getWidth()){
				return false;
			}
			else if(entity.getY() < this.getY() - entity.getHeight()){
				return false;
			}
		}
		// bottom side of a roaming enemy
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
}
