package QWithoutA;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu implements MouseListener {
	
	public static Rectangle playButton = new Rectangle(500, 300, 100, 50);
	public static Rectangle helpButton = new Rectangle(500, 400, 100, 50);
	public static Rectangle quitButton = new Rectangle(500, 500, 100, 50);

	public static void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Font fnt0 = new Font("Comic Sans MS", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.BLACK);
		g.drawString("The Adventures of Salt Man", 250, 250);
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt1);
		g.drawString("Play", playButton.x + 19, playButton.y + 35);
		g2d.draw(playButton);
		g.drawString("Help", helpButton.x + 19, helpButton.y + 35);
		g2d.draw(helpButton);
		g.drawString("Quit", quitButton.x + 19, quitButton.y + 35);
		g2d.draw(quitButton);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

}
