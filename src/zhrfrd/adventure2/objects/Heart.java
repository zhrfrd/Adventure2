package zhrfrd.adventure2.objects;

import java.io.IOException;

import javax.imageio.ImageIO;

import zhrfrd.adventure2.main.GamePanel;

public class Heart extends SuperObject {
	GamePanel gp;
	
	public Heart(GamePanel gp) {
		this.gp = gp;
		name = "Heart";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png"));
			image = utilityTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
			image2 = utilityTool.scaleImage(image2, gp.TILE_SIZE, gp.TILE_SIZE);
			image3 = utilityTool.scaleImage(image3, gp.TILE_SIZE, gp.TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		solid = true;
	}
}
