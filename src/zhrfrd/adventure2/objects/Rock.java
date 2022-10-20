package zhrfrd.adventure2.objects;

import java.awt.Color;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.entities.Projectile;
import zhrfrd.adventure2.main.GamePanel;

public class Rock extends Projectile {
	GamePanel gp;
	
	public Rock(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		name = "Rock";
		speed = 8;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;   // 1 Mana to use the Rock
		alive = false;
		
		getImage();
	}
	
	/**
	 * Get the rock image from res folder.
	 */
	public void getImage() {
		up1 = setup("/projectiles/rock_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/projectiles/rock_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/projectiles/rock_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/projectiles/rock_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/projectiles/rock_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/projectiles/rock_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/projectiles/rock_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/projectiles/rock_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
	/**
	 * Check if the user has enough ammo to shoot a rock.
	 * 
	 * @param user The entity that wants to shoot the rock.
	 * @return True if the users has enough ammo to shoot a rock, false otherwise.
	 */
	@Override
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		
		if (user.ammo >= useCost)
			haveResource = true;
		
		return haveResource;
	}
	
	/**
	 * Subtract the ammo from the user when he uses it.
	 * @param user The entity that wants to shoot the rock.
	 */
	@Override
	public void subtractResource(Entity user) {
		user.ammo -= useCost;
	}
	
	@Override
	/**
	 * Get the color of the particle.
	 * 
	 * @return The color of the particle. 
	 */
	public Color getParticleColor() {
		Color color = new Color(153, 76, 0);
		
		return color;
	}
	
	@Override
	/**
	 * Get the size of the particle in pixels.
	 * 
	 * @return The size of the particle in pixels.
	 */
	public int getParticleSize() {
		int size = 10;   // Pixels
		
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
}
