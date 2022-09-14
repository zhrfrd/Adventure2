package zhrfrd.adventure2.entities;

import java.util.Random;

import zhrfrd.adventure2.main.GamePanel;

public class OldMan extends Entity {

	public OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		getImage();
	}
	
	/*
	 * Get the sprites of the old man
	 */
	public void getImage() {
		up1 = setup("/npc/oldman_up_1");
		up2 = setup("/npc/oldman_up_2");
		down1 = setup("/npc/oldman_down_1");
		down2 = setup("/npc/oldman_down_2");
		left1 = setup("/npc/oldman_left_1");
		left2 = setup("/npc/oldman_left_2");
		right1 = setup("/npc/oldman_right_1");
		right2 = setup("/npc/oldman_right_2");
	}
	
	/*
	 * Set old man behaviour such as movements
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