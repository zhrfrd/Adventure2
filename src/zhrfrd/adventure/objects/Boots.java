package zhrfrd.adventure.objects;

import java.io.IOException;

import javax.imageio.ImageIO;

import zhrfrd.adventure.main.GamePanel;

public class Boots extends SuperObject{
	GamePanel gp;
	
	public Boots(GamePanel gp) {
		this.gp = gp;
		name = "Boots";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
			utilityTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
