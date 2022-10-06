package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class ShieldWood extends Entity {
	public ShieldWood(GamePanel gp) {
		super(gp);
		
		type = TYPE_SHIELD;
		name = "Wood shield";
		down1 = setup("/objects/shield_wood", gp.TILE_SIZE, gp.TILE_SIZE);
		defenceValue = 1;
		description = "[" + name + "]\nAn old shield made of\nwood."; 
	}
}
