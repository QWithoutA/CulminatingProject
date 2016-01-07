/**
 * 
 */
package Entities;

import java.awt.Graphics;

import java.awt.Color;
import java.awt.Graphics;

import QWithoutA.MovingObject;

/**
 * 
 * @author Glen Su
 *  Jan 01, 2015
 */
public class RoamingEnemy extends MovingObject {
	
	private int width;
	private int height;
	private static int paceCounter;
	private static boolean isTurning;
	
	public RoamingEnemy(double x, double y, int left, int right, int top, int bottom) {
		super(x, y, left, right, top, bottom);
		// TODO Auto-generated constructor stub
		paceCounter = 0;
		setHeight(30);
		setWidth(35);
		movingToBoundry(false);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		int drawX = (int) getX();
		int drawY = (int) getY();
		
		g.setColor(Color.RED);
		g.fillRect(drawX, drawY, width, height);
	}

	@Override
	public void animateOneStep() {
		// TODO Auto-generated method stub
		if(paceCounter > 100){
			movingToBoundry(true);
			paceCounter = 0;
		}
		else{
			
		}
	}

	public void setWidth(int x){
		width = x;
	}
	public int getWidth(){
		return width;
	}
	public void setHeight(int x){
		height = x;
	}
	public int getHeight(){
		return height;
	}
	
	public void movingToBoundry(boolean x){
		isTurning = x;
	}
	public boolean hitBoundry(){
		return isTurning;
	}
	
}
