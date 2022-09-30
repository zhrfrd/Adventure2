package zhrfrd.adventure2.monsters;

import java.util.Random;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class Slime extends Entity {
	GamePanel gp;
	
	public Slime(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = 2;
		name = "Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		attack = 5;
		defence = 0;
		exp = 2;   // Experience the player gets when he kills the monster
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	
	/*   
	 * Get the already scaled up sprites of the slime
	 */
	public void getImage() {
		up1 = setup("/monsters/slime_1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/monsters/slime_2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/monsters/slime_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/monsters/slime_2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/monsters/slime_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/monsters/slime_2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/monsters/slime_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/monsters/slime_2", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
	/*
	 * Set slime behaviour
	 */
	@Override
	public void setAction() {
		actionLockCounter ++;
		
		// Change movement direction every 2 seconds
		if (actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;   // Get a random number from 1 to 100
			
			if (i <= 25) 
				direction = "up";
			
			if (i > 25 && i <= 50)
				direction = "down";
			
			if (i > 50 && i <= 75)
				direction = "left";
			
			if (i > 75 && i <= 100)
				direction = "right";
			
			actionLockCounter = 0;
		}
	}
	
	/*
	 * React when receiving damage
	 */
	@Override
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction;   // Move away from the player when receiving damage
	}
}
