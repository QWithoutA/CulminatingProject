package QWithoutA;

import java.awt.Color;
import java.awt.Graphics;

public class ItemBlock extends Blocks{
	
	private int counter;
	private int flashSpeed;
	private boolean filledIn;

	private int width = 35;
	private int height = 35;
	public ItemBlock(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left + 10, right - 10, top + 10, bottom - 10);
		
		counter = 0;
		flashSpeed = (int) (Math.random() * 5 + 5);
		filledIn = true;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		int drawX = (int) getX();
		int drawY = (int) getY();
		
	
		g.fillRect(drawX, drawY, width, height);
		if (!filledIn) {
			g.setColor(Color.black);
			g.fillRect(drawX, drawY, width, height);
		}
	}

	@Override
	public void animateOneStep() {
	/*	counter++;
		if (counter == flashSpeed) {
			counter = 0;
			if (filledIn)
				filledIn = false;
			else
				filledIn = true;
		}
		if (getX() >= getRight() | getX() <= getLeft()){
			setX(1100);
			setY(550);
		}
		*/
		
	}

}