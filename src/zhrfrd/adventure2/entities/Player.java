package zhrfrd.adventure2.entities;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import zhrfrd.adventure2.main.GamePanel;
import zhrfrd.adventure2.main.KeyHandler;
import zhrfrd.adventure2.objects.ShieldWood;
import zhrfrd.adventure2.objects.SwordNormal;

public class Player extends Entity {
	KeyHandler keyHandler;
	public final int SCREEN_X, SCREEN_Y;   // Position of the player of the screen
	public boolean attackCancelled = false;
	
	public Player(GamePanel gp, KeyHandler keyHandler) {
		super(gp);
		this.keyHandler = keyHandler;
		SCREEN_X = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);    // Player in the middle of the screen
		SCREEN_Y = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);   //
		
		solidArea = new Rectangle(8, 16, 32, 32);   // Rectangle solid area of the player
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = 36;
		attackArea.height = 36;
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
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
		
		// Stats
		level = 1;
		maxLife = 6;   // 3 hearts (one life is equal to half heart)
		life = maxLife;
		strength = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new SwordNormal(gp);
		currentShield = new ShieldWood(gp);
		attack = getAttack();
		defence = getDefence();
	}
	
	/*
	 * Get attack value of the player
	 */
	public int getAttack() {
		return attack = strength * currentWeapon.attackValue;
	}
	
	/*
	 * Get defence value of the player
	 */
	public int getDefence() { 
		return defence = dexterity * currentShield.defenceValue;
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
		attackUp1 = setup("/player/player_attack_up_1", gp.TILE_SIZE, gp.TILE_SIZE * 2);
		attackUp2 = setup("/player/player_attack_up_2", gp.TILE_SIZE, gp.TILE_SIZE * 2);
		attackDown1 = setup("/player/player_attack_down_1", gp.TILE_SIZE, gp.TILE_SIZE * 2);
		attackDown2 = setup("/player/player_attack_down_2", gp.TILE_SIZE, gp.TILE_SIZE * 2);
		attackLeft1 = setup("/player/player_attack_left_1", gp.TILE_SIZE * 2, gp.TILE_SIZE);
		attackLeft2 = setup("/player/player_attack_left_2", gp.TILE_SIZE * 2, gp.TILE_SIZE);
		attackRight1 = setup("/player/player_attack_right_1", gp.TILE_SIZE * 2, gp.TILE_SIZE);
		attackRight2 = setup("/player/player_attack_right_2", gp.TILE_SIZE * 2, gp.TILE_SIZE);
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
		if (gp.keyHandler.enterPressed) {
			// If the player collided with an existing npc, do interaction
			if (index != 999) {
				attackCancelled = true;
				gp.gameState = gp.dialogState;
				gp.npc[index].speak();
			}
		}
	}
	
	/*
	 * Handle player touching a monster
	 */
	public void contactMonster(int i) {
		if (i != 999)
			if (!invincible) {
				int damage = gp.monster[i].attack - defence;
				
				if (damage < 0)
					damage = 0;
				
				gp.playSoundEffect(6);
				life -= damage;
				invincible = true;   // When the player receive damage from touching a monster, he becomes temporary invincible to avoid losing all his life straight away
			}
	}
	
	/*
	 * Handle damaging of monster after attacking
	 */
	public void damageMonster(int i) {
		if (i != 999)
			if (!gp.monster[i].invincible) {
				int damage = attack - gp.monster[i].defence;
				
				if (damage < 0)
					damage = 0;
				
				gp.playSoundEffect(5);
				gp.monster[i].life -= damage;
				gp.ui.addMessage(damage + " damage!");
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
				
				// When the monster's life reaches 0 the monster is dead
				if (gp.monster[i].life <= 0) {
					gp.monster[i].dying = true;   // The monster is about to day. Start dying visual effect
					gp.ui.addMessage("Killed " + gp.monster[i].name + "!");
					gp.ui.addMessage("+ " + gp.monster[i].exp + " exp!");
					exp += gp.monster[i].exp;
					
					checkLevelUp();
				}
			}
	}
	
	/*
	 * Check if the player exp is high enough to level up
	 */
	public void checkLevelUp() {
		if (exp >= nextLevelExp) {
			level ++;
			nextLevelExp *= 2;   // Increase next level exp necessary
			maxLife += 2;   // Increase max life 
			strength ++;
			dexterity ++;
			attack = getAttack();     // Recalculate the player attack
			defence = getDefence();   // and defence
			
			gp.playSoundEffect(8);
			gp.gameState = gp.dialogState;
			gp.ui.currentDialog = "You are level " + level + " now!\n" + "You feel stronger but nothing will stop\nyou from paying your taxes!";
		}
	}
	
	/*
	 * Handle the player attacking behaviour and animation 
	 */
	public void attacking () {
		spriteCounter ++;
		
		// Attack animation
		// Weapon in
		if (spriteCounter <= 5)
			spriteNumber = 1;
		// Weapon out
		if (spriteCounter > 5 && spriteCounter<= 25) {
			spriteNumber = 2;
			
			// Store current player position and solid area. It will be used temporary for the attacking area and then swapped back to its origin
			int currentWorldX = worldX;   // Store current player x and y position
			int currentWorldY = worldY;   //
			int currentSolidAreaWidth = solidArea.width;     // Store current solid area x and y position
			int currentSolidAreaHeight = solidArea.height;   //
			
			// Temporary offset player x and y position by his attack area
			switch (direction) {
				case "up": worldY -= attackArea.height; break;
				case "down": worldY += attackArea.height; break;
				case "left": worldX -= attackArea.width; break;
				case "right": worldX += attackArea.width; break;
			}
			
			// Temporary change the player's solid area size to his weapon attacking area size 
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			// Check monster collision with updated worldX worldY and solid area
			int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);   // Check which monster got hit by the player
			damageMonster(monsterIndex);
			
			// After checking collision, restore original player position and solid area size
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = currentSolidAreaWidth;
			solidArea.height = currentSolidAreaHeight;
		}
		
		// Weapon in
		if (spriteCounter > 25) {
			spriteNumber = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	/*
	 * Update player information such as position
	 */
	@Override
	public void update() {
		if (attacking) {
			attacking();
		}
		
		// Get keyboard strokes and update player position and allow interactions by only pressing the enter key
		else if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.enterPressed) {
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
			
			if (gp.keyHandler.enterPressed && !attackCancelled)  {
//				gp.playSoundEffect(7);
				attacking = true;
				spriteCounter = 0;
			}
			
			attackCancelled = false;
			gp.keyHandler.enterPressed = false;
			
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
		int tempScreenX = SCREEN_X;   // Used to reset the image position on the screen when the player attacks leftward and upward
		int tempScreenY = SCREEN_Y;   //
		
		if (direction == "up") {
			if (!attacking) {
				if (spriteNumber == 1) image = up1;
				if (spriteNumber == 2) image = up2;
			}
			
			if (attacking) {
				tempScreenY = SCREEN_Y - gp.TILE_SIZE;   // Adjust the player position on the screen to move the weapon upward and keep the player in the same position
				if (spriteNumber == 1) image = attackUp1;
				if (spriteNumber == 2) image = attackUp2;
			}
		}
		
		if (direction == "down") {
			if (!attacking) {
				if (spriteNumber == 1) image = down1;
				if (spriteNumber == 2) image = down2;
			}
			
			if (attacking) {
				if (spriteNumber == 1) image = attackDown1;
				if (spriteNumber == 2) image = attackDown2;
			}
		}
		
		if (direction == "left") {
			if (!attacking) {
				if (spriteNumber == 1) image = left1;
				if (spriteNumber == 2) image = left2;
			}
			
			if (attacking) {
				tempScreenX = SCREEN_X - gp.TILE_SIZE;   // Adjust the player position on the screen to move the weapon leftward and keep the player in the same position
				if (spriteNumber == 1) image = attackLeft1;
				if (spriteNumber == 2) image = attackLeft2;
			}
		}
		
		if (direction == "right") {
			if (!attacking) {
				if (spriteNumber == 1) image = right1;
				if (spriteNumber == 2) image = right2;
			}
			
			if (attacking) {
				if (spriteNumber == 1) image = attackRight1;
				if (spriteNumber == 2) image = attackRight2;
			}
		}
		
		// When the player receives damage it he becomes slightly transparent for a moment to show a visual effect of the damage
		if (invincible)
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));   // Change the alpha level of the player sprite 
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		// Reset the transparency of the player sprite
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		// Debug
		g2.setColor(Color.red);
		g2.drawRect(SCREEN_X + solidArea.x, SCREEN_Y + solidArea.y, solidArea.width, solidArea.height);
		// Attack solid area
		int ttempScreenX = SCREEN_X + solidArea.x;
		int ttempScreenY = SCREEN_Y + solidArea.y;		
		switch(direction) {
		case "up": ttempScreenY = SCREEN_Y - attackArea.height; break;
		case "down": ttempScreenY = SCREEN_Y + gp.TILE_SIZE; break; 
		case "left": ttempScreenX = SCREEN_X - attackArea.width; break;
		case "right": ttempScreenX = SCREEN_X + gp.TILE_SIZE; break;
		}				
		g2.setColor(Color.blue);
        g2.setStroke(new BasicStroke(1));
		g2.drawRect(ttempScreenX, ttempScreenY, attackArea.width, attackArea.height);
		
	}
}
