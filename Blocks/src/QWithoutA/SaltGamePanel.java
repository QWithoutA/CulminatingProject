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
	
	//an arraylist of blocks
	ArrayList<NormalBlock> block = new ArrayList<NormalBlock>();
	//an arraylist of the ground (can also make hills)
	ArrayList<Ground> ground = new ArrayList<Ground>();
	//an arraylist of itemblocks
	ArrayList<ItemBlock> iBlock = new ArrayList<ItemBlock>();
	//an arraylist of movingplatforms 
	ArrayList<MovingPlatform> mPlat = new ArrayList<MovingPlatform>();

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
		this.setBackground(Color.CYAN);
		
		ground.add(new Ground(0, 525, 0, width, 0, height));
		
		iBlock.add(new ItemBlock(250, 300, 0, width, 0, height));
		
		block.add(new NormalBlock(150, 300, 0, width, 0, height));
		block.add(new NormalBlock(350, 300, 0, width, 0, height));
		
		mPlat.add(new MovingPlatform(200, 250, 0, width, 0, height));
		mPlat.get(0).setXSpeed(14-7);
		
		mPlat.add(new MovingPlatform(800, 250, 0, width, 0, height));
		mPlat.get(1).setYSpeed(14-10);
		

		Thread gameThread = new Thread(this);
		gameThread.start();
		
		setFocusable(true);
		addKeyListener(this);

	}
	
	public void run() {
		while (true) {
			repaint();
			cheackCollision();
			if(cheackCollision()){
				mPlat.get(0).setXSpeed(mPlat.get(0).getXspeed() *-1);
				mPlat.get(1).setYSpeed(mPlat.get(1).getYspeed() *-1);
			}
			try {
				Thread.sleep(pauseDuration);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
			
		for (int i = 0; i < ground.size(); i++) {
			 g.setColor(Color.GREEN);   
			ground.get(i).draw(g);
			  }
			
		for (int i = 0; i < block.size(); i++) {
			g.setColor(Color.BLACK);   
			block.get(i).draw(g);
			  }
		
		for (int i = 0; i < iBlock.size(); i++) {  
			g.setColor(Color.MAGENTA);  
			iBlock.get(i).draw(g);
			  }
		
		for (int i = 0; i < mPlat.size(); i++) {  
			g.setColor(Color.BLACK);  
			mPlat.get(i).draw(g);
			  }
	}
	
	public boolean cheackCollision(){
	if(mPlat.get(0).getX() + mPlat.get(0).getWidth() > Math.abs(500) || mPlat.get(0).getX() < Math.abs(100)){
		return true;
		}
	else 
		return false;
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
