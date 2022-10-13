package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class ManaCrystal extends Entity {
	GamePanel gp;
	
	public ManaCrystal(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		type = TYPE_PICKUP_ONLY;
		name = "Mana Crystal";
		value = 1;
		down1 = setup("/objects/manacrystal_full", gp.TILE_SIZE, gp.TILE_SIZE);
		image = setup("/objects/manacrystal_full", gp.TILE_SIZE, gp.TILE_SIZE);
		image2 = setup("/objects/manacrystal_blank", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
	/**
	 * Add mana to the player;
	 * 
	 * @param entity The entity that will add the mana.
	 */
	@Override
	public void use(Entity entity) {
		gp.playSoundEffect(2);
		gp.ui.addMessage("Mana +" + value);
		entity.mana += value;
	}
}
