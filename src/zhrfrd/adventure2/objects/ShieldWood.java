package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class ShieldWood extends Entity {
	public ShieldWood(GamePanel gp) {
		super(gp);
		
		name = "Wood shield";
		down1 = setup("/objects/shield_wood", gp.TILE_SIZE, gp.TILE_SIZE);
		defence = 1;
	}
}
