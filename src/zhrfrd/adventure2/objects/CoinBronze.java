package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class CoinBronze extends Entity {
	GamePanel gp;
	
	public CoinBronze(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		type = TYPE_PICKUP_ONLY;
		name = "Bronze Coin";
		value = 1;
		down1 = setup("/objects/coin_bronze", gp.TILE_SIZE, gp.TILE_SIZE);
	}

	/**
	 * Add coins to the player.
	 * 
	 * @param entity The entity that will use the coin
	 */
	@Override
	public void use(Entity entity) {
		gp.playSoundEffect(1);
		gp.ui.addMessage("Coin +" + value);
		gp.player.coin += value;
	}
}
