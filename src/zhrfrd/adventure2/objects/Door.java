package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class Door extends Entity {
	public Door(GamePanel gp) {
		super(gp);
		name = "Door";
		down1 = setup("/objects/door", gp.TILE_SIZE, gp.TILE_SIZE);
		solid = true;
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
	}
}
