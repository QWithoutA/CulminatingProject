package QWithoutA;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class SaltGamePanel  extends JPanel implements Runnable, KeyListener {
	int width = 1910;
	int height = 1910;
	
	//5 for now but add more later 
	final int blockNum = 5;
	
	//an array of blocks
	Blocks[] block = new Blocks[blockNum];
	

	public static void main(String[] args) {

		// Set up main window (using Swing's Jframe)
		JFrame frame = new JFrame("SaltMan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1910, 1910));
		frame.setAutoRequestFocus(false);
		frame.setVisible(true);
		Container c = frame.getContentPane();
		c.add(new SaltGamePanel());
		frame.pack();
	}

	public SaltGamePanel(){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.WHITE);
		
		block[0] = new Blocks(0, 200, 0, width, 0, height);
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
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}
}
