package QWithoutA;

import java.awt.Graphics;
//Ground class serves as bottom platform on which the player character will be by default; separate from ordinary blocks

public class Ground extends NormalBlock{

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
<<<<<<< HEAD
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
<<<<<<< HEAD
	
	public void setWidth() {
		this.width = width;
	}
	
	public void setHeight() {
		this.height = height;
	}
=======

>>>>>>> refs/remotes/origin/master

=======
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
>>>>>>> refs/remotes/origin/su
}