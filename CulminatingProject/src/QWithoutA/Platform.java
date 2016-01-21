package QWithoutA;

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
		try {
	        image = ImageIO.read(Player.class.getResourceAsStream("/Images/Platform Block.png"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		int drawX = (int) getX();
		int drawY = (int) getY();
		g.fillRect(drawX, drawY, width, height);
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
}