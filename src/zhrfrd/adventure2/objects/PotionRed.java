package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class PotionRed extends Entity {
	GamePanel gp;
	
	public PotionRed(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		type = TYPE_CONSUMABLE;
		name = "Red potion";
		down1 = setup("/objects/potion_red", gp.TILE_SIZE, gp.TILE_SIZE);
		value = 5;   // Healing value
		description = "[" + name + "]\nHeals your life by " + value + ".";
	}
	
	/**
	 * Display a dialog box and heals player's life when used
	 * 
	 * @param entity The entity that will use the red potion.
	 */
	@Override
	public void use(Entity entity) {
		gp.gameState = gp.dialogState;
		gp.ui.currentDialog = "You drunk the " + name + "!\nYour life has been recovered by " + value + ".";
		entity.life += value;
		 
		gp.playSoundEffect(2);
	}
}
