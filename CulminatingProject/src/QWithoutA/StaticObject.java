package QWithoutA;

import java.awt.Color;
import java.awt.Graphics;

public abstract class StaticObject implements Runnable {

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
	private int pauseDuration;
	/**
	 * Object color (defaults to black)
	 */
	protected Color color;
	/**
	 * Set to false to stop the thread.
	 */
	private boolean moving;


	public StaticObject(double x, double y, int left, int right, int top, int bottom){
		this.pauseDuration = 40;
		this.color = Color.black;
		this.x = x;
		this.y = y;
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


	
	public double getY() {
		return y;
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
