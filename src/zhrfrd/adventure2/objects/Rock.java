package zhrfrd.adventure2.objects;

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
}
