package zhrfrd.adventure2.entities;

import java.awt.Rectangle;
import java.util.Random;

import zhrfrd.adventure2.main.GamePanel;

public class OldMan extends Entity {

	public OldMan(GamePanel gp) {
		super(gp);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		direction = "down";
		speed = 1;
		getImage();
		setDialog();
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
	 * Set the old man dialogs text
	 */
	public void setDialog() {
		dialogs[0] = "Hello, mate!";
		dialogs[1] = "So, you've come to this valley to find the \nhidden treasure eh??"; 
		dialogs[2] = "Maialone xP";
		dialogs[3] = "I'm too old to take an adventure with you, \nBUT!!!";
		dialogs[4] = "Oh, I forgot what I was going to say..."; 
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
	
	/*
	 * Make the old man speak
	 */
	@Override
	public void speak() {
		super.speak();
	}
}