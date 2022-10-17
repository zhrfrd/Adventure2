package zhrfrd.adventure2.tiles_interactive;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class DryTree extends InteractiveTile {
	GamePanel gp;
	
	public DryTree(GamePanel gp, int col, int row) {
		super(gp, col, row);

		this.gp = gp;
		down1 = setup("/tiles_interactive/drytree", gp.TILE_SIZE, gp.TILE_SIZE);
		destructible = true;
		life = 3;
	}
	
	/**
	 * Play sound effect for the interactive tile 
	 */
	public void playSoundEffect() {
		gp.playSoundEffect(11);
	}
	
	/**
	 * Get the changed (destroyed) form of the interactive tile
	 * 
	 * @return The changed (destroyed) form of the interactive tile
	 */
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = new Trunk(gp, worldX / gp.TILE_SIZE, worldY / gp.TILE_SIZE);
		
		return tile;
	}
	
	/**
	 * Check if the item the player is holding is correct in order to break the dry tree.
	 * 
	 * @param entity Entity that is holding the item.
	 * @return True if the item is correct, false otherwise.
	 */
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		
		if (entity.currentWeapon.type == TYPE_AXE)
				isCorrectItem = true;
		
		return isCorrectItem;
	}
}
