<<<<<<< HEAD
<<<<<<< HEAD
package QWithoutA;

import java.awt.Color;
import java.awt.Graphics;

public class FallingBlock extends NormalBlock{

	private int width = 25;
	private int height = 25;
	public FallingBlock(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left + 10, right - 10, top + 10, bottom - 10);
		
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
	
	
	
}
=======


package QWithoutA;

import java.awt.Graphics;

public class FallingBlock extends Blocks{

	private int width = 25;
	private int height = 25;
	public FallingBlock(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left + 10, right - 10, top + 10, bottom - 10);
		
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
}


>>>>>>> refs/remotes/origin/master
=======


package QWithoutA;

import java.awt.Graphics;

public class FallingBlock extends Blocks{

	private int width = 25;
	private int height = 25;
	public FallingBlock(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left + 10, right - 10, top + 10, bottom - 10);
		
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
}


>>>>>>> refs/remotes/origin/su
