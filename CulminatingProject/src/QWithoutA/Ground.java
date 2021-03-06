package QWithoutA;
/**
 * this is the ground class, it creates a new ground
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entities.Player;
// Ground class serves as bottom platform on which the player character will be by default; separate from ordinary blocks
public class Ground extends Blocks{

	private int width = 1150;
	private int height = 35;
	private BufferedImage image;

	public Ground(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left, right, top, bottom);

		try{
			image = ImageIO.read(Player.class.getResourceAsStream("/Images/Terrain Block.png"));
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
	public int getHeight(){
		return height;
	}
	public void setHeight(int x){
		this.height = x;
	}
	
	public int getWidth(){
		return width;
	}
	public void setWidth(int x){
		this.width = x;
	}
	/**
	 * check if the player is on the ground
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
			else if(entity.getY() + entity.getHeight() > this.getY() + this.getHeight()){
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
			else if(entity.getY() + entity.getHeight() > this.getY() + this.getHeight()){
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
			else if(entity.getY() + entity.getHeight() > this.getY() + this.getHeight()){
				return false;
			}
			else if(entity.getY() + entity.getHeight() < this.getY() + 5){
				return false;
			}
		}
		// bottom side of hitbox
		else if(entity.getY() + entity.getHeight() > this.getY() + this.getHeight()){
			if(entity.getX() + entity.getHeight() < this.getX()){
				return false;
			}
			else if(entity.getX() < this.getX() - entity.getWidth()){
				return false;
			}
			else if(entity.getY() + entity.getHeight() < this.getY() - 5){
				return false;
			}
			else if(entity.getY() + entity.getHeight() > this.getY() + this.getHeight()){
				return false;
			}
		}
		return true;
	}
	/**
	 * check if the player is touching underneath the ground
	 */
	public boolean checkBottomCollision(Player entity){
		//If the player touches a block hitbox below
		if(entity.getX() > this.getX()+ this.getWidth()){ //right side of hitbox
			if(entity.getX() + entity.getWidth() < this.getX()){
				return false;
			}
			// bottom side of hitbox
			else if(entity.getY() > this.getY() + this.getHeight()+10){
				return false;
			}
			// top side of hitbox
			else if(entity.getY() < this.getY() + this.getHeight()){
				return false;
			}
			else if(entity.getX() > this.getX()+ this.getWidth()){
				return false;
			}
		}	
		else if(entity.getX() + entity.getWidth() < this.getX()){//left side of hitbox
			if(entity.getX() > this.getX() + this.getWidth()){
				return false;
			}
			// bottom side of hitbox
			else if(entity.getY() > this.getY() + this.getHeight()+10){
				return false;
			}
			// top side of hitbox
			else if(entity.getY() < this.getY() + this.getHeight()){
				return false;
			}
			else if(entity.getX() + entity.getWidth() < this.getX()){
				return false;
			}
		}
		// bottom side of hitbox
		else if(entity.getY() > this.getY() + this.getHeight()+10){		
			if(entity.getY() < this.getY() + this.getHeight()){
				return false;
			}
			else if(entity.getY() > this.getY() + this.getHeight()+10){
				return false;
			}
			else if(entity.getX() + entity.getWidth() < this.getX()){
				return false;
			}
			else if(entity.getX() > this.getX() + this.getWidth()){
				return false;
			}
		}
		// top side of hitbox
		else if(entity.getY() < this.getY() + this.getHeight()){
			if(entity.getX() + entity.getWidth() < this.getX()){
				return false;
			}
			else if(entity.getX() > this.getX() + this.getWidth()){
				return false;
			}
			else if(entity.getY() > this.getY() + this.getHeight()+10){
				return false;
			}
			else if(entity.getY() < this.getY() + this.getHeight()){
				return false;
			}
		}
		return true;
	}
	/**
	 * check if the player is touching the left side of the ground
	 */
	public boolean checkLeftSideCollision(Player entity){
		//If the player touches a block hitbox on the left side
		if(entity.getX() + entity.getWidth() > this.getX() + 5){ //right side of hitbox
			if(entity.getX() + entity.getWidth() < this.getX() - 5){
				return false;
			}
			// top side of hitbox
			else if(entity.getY() + entity.getHeight() < this.getY() + 5){
				return false;
			}
			// bottom side of hitbox
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
			else if(entity.getX() + entity.getWidth() > this.getX() +5){
				return false;
			}
		}	
		else if(entity.getX() + entity.getWidth() < this.getX() - 5){//left side of hitbox
			if(entity.getX() + entity.getWidth() > this.getX() + 5){
				return false;
			}
			// top side of hitbox
			else if(entity.getY() + entity.getHeight() < this.getY() + 5){
				return false;
			}
			// bottom side of hitbox
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
			else if(entity.getX() + entity.getWidth() < this.getX() - 5){
				return false;
			}
		}
		// top side of hitbox
		else if(entity.getY() + entity.getHeight() < this.getY() + 5){		
			if(entity.getX() + entity.getWidth() < this.getX() - 5){
				return false;
			}
			else if(entity.getX() + entity.getWidth() > this.getX() + 5){
				return false;
			}
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
			else if(entity.getY() + entity.getHeight() < this.getY() + 5){
				return false;
			}
		}
		// bottom side of hitbox
		else if(entity.getY() > this.getY() + this.getHeight()){
			if(entity.getX() + entity.getWidth() < this.getX() - 5){
				return false;
			}
			else if(entity.getX() + entity.getWidth() > this.getX() + 5){
				return false;
			}
			else if(entity.getY() + entity.getHeight() < this.getY() + 5){
				return false;
			}
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
		}
		return true;
	}
	/**
	 * check if the player is touching the right side of the ground
	 */
	public boolean checkRightSideCollision(Player entity){
		//If the player touches a block hitbox on the right side
		if(entity.getX() > this.getX() + this.getWidth() + 5){ //right side of hitbox
			if(entity.getX() < this.getX() + this.getWidth() - 5){
				return false;
			}
			// top side of hitbox
			else if(entity.getY() + entity.getHeight() < this.getY() + 5){
				return false;
			}
			// bottom side of hitbox
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
			else if(entity.getX() + entity.getWidth() > this.getX() + 5){
				return false;
			}
		}	
		else if(entity.getX() < this.getX() + this.getWidth() - 5){//left side of hitbox
			if(entity.getX() > this.getX() + this.getWidth() + 5){
				return false;
			}
			// top side of hitbox
			else if(entity.getY() + entity.getHeight() < this.getY() + 5){
				return false;
			}
			// bottom side of hitbox
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
			else if(entity.getX() < this.getX() + this.getWidth() - 5){
				return false;
			}
		}
		// top side of hitbox
		else if(entity.getY() + entity.getHeight() < this.getY() + 5){		
			if(entity.getX() < this.getX() + this.getWidth() - 5){
				return false;
			}
			else if(entity.getX() > this.getX() + this.getWidth() + 5){
				return false;
			}
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
			else if(entity.getY() + entity.getHeight() < this.getY() + 5){
				return false;
			}
		}
		// bottom side of hitbox
		else if(entity.getY() > this.getY() + this.getHeight()){
			if(entity.getX() < this.getX() + this.getWidth() - 5){
				return false;
			}
			else if(entity.getX() > this.getX() + this.getWidth() + 5){
				return false;
			}
			else if(entity.getY() + entity.getHeight() < this.getY() + 5){
				return false;
			}
			else if(entity.getY() > this.getY() + this.getHeight()){
				return false;
			}
		}
		return true;
	}
}