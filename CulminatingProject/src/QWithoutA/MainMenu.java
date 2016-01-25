package QWithoutA;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import Entities.Player;
import QWithoutA.SaltGamePanel;
import QWithoutA.SaltGamePanel.STATE;

public class MainMenu  {
	
	private int mouseX;
	private int mouseY;
	private static BufferedImage image;

	public static Rectangle playButton = new Rectangle(500, 250, 100, 50);
	public static Rectangle quitButton = new Rectangle(500, 350, 100, 50); {
	try{
		image = ImageIO.read(Player.class.getResourceAsStream("/Images/Title Screen.png"));
	}catch(IOException e){
		e.printStackTrace();
	}
	}
	
	public static void render(Graphics g) {
		if (SaltGamePanel.State == STATE.MENU) {
			g.drawImage(image, 5, 5, null);
			Graphics2D g2d = (Graphics2D) g;
			Font fnt0 = new Font("Comic Sans MS", Font.BOLD, 50);
			g.setFont(fnt0);
			g.setColor(Color.WHITE);
			Font fnt1 = new Font("arial", Font.BOLD, 30);
			g.setFont(fnt1);
			g.drawString("Play", playButton.x + 19, playButton.y + 35);
			g2d.draw(playButton);
			g.drawString("Quit", quitButton.x + 19, quitButton.y + 35);
			g2d.draw(quitButton);
		}
	}

}