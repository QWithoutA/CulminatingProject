package QWithoutA;

import java.awt.Graphics;
// Ground class serves as bottom platform on which the player character will be by default; separate from ordinary blocks
public class Ground extends Blocks{

	private int width = 1150;
	private int height = 35;
	public Ground(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left, right, top, bottom);
		
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
		
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}


}