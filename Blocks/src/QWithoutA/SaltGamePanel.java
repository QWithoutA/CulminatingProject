package QWithoutA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class SaltGamePanel  extends JPanel implements Runnable, KeyListener {
	int width = 700;
	int height = 700;
	
	//number of blocks in main menu
	int blockNum = 36;
	
	//an array of blocks
	Blocks[] block = new Blocks[blockNum];
	Blocks[] block2 = new Blocks[blockNum];
	/**
	 * The pause between repainting (should be set for about 30 frames per
	 * second).
	 */
	final int pauseDuration = 50;
	

	public static void main(String[] args) {

		// Set up main window (using Swing's Jframe)
		JFrame frame = new JFrame("SaltMan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(700, 700));
		frame.setAutoRequestFocus(false);
		frame.setVisible(true);
		//frame.setLayout(new BorderLayout());
		Container c = frame.getContentPane();
		c.add(new SaltGamePanel());
		frame.setResizable(false);
		frame.pack();
	}

	public SaltGamePanel(){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.WHITE);
		// for loop establishes the block array from which all blocks of the main menu are made
		for (int i=0; i<blockNum; i++) {
			block[i] = new Blocks(0, 0, 0, width, 0, height);
		}	
		block2[0] = new Blocks(0, 0, 0, width, 0, height);
		block2[1] = new Blocks(0, 0, 0, width, 0, height);
		Thread gameThread = new Thread(this);
		gameThread.start();
		
		setFocusable(true);
		addKeyListener(this);

	}
	
	public void run() {
		while (true) {
			repaint();
			try {
				Thread.sleep(pauseDuration);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// for loop draws in the ground of the main menu/first screen
		// loop sets X and Y coordinates
		for (int i = 0; i < blockNum; i++) {
			for (int j = 0; j < blockNum; j++) {
				g.setColor(Color.BLACK);
				block[i].draw(g);
				block[i].setX(i*20);
				block[i].setY(j*20+600);
			}
		}
		// for loop draws in the ceiling of the main menu/t screen
		// loop sets X and Y coordinates
		for (int i = 0; i < blockNum; i++) {
			for (int j = 0; j < blockNum; j++) {
				g.setColor(Color.BLACK);
				block[i].draw(g);
				block[i].setX(i*20);
				block[i].setY(j*20-600);
			}
		}
		// small illustration demonstrating the creation of separate blocks independent of the original array/block declaration
		block2[0].setX(50);
		block2[0].setY(400);
		block2[1].setX(90);
		block2[1].setY(400);
		for (int i=0;i<2;i++)
		block2[i].draw(g); 
		
	}

	
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		/**
		if(e.getKeyCode() == 38){
			paddle[1].setY((int) (paddle[1].getY()-20));
		}
		else if(e.getKeyCode() == 40){
			paddle[1].setY((int) (paddle[1].getY()+20));
		}	
	*/
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
