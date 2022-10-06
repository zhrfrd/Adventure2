package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class PotionRed extends Entity {
	GamePanel gp;
	int value = 5;   // Healing value
	
	public PotionRed(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		type = TYPE_CONSUMABLE;
		name = "Red potion";
		down1 = setup("/objects/potion_red", gp.TILE_SIZE, gp.TILE_SIZE);
		description = "[" + name + "]\nHeals your life by " + value + ".";
	}
	
	/*
	 * Display a dialog box and heals player's life when used
	 */
	@Override
	public void use(Entity entity) {
		gp.gameState = gp.dialogState;
		gp.ui.currentDialog = "You drunk the " + name + "!\nYour life has been recovered by " + value + ".";
		entity.life += value;
		
		if (entity.life > entity.maxLife)
			entity.life = entity.maxLife;
		 
		gp.playSoundEffect(2);
	}
}
