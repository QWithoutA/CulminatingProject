package QWithoutA;

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
}