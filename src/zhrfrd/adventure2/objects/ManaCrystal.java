package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class ManaCrystal extends Entity {
	GamePanel gp;
	
	public ManaCrystal(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		name = "Mana Crystal";
		image = setup("/objects/manacrystal_full", gp.TILE_SIZE, gp.TILE_SIZE);
		image2 = setup("/objects/manacrystal_blank", gp.TILE_SIZE, gp.TILE_SIZE);
	}
}
