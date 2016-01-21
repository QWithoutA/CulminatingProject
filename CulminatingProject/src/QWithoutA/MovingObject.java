package QWithoutA;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Player;

public abstract class MovingObject implements Runnable {

	/**
	 * The x location of the object.
	 */
	private double x;
	/**
	 * The y location of the object.
	 */
	private double y;
	/**
	 * The x speed of the object.
	 */
	private double xSpeed;
	/**
	 * The y speed of the object.
	 */
	private double ySpeed;
	/**
	 * The left edge for bouncing.
	 */
	private int left;
	/**
	 * The right edge for bouncing.
	 */
	private int right;
	/**
	 * The top edge for bouncing.
	 */
	private int top;
	/**
	 * The bottom edge for bouncing.
	 */
	private int bottom;
	/**
	 * Length of pause between position updates. Related to speed of object.
	 * (Defaults to 10).
	 */
	private int pauseDuration;
	/**
	 * Object color (defaults to black)
	 */
	protected Color color;
	/**
	 * Set to false to stop the thread.
	 */
	private boolean moving;


	public MovingObject(double x, double y, int left, int right, int top, int bottom){
		this.pauseDuration = 40;
		this.xSpeed = 0;
		this.ySpeed = 0;
		this.color = Color.black;
		this.x = x;
		this.y = y;
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		startThread();
	}

	/**
	 * Starts the movement thread.
	 */
	public void startThread() {
		moving = true;
		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * Stops the movement thread by terminating the main loop in run().
	 */
	public void stopThread() {
		moving = false;
	}
// double check run method later.
	public void run() {
		while (moving) {
			animateOneStep();
			x += xSpeed;
			y += ySpeed;
			if (y >= bottom | y <= top)
				ySpeed *= -1;
			if (x >= right | x <= left)
				xSpeed *= -1;
			try {
				Thread.sleep(pauseDuration);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Draws the object.
	 * 
	 * @param g
	 *       The graphics context
	 */
	abstract public void draw(Graphics g);

	/**
	 * Performs one step of animation.
	 */
	abstract public void animateOneStep();
	
	
	public double getX() {
		return x;
	}

	
	public int getRight(){
		return right;
	}
	

	public int getLeft(){
		return left;
	}

	
	public double getY() {
		return y;
	}
	

	public double getXspeed(){
		return xSpeed;
	}
	

	public double getYspeed(){
		return ySpeed;
	}

	
	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
	

	public void setYSpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}
	


	public void setX(int x) {
		this.x = x;
	}
	

	public void setY(int y) {
		this.y = y;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
