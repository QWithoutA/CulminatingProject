package QWithoutA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class SaltGamePanel  extends JPanel implements Runnable, KeyListener {
	int width = 1100;
	int height = 550;
	
	//number of blocks in main menu
	int blockNum = 63;
	
	//an arraylist of blocks static
	ArrayList<Blocks> block = new ArrayList<Blocks>();
	/**
	 * The pause between repainting (should be set for about 30 frames per
	 * second).
	 */
	final int pauseDuration = 50;
	

	public static void main(String[] args) {

		// Set up main window (using Swing's Jframe)
		JFrame frame = new JFrame("SaltMan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1100, 550));
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
		
		block.add(new Blocks(150, 200, 0, width, 0, height));
		block.add(new Blocks(300, 200, 0, width, 0, height));

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
			
			for (int i = 0; i < block.size(); i++) {
			   block.get(i).draw(g);
			   
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
