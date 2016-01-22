package QWithoutA;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entities.Player;

public class Blocks extends MovingObject{

	private int width = 35;
	private int height = 35;
	
	private BufferedImage image;
	public Blocks(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left + 10, right - 10, top + 10, bottom - 10);
		
		try{
			image = ImageIO.read(Player.class.getResourceAsStream("/Images/Regular Block.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		int drawX = (int) getX();
		int drawY = (int) getY();
//		g.fillRect(drawX, drawY, width, height);
		g.drawImage(image, drawX, drawY, this.width, this.height, null);
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

	public boolean checkStandingCollision(Player entity){
		//If the player touches a block hitbox on top
//		if(entity.getX() > this.getX()+ this.getWidth()){ //right side of block
//			if(entity.getX() > this.getX() + this.getWidth()){
//				return false;
//			}
//			// top side of block
//			else if(entity.getY() + entity.getHeight() > this.getY()){
//				return false;
//			}
//			// bottom side of block
//			else if(entity.getY() < this.getY() + this.getHeight()){
//				return false;
//			}
//		}	
//		else if(entity.getX() + entity.getWidth() < this.getX()){//left side of block
//			if(entity.getX() < this.getX() - entity.getWidth()){
//				return false;
//			}
//			// top side of block
//			else if(entity.getY() + entity.getHeight() > this.getY()){
//				return false;
//			}
//			// bottom side of block
//			else if(entity.getY() < this.getY() + this.getHeight()){
//				return false;
//			}
//		}
		// top side of block
		if(entity.getY() < this.getY() - entity.getHeight()){
				return false;
			}
			else if(entity.getX() + entity.getWidth() < this.getX()){
				return false;
			}
			else if(entity.getY() + entity.getHeight() > this.getY()){
				return false;
			}
			else if(entity.getX() > this.getX() + this.getWidth()){
				return false;
		}
		// bottom side of block
//		else if(entity.getY() > this.getY() + this.getHeight()){
//			if(entity.getX() + entity.getHeight() < this.getX()){
//				return false;
//			}
//			else if(entity.getX() < this.getX() - entity.getWidth()){
//				return false;
//			}
//			else if(entity.getY() > this.getY() + entity.getHeight()){
//				return false;
//			}
//		}
		return true;
	}

	public boolean checkBreakingCollision(Player entity){
		//If the player touches a block hitbox below
		if(entity.getX() >= this.getX()+ this.getWidth()){ //entity on right side of block
			if(entity.getX() > this.getX() + this.getWidth()){
				return false;
			}
			else if(entity.getY() < this.getY() + this.getHeight()){
				return false;
			}
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
		}	
		else if(entity.getX() + entity.getWidth() <= this.getX()){// left side of block
			if(entity.getX() < this.getX() - entity.getWidth()){
				return false;
			}
			else if(entity.getY() < this.getY() + this.getHeight()){
				return false;
			}
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
		}
		// top side of block
		else if(entity.getY() + entity.getHeight() < this.getY()){
			if(entity.getX() > this.getX() + this.getWidth()){
				return false;
			}
			else if(entity.getX() < this.getX() - entity.getWidth()){
				return false;
			}
			else if(entity.getY() < this.getY() - entity.getHeight()){
				return false;
			}
		}
		// bottom side of block
		else if(entity.getY() > this.getY() + this.getHeight()){
			return false;
		}
		return true;
	}
	public boolean checkSideCollision(Player entity){
		//If the player touches a block hitbox on the sides
		if(entity.getX() >= this.getX()+ this.getWidth()){ //entity on right side of block
			if(entity.getX() > this.getX() + this.getWidth()){
				return false;
			}
			// top side of block
			else if(entity.getY() + entity.getHeight() > this.getY()){
				return false;
			}
			// bottom side of block
			else if(entity.getY() < this.getY() + this.getHeight()){
				return false;
			}
		}	
		else if(entity.getX() + entity.getWidth() <= this.getX()){// left side of block
			if(entity.getX() < this.getX() - entity.getWidth()){
				return false;
			}
			// top side of block
			else if(entity.getY() + entity.getHeight() > this.getY()){
				return false;
			}
			// bottom side of block
			else if(entity.getY() < this.getY() + this.getHeight()){
				return false;
			}
		}
		// top side of block
		else if(entity.getY() + entity.getHeight() < this.getY()){
			if(entity.getX() > this.getX() + this.getWidth()){
				return false;
			}
			else if(entity.getX() < this.getX() - entity.getWidth()){
				return false;
			}
			else if(entity.getY() < this.getY() - entity.getHeight()){
				return false;
			}
		}
		// bottom side of block
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