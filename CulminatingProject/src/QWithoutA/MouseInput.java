package QWithoutA;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import QWithoutA.SaltGamePanel.STATE; 

public class MouseInput implements MouseListener {
	
	public static Rectangle playButton = new Rectangle(500, 300, 100, 50);
	public static Rectangle helpButton = new Rectangle(500, 400, 100, 50);
	public static Rectangle quitButton = new Rectangle(500, 500, 100, 50);
	

	public MouseInput() {
		
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		int mouseX = e.getX ();
		int mouseY = e.getY ();
		
		if (mouseX >= playButton.x && mouseX <= playButton.x + 100) {
		if (mouseY >= playButton.y && mouseY <= playButton.y + 50) {
			SaltGamePanel.State = STATE.GAME;
			}
		}

	}

	public void mouseReleased(MouseEvent e) {
		
	}

}
