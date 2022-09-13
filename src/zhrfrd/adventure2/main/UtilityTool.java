package zhrfrd.adventure2.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {
	/*
	 * Scale tiles
	 */
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();   // Enable drawing into this buffered image (scaledImage)
		g2.drawImage(original, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}
}
