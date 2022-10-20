package zhrfrd.adventure2.tiles_interactive;

import java.awt.Color;
import java.awt.Graphics2D;

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
	
	/**
	 * Draw the interactive tile (such as breakable trees) on the map.
	 * 
	 * @param g2 The "paint-brush" responsible to draw on the game panel.
	 */
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;   // Interactive tile position on the screen
		int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;   //
		
		// Render only the interactive tiles visible on the screen plus one extra tile in order to not show black edges when moving
		if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X && 
				worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X && 
				worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y && 
				worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
			
			g2.drawImage(down1, screenX, screenY, null);
		}
	}
}
