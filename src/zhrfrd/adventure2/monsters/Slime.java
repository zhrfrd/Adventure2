package zhrfrd.adventure2.monsters;

import java.util.Random;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class Slime extends Entity {
	public Slime(GamePanel gp) {
		super(gp);
		
		type = 2;
		name = "Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
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
		up1 = setup("/monsters/slime_1");
		up2 = setup("/monsters/slime_2");
		down1 = setup("/monsters/slime_1");
		down2 = setup("/monsters/slime_2");
		left1 = setup("/monsters/slime_1");
		left2 = setup("/monsters/slime_2");
		right1 = setup("/monsters/slime_1");
		right2 = setup("/monsters/slime_2");
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
}
