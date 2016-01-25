package QWithoutA;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import QWithoutA.SaltGamePanel.STATE;

public class DeathScreen {

	private int mouseX;
	private int mouseY;

	public static Rectangle continueButton = new Rectangle(475, 250, 170, 50);
	public static Rectangle quitButton = new Rectangle(500, 350, 100, 50);

public static void render(Graphics g) {
	if (SaltGamePanel.State == STATE.DEATH) {
		Graphics2D g2d = (Graphics2D) g;
		Font fnt0 = new Font("Comic Sans MS", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.BLACK);
		g.drawString("You died!", 230, 200);
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt1);
		g.drawString("Continue", continueButton.x + 19, continueButton.y + 35);
		g2d.draw(continueButton);
		g.drawString("Quit", quitButton.x + 19, quitButton.y + 35);
		g2d.draw(quitButton);
	}
}

}
