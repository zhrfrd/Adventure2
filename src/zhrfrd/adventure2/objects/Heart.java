package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class Heart extends Entity {
	public Heart(GamePanel gp) {
		super(gp);
		name = "Heart";
		image = setup("/objects/heart_full", gp.TILE_SIZE, gp.TILE_SIZE);
		image2 = setup("/objects/heart_half", gp.TILE_SIZE, gp.TILE_SIZE);
		image3 = setup("/objects/heart_blank", gp.TILE_SIZE, gp.TILE_SIZE);
	}
}
