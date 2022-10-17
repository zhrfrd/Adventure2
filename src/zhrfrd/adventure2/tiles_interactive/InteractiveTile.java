package zhrfrd.adventure2.tiles_interactive;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class InteractiveTile extends Entity {
	GamePanel gp;
	public boolean destructible = false;

	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
		
		this.worldX = gp.TILE_SIZE * col;
		this.worldY = gp.TILE_SIZE * row;
	}
	
	/**
	 * Play sound effect for the interactive tile 
	 */
	public void playSoundEffect() {}

	/**
	 * Check if the item the player is holding is correct.
	 * 
	 * @param entity Entity that is holding the item.
	 * @return True if the item is correct, false otherwise.
	 */
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		
		return isCorrectItem;
	}
	
	/**
	 * Get the changed (destroyed) form of the interactive tile
	 * 
	 * @return The changed (destroyed) form of the interactive tile
	 */
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = null;
		
		return null;
	}
	
	/**
	 * Update the interactive tile status
	 */
	@Override
	public void update() {
		// Add temporary invincibility to the interactive tile 
		if (invincible) {
			invincibleCounter ++;
			
			if (invincibleCounter > 20) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
}
