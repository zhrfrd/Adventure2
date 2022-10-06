package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class ShieldBlue extends Entity {
	public ShieldBlue(GamePanel gp) {
		super(gp);
		
		type = TYPE_SHIELD;
		name = "Blue shield";
		down1 = setup("/objects/shield_blue", gp.TILE_SIZE, gp.TILE_SIZE);
		defenceValue = 2;
		description = "[" + name + "]\nAn iron shield made of\nblue."; 
	}
}
