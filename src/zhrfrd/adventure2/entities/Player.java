package zhrfrd.adventure2.entities;

import java.awt.AlphaComposite;
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
//		worldX = gp.TILE_SIZE * 10;   // Player starting position on the map
//		worldY = gp.TILE_SIZE * 13;   //
		speed = 4;
		direction = "down";   // Default direction
		
		maxLife = 6;   // 3 hearts (one life is equal to half heart)
		life = maxLife;
	}
	
	/*
	 * Get the already scaled up sprites of the moving player
	 */
	public void getPlayerImage() {
		up1 = setup("/player/player_up_1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/player/player_up_2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/player/player_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/player/player_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/player/player_left_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/player/player_left_2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/player/player_right_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/player/player_right_2", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
	/*
	 * Get the already scaled up sprites of the attacking player
	 */
	public void getPlayerAttackImage() {
		attackUp1 = setup("/player/player_attack_up_1/", gp.TILE_SIZE, gp.TILE_SIZE * 2);
		attackUp2 = setup("/player/player_attack_up_2/", gp.TILE_SIZE, gp.TILE_SIZE * 2);
		attackDown1 = setup("/player/player_attack_down_1/", gp.TILE_SIZE, gp.TILE_SIZE * 2);
		attackDown2 = setup("/player/player_attack_down_2/", gp.TILE_SIZE, gp.TILE_SIZE * 2);
		attackLeft1 = setup("/player/player_attack_left_1/", gp.TILE_SIZE * 2, gp.TILE_SIZE);
		attackLeft2 = setup("/player/player_attack_left_2/", gp.TILE_SIZE * 2, gp.TILE_SIZE);
		attackRight1 = setup("/player/player_attack_right_1/", gp.TILE_SIZE * 2, gp.TILE_SIZE);
		attackRight2 = setup("/player/player_attack_right_2/", gp.TILE_SIZE * 2, gp.TILE_SIZE);
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
		if (index != 999)
			if (gp.keyHandler.enterPressed) {
				gp.gameState = gp.dialogState;
				gp.npc[index].speak();
			}
	}
	
	/*
	 * Handle player touching a monster
	 */
	public void contactMonster(int i) {
		if (i != 999)
			if (!invincible) {
				life --;
				invincible = true;   // When the player receive damage from touching a monster, he becomes temporary invincible to avoid losing all his life straight away
			}
	}
	
	/*
	 * Update player information such as position
	 */
	@Override
	public void update() {
		// Get keyboard strokes and update player position and allow interactions by only pressing the enter key
		if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.enterPressed) {
			if (keyHandler.upPressed)
				direction = "up";
			
			if (keyHandler.downPressed)
				direction = "down";
			
			if (keyHandler.leftPressed)
				direction = "left";
			
			if (keyHandler.rightPressed)
				direction = "right";
			
			// Check the tile collision
			collisionOn = false;
			gp.collisionChecker.checkTile(this);
			
			// Check if player collided with an object and save the object index
			int objIndex = gp.collisionChecker.checkObject(this, true);
			pickUpObject(objIndex);	
			
			// Check NPC collision
			int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// Check monster collision
			int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// Check event
			gp.eventHandler.checkEvent();
			
			// Move the player only in case there is no collision detected
			if (!collisionOn && !keyHandler.enterPressed) {
				if (direction == "up")
					worldY -= speed;
				
				if (direction == "down")
					worldY += speed;
					
				if (direction == "left")
					worldX -= speed;
					
				if (direction == "right")
					worldX += speed;
			}
			
			gp.keyHandler.enterPressed = false;
			
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
		
		// When the player hits a monster (and receive damage) he become invincible for a period of 1 seconds to avoid loosing all his life straight away
		if (invincible) {
			invincibleCounter ++;
			
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
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
		
		// When the player receives damage it he becomes slightly transparent for a moment to show a visual effect of the damage
		if (invincible)
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));   // Change the alpha level of the player sprite 
		
		g2.drawImage(image, SCREEN_X, SCREEN_Y, null);
		
		// Reset the transparency of the player sprite
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		// Debug
		g2.setColor(Color.red);
		g2.drawRect(SCREEN_X + solidArea.x, SCREEN_Y + solidArea.y, solidArea.width, solidArea.height);
	}
}
