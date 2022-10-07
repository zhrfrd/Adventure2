package zhrfrd.adventure2.objects;

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
	
	/*
	 * Get the fireball image from res folder
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
}
