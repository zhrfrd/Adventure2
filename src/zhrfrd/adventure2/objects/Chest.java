package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class Chest extends Entity{
	public Chest(GamePanel gp) {
		super(gp);
		name = "Chest";
		down1 = setup("/objects/chest", gp.TILE_SIZE, gp.TILE_SIZE);
	}
}
