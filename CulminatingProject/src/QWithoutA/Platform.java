package QWithoutA;

/**
 * this is the platform class, it creates a new platform
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entities.Player;

public class Platform extends MovingObject{

	private int width = 100;
	private int height = 5;

	private BufferedImage image;
	public Platform(double x, double y, int left, int right, int top, int bottom) {																				
		super(x, y, left + 10, right - 10, top + 10, bottom - 10);
		try{
			image = ImageIO.read(Player.class.getResourceAsStream("/Images/Platform Block.png"));
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
//		g.fillRect(drawX, drawY, width, height);

	}

	@Override
	public void animateOneStep() {
		// TODO Auto-generated method stub
		
	}
	
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	/**
	 * check if the player is on the platform
	 */
	public boolean checkStandingCollision(Player entity){
		//If the player touches a block hitbox on top
		if(entity.getX() > this.getX()+ this.getWidth()){ //right side of hitbox			
			if(entity.getX() + entity.getWidth() < this.getX()){
				return false;
			}
			// top side of hitbox
			else if(entity.getY() + entity.getHeight() < this.getY() - 5){
				return false;
			}
			// bottom side of hitbox
			else if(entity.getY() + entity.getHeight() > this.getY() + 5){
				return false;
			}
			else if(entity.getX() > this.getX()+ this.getWidth()){
				return false;
			}
		}	
		else if(entity.getX() + entity.getWidth() < this.getX()){//left side of hitbox			
			if(entity.getX() < this.getX() - entity.getWidth()){
				return false;
			}
			// top side of hitbox
			else if(entity.getY() + entity.getHeight() < this.getY() - 5){
				return false;
			}
			// bottom side of hitbox
			else if(entity.getY() + entity.getHeight() > this.getY() + 5){
				return false;
			}
			else if(entity.getX() + entity.getWidth() < this.getX()){
				return false;
			}
		}
		// top side of hitbox
		else if(entity.getY() + entity.getHeight() < this.getY() - 5){				
			if(entity.getX() + entity.getWidth() < this.getX()){
				return false;
			}
			else if(entity.getY() + entity.getHeight() < this.getY() - 5){
				return false;
			}
			else if(entity.getY() + entity.getHeight() > this.getY() + this.getHeight()/2){
				return false;
			}
			else if(entity.getX() > this.getX()+ this.getWidth()){
				return false;
			}
		}
		// bottom side of hitbox
		else if(entity.getY() + entity.getHeight() > this.getY() + 5){
			if(entity.getX() + entity.getWidth() < this.getX()){
				return false;
			}
			else if(entity.getX() < this.getX() - entity.getWidth()){
				return false;
			}
			else if(entity.getY() + entity.getHeight() < this.getY() - 5){
				return false;
			}
			else if(entity.getY() + entity.getHeight() > this.getY() + 5){
				return false;
			}
		}
		return true;
	}
}