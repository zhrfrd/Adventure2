package zhrfrd.adventure2.objects;

import java.io.IOException;

import javax.imageio.ImageIO;

import zhrfrd.adventure2.main.GamePanel;

public class Door extends SuperObject {
	GamePanel gp;
	
	public Door(GamePanel gp) {
		this.gp = gp;
		name = "Door";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
			utilityTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		solid = true;
	}
}
