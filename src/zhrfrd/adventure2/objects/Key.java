package zhrfrd.adventure2.objects;

import java.io.IOException;

import javax.imageio.ImageIO;

import zhrfrd.adventure2.main.GamePanel;

public class Key extends SuperObject {
	GamePanel gp;
	
	public Key(GamePanel gp) {
		this.gp = gp;
		name = "Key";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
			utilityTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
