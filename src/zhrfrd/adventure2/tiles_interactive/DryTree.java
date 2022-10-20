package zhrfrd.adventure2.tiles_interactive;

import java.awt.Color;

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
	
	@Override
	/**
	 * Get the color of the dry tree's particle.
	 * 
	 * @return The color of the particle. 
	 */
	public Color getParticleColor() {
		Color color = new Color(65, 50, 30);
		
		return color;
	}
	
	@Override
	/**
	 * Get the size of the particle in pixels.
	 * 
	 * @return The size of the particle in pixels.
	 */
	public int getParticleSize() {
		int size = 6;   // Pixels
		
		return size;
	}
	
	@Override
	/**
	 * Get the speed of the moving particle.
	 * 
	 * @return The speed of the moving particle.
	 */
	public int getParticleSpeed() {
		int speed = 1;
		
		return speed;
	}
	
	@Override
	/**
	 * Get the maximum life time of a particle.
	 * 
	 * @return The maximum life time value of the particle.
	 */
	public int getParticleMaxLife() {
		int maxLife = 20;
		
		return maxLife;
	}
	
	@Override
	/**
	 * Play sound effect for the interactive tile.
	 */
	public void playSoundEffect() {
		gp.playSoundEffect(11);
	}
	
	@Override
	/**
	 * Get the changed (destroyed) form of the interactive tile.
	 * 
	 * @return The changed (destroyed) form of the interactive tile.
	 */
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = new Trunk(gp, worldX / gp.TILE_SIZE, worldY / gp.TILE_SIZE);
		
		return tile;
	}
	
	@Override
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
