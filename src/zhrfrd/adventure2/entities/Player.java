package zhrfrd.adventure2.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import zhrfrd.adventure2.main.GamePanel;
import zhrfrd.adventure2.main.KeyHandler;

public class Player extends Entity {
	KeyHandler keyHandler;
	public final int SCREEN_X, SCREEN_Y;   // Position of the player of the screen
	
	public Player(GamePanel gp, KeyHandler keyHandler) {
		super(gp);
		this.keyHandler = keyHandler;
		SCREEN_X = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);    // Player in the middle of the screen
		SCREEN_Y = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);   //
		
		solidArea = new Rectangle(8, 16, 32, 32);   // Rectangle solid area of the player
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		setDefaultValues();
		getPlayerImage();
	}
	
	/*
	 * Set default values of the player when a new instance of Player is created
	 */
	public void setDefaultValues() {
		worldX = gp.TILE_SIZE * 23;   // Player starting position on the map
		worldY = gp.TILE_SIZE * 21;   //
		speed = 4;
		direction = "down";   // Default direction
	}
	
	/*
	 * Get the sprites of the player
	 */
	public void getPlayerImage() {
		up1 = setup("/player/player_up_1");
		up2 = setup("/player/player_up_2");
		down1 = setup("/player/player_down_1");
		down2 = setup("/player/player_down_2");
		left1 = setup("/player/player_left_1");
		left2 = setup("/player/player_left_2");
		right1 = setup("/player/player_right_1");
		right2 = setup("/player/player_right_2");
	}
	
	/*
	 * Update player information such as position
	 */
	public void update() {
		// Get keyboard strokes and update player position
		if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
			if (keyHandler.upPressed)
				direction = "up";
			
			if (keyHandler.downPressed)
				direction = "down";
			
			if (keyHandler.leftPressed)
				direction = "left";
			
			if (keyHandler.rightPressed)
				direction = "right";
			
			collisionOn = false;
			gp.collisionChecker.checkTile(this);   // Check the tile collision
			
			int objIndex = gp.collisionChecker.checkObject(this, true);   // Check if player collided with an object and save the object index
			pickUpObject(objIndex);	
			
			int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);   // Check NPC collision
			interactNPC(npcIndex);
			
			// Move the player only in case there is no collision detected
			if (collisionOn == false) {
				if (direction == "up")
					worldY -= speed;
				
				if (direction == "down")
					worldY += speed;
					
				if (direction == "left")
					worldX -= speed;
					
				if (direction == "right")
					worldX += speed;
			}
			
			spriteCounter ++;   // Increase spriteCounter every 0.01666 seconds
			
			// Swap sprite every 10 FPS
			if (spriteCounter > 9) {
				if (spriteNumber == 1)
					spriteNumber = 2;
				
				else if (spriteNumber == 2)
					spriteNumber = 1;
				
				spriteCounter = 0;
			}
		}
		
		else
			spriteNumber = 1;
	}
	
	/*
	 * Remove object from the map and add it to the player inventory
	 */
	public void pickUpObject(int index) {
		// If the player collided with an existing object, pick it up
		if (index != 999) {
			
		}
	}
	
	/*
	 * Handle interaction between player and NPC
	 */
	public void interactNPC(int index) {
		// If the player collided with an existing npc, do interaction
		if (index != 999) {
			
			if (gp.keyHandler.enterPressed) {
				gp.gameState = gp.dialogState;
				gp.npc[index].speak();
			}
		}
		
		gp.keyHandler.enterPressed = false;
	}
	
	/*
	 * Re-draw player each update
	 */
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		if (direction == "up") {
			if (spriteNumber == 1)
				image = up1;
			
			else
				image = up2;
		}
		
		if (direction == "down") {
			if (spriteNumber == 1)
				image = down1;
			
			else
				image = down2;
		}
		
		if (direction == "left") {
			if (spriteNumber == 1)
				image = left1;
			
			else
				image = left2;
		}
		
		if (direction == "right") {
			if (spriteNumber == 1)
				image = right1;
			
			else
				image = right2;
		}
		
		g2.drawImage(image, SCREEN_X, SCREEN_Y, null);
		g2.setColor(Color.red);
		g2.drawRect(SCREEN_X + solidArea.x, SCREEN_Y + solidArea.y, solidArea.width, solidArea.height);
	}
}
