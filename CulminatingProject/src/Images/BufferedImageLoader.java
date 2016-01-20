package Images;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

	public class BufferedImageLoader {
		  BufferedImage img;
		    public BufferedImageLoader() {
		        try {
		            img = ImageIO.read(new File("Saltman.png"));
		        } catch (IOException e) {
		        	e.printStackTrace();
		        }

		     }

		public static BufferedImage loadImage(String path){
			try {
				return ImageIO.read(BufferedImageLoader.class.getResource(path));
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			return null;
		}
	}