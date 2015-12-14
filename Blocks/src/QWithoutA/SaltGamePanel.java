package QWithoutA;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class SaltGamePanel  extends JPanel implements Runnable, KeyListener {
	int width = 1900;
	int height = 1900;

	public static void main(String[] args) {

		// Set up main window (using Swing's Jframe)
		JFrame frame = new JFrame("SaltMan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(500, 300));
		frame.setAutoRequestFocus(false);
		frame.setVisible(true);
		Container c = frame.getContentPane();
		c.add(new SaltGamePanel());
		frame.pack();
	}

	public SaltGamePanel(){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.BLACK);
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
