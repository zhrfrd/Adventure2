package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class SwordNormal extends Entity {
	public SwordNormal(GamePanel gp) {
		super(gp);
		
		name = "Normal sword";
		down1 = setup("/objects/sword_normal", gp.TILE_SIZE, gp.TILE_SIZE);
		attackValue = 1;
		description = "[" + name + "]\nAn old sword belonged\nto Toni Patata."; 
	}

}
