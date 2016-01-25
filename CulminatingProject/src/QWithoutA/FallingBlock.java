package QWithoutA;

import java.awt.Graphics;
import Entities.Player;

public class FallingBlock extends Blocks{

	private int width = 35;
	private int height = 35;
	private boolean isFalling;
	private int countFalling;
	public FallingBlock(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left, right, top, bottom);
		countFalling = 0;
		isFalling = false;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		int drawX = (int) getX();
		int drawY = (int) getY();
		g.fillRect(drawX, drawY, width, height);
	}

	@Override
	public void animateOneStep() {
		// TODO Auto-generated method stub
		if(isFalling){
			countFalling++;
		}
		if(countFalling > 100){
			setFalling(false);
		}
	}
	
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public void setFalling(boolean x){
		this.isFalling = x;
	}
	public boolean isFalling(){
		return isFalling;
	}
	public void setCountFalling(int x){
		countFalling = x;
	}
	public int getCountFalling(){
		return countFalling;
	}
	/**
	 * check if the player is on the falling block
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
			else if(entity.getY() + entity.getHeight() > this.getY() + this.getHeight()/2){
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
			else if(entity.getY() + entity.getHeight() > this.getY() + this.getHeight()/2){
				return false;
			}
			else if(entity.getX() + entity.getWidth() < this.getX()){
				return false;
			}
		}
		// top side of hitbox
		else if(entity.getY() + entity.getHeight() < this.getY() + 5){  
			if(entity.getX() + entity.getWidth() < this.getX()){
				return false;
			}
			else if(entity.getX() > this.getX() + this.getWidth()){
				return false;
			}
			else if(entity.getY() + entity.getHeight() > this.getY() + this.getHeight()/2){
				return false;
			}
			else if(entity.getY() + entity.getHeight() < this.getY() + 5){
				return false;
			}
		}
		// bottom side of hitbox
		else if(entity.getY() + entity.getHeight() > this.getY() + this.getHeight()/2){
			if(entity.getX() + entity.getHeight() < this.getX()){
				return false;
			}
			else if(entity.getX() < this.getX() - entity.getWidth()){
				return false;
			}
			else if(entity.getY() + entity.getHeight() < this.getY() - 5){
				return false;
			}
			else if(entity.getY() + entity.getHeight() > this.getY() + this.getHeight()/2){
				return false;
			}
		}
		return true;
	}
}


