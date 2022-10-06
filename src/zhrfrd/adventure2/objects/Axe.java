package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class Axe extends Entity {
	public Axe(GamePanel gp) {
		super(gp);
		
		type = TYPE_AXE;
		name = "Wooden axe";
		down1 = setup("/objects/axe", gp.TILE_SIZE, gp.TILE_SIZE);
		attackValue = 2;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "[" + name + "]\n You can chop wood with\nthis.\nSuitable also for fingers.";
	}
}
