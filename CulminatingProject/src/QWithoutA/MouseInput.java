package QWithoutA;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import QWithoutA.SaltGamePanel;
import QWithoutA.SaltGamePanel.STATE; 

public class MouseInput implements MouseListener {
	
	public static Rectangle playButton = new Rectangle(500, 250, 100, 50);
	public static Rectangle continueButton = new Rectangle(500, 250, 100, 50);
	public static Rectangle quitButton = new Rectangle(500, 350, 100, 50);
	
	public void mouseClicked(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		if (SaltGamePanel.State == STATE.MENU){
			if (mouseX >= playButton.x && mouseX <= playButton.x + 100) {
				if (mouseY >= playButton.y && mouseY <= playButton.y + 50) {
					SaltGamePanel.State = SaltGamePanel.STATE.GAME;
				}
			}
			if (mouseX >= quitButton.x && mouseX <= quitButton.x + 100) {
				if (mouseY >= quitButton.y && mouseY <= quitButton.y + 50) {
					System.exit(0);
				}
			}
		}
		else if (SaltGamePanel.State == STATE.DEATH) {
			if (mouseX >= continueButton.x && mouseX <= continueButton.x + 100) {
				if (mouseY >= continueButton.y && mouseY <= continueButton.y + 50) {
					SaltGamePanel.player.get(0).setX(0);
					SaltGamePanel.player.get(0).setY(400);
					SaltGamePanel.State = SaltGamePanel.STATE.GAME;
				}
			}
			if (mouseX >= quitButton.x && mouseX <= quitButton.x + 100) {
				if (mouseY >= quitButton.y && mouseY <= quitButton.y + 50) {
					System.exit(0);
				}
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		if (SaltGamePanel.State == STATE.MENU){
			if (mouseX >= playButton.x && mouseX <= playButton.x + 100) {
				if (mouseY >= playButton.y && mouseY <= playButton.y + 50) {
					SaltGamePanel.State = SaltGamePanel.STATE.GAME;
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		
	}

}