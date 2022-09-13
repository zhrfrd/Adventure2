package zhrfrd.adventure.objects;

import java.io.IOException;

import javax.imageio.ImageIO;

import zhrfrd.adventure.main.GamePanel;

public class Chest extends SuperObject{
	GamePanel gp;
	
	public Chest(GamePanel gp) {
		this.gp = gp;
		name = "Chest";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
			utilityTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
