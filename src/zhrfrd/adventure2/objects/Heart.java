package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class Heart extends Entity {
	GamePanel gp;
	
	public Heart(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		type = TYPE_PICKUP_ONLY;
		name = "Heart";
		value = 2;
		down1 = setup("/objects/heart_full", gp.TILE_SIZE, gp.TILE_SIZE);
		image = setup("/objects/heart_full", gp.TILE_SIZE, gp.TILE_SIZE);
		image2 = setup("/objects/heart_half", gp.TILE_SIZE, gp.TILE_SIZE);
		image3 = setup("/objects/heart_blank", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
	/**
	 * Pickup hearts to heal player's life.
	 * 
	 * @param entity The entity that will use the heart to heal.
	 */
	@Override
	public void use(Entity entity) {
		gp.playSoundEffect(2);
		gp.ui.addMessage("Life +" + value);
		entity.life += value;
	}
}
