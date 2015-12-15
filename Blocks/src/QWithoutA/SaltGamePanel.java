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
	
	//5 for now but add more later 
	final int blockNum = 5;
	
	//an array of blocks
	Blocks[] block = new Blocks[blockNum];
	
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
		frame.setLayout(new BorderLayout());
		Container c = frame.getContentPane();
		c.add(new SaltGamePanel());
		frame.setResizable(false);
		frame.pack();
	}

	public SaltGamePanel(){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.WHITE);
		for (int i=0; i<blockNum; i++) {
			block[i] = new Blocks(0, 0, 0, width, 0, height);
		}
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
		for (int i = 0; i < blockNum; i++) {
			block[i].draw(g);
			g.setColor(Color.BLACK);
		}
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
