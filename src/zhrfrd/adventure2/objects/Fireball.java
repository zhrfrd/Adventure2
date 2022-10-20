package zhrfrd.adventure2.objects;

import java.awt.Color;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.entities.Projectile;
import zhrfrd.adventure2.main.GamePanel;

public class Fireball extends Projectile {
	GamePanel gp;
	
	public Fireball(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		name = "Fireball";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;   // 1 Mana to use the fireball
		alive = false;
		
		getImage();
	}
	
	/**
	 * Get the fireball image from res folder.
	 */
	public void getImage() {
		up1 = setup("/projectiles/fireball_up_1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/projectiles/fireball_up_2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/projectiles/fireball_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/projectiles/fireball_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/projectiles/fireball_left_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/projectiles/fireball_left_2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/projectiles/fireball_right_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/projectiles/fireball_right_2", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
	/**
	 * Check if the user has enough mana to shoot a fireball.
	 * 
	 * @param user The entity that wants to shoot the fireball.
	 * @return True if the users has enough mana to shoot a fireball, false otherwise.
	 */
	@Override
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		
		if (user.mana >= useCost)
			haveResource = true;
		
		return haveResource;
	}
	
	/**
	 * Subtract the mana from the user when he uses it.
	 * @param user The entity that wants to shoot the fireball.
	 */
	@Override
	public void subtractResource(Entity user) {
		user.mana -= useCost;
	}
	
	@Override
	/**
	 * Get the color of the particle.
	 * 
	 * @return The color of the particle. 
	 */
	public Color getParticleColor() {
		Color color = new Color(220, 0, 0);
		
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
