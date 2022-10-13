package zhrfrd.adventure2.entities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import zhrfrd.adventure2.main.GamePanel;
import zhrfrd.adventure2.main.UtilityTool;

public class Entity {
	GamePanel gp;
	
	public int speed;
	public BufferedImage image, image2, image3;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public Rectangle solidArea = new Rectangle(0, 0, 48,48);   // Default solid area for the collision detection
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);   // Default attack area
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean solid = false;
	String dialogs[] = new String[20];
	// State
	public int worldX, worldY;   // Position of the entity in the world map
	public String direction = "down";
	public int spriteNumber = 1;
	int dialogIndex = 0;
	public boolean collisionOn = false;
	public boolean invincible = false;
	boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn = false;
	// Counter
	public int spriteCounter = 0;
	public int invincibleCounter = 0;
	public int actionLockCounter = 0;   // Interval for the npc movement
	public int reloadProjectileCounter = 0;   // Reload for shooting another projectile
	public int dyingCounter = 0;
	int hpBarCounter = 0;
	// Character
	public String name;
	public int maxLife, life;
	public int maxMana, mana;
	public int ammo;
	public int level;
	public int strength;   // More strength = gives more damage
	public int dexterity;   // More dexterity = receive less damage
	public int attack;
	public int defence;
	public int exp;
	public int nextLevelExp;   // Exp necessary to reach next level
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	public Projectile projectile;
	// Item attributes
	public int value;
	public int attackValue;
	public int defenceValue;
	public String description = "";
	public int useCost;   // Mana cost to shoot a projectile
	// Type
	public int type;   // Type of entity (eg. player, npc, monster ...)
	public final int TYPE_PLAYER = 0;
	public final int TYPE_NPC = 1;
	public final int TYPE_MONSTER = 2;
	public final int TYPE_SWORD = 3;
	public final int TYPE_AXE = 4;
	public final int TYPE_SHIELD = 5;
	public final int TYPE_CONSUMABLE = 6;
	public final int TYPE_PICKUP_ONLY = 7;   // Items that can be picked up but not stored inside the inventory such as coins
	
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	/*
	 * Set entity behaviour such as movements
	 */
	public void setAction() {}
	
	/*
	 * React when receiving damage
	 */
	public void damageReaction() {}
	
	/*
	 * Use item selected such as potions
	 */
	public void use(Entity entity) {}
	
	/**
	 * Check which item will be dropped by an entity
	 */
	public void checkDrop() {}
	
	/**
	 * Drop item on the location of the entity that dropped it.
	 * 
	 * @param droppedItem Item that will be dropped by the entity
	 */
	public void dropItem(Entity droppedItem) {
		// Add dropped item to the obj array.
		for (int i = 0; i < gp.obj.length; i ++) {
			if (gp.obj[i] == null) {
				gp.obj[i] = droppedItem;
				gp.obj[i].worldX = worldX;
				gp.obj[i].worldY = worldY;
				break;   // Exit the loop, otherwise all the empty array slots will be filled by the dropped item
			}
		}
	}
	
	/*
	 * Make the entity speak
	 */
	public void speak() {
		// Reset the dialog to the beginning when the old man completes all his lines
		if (dialogs[dialogIndex] == null)
			dialogIndex = 0;
		
		gp.ui.currentDialog = dialogs[dialogIndex];
		dialogIndex ++;   // Change line each time the player interacts with the old man
		
		// When the player talks to the old man, change the old man direction towards the player
		switch (gp.player.direction) {
		case "up":
			direction = "down";
			break;
			
		case "down":
			direction = "up";
			break;
			
		case "left":
			direction = "right";
			break;
			
		case "right":
			direction = "left";
			break;
		}
	}
	
	/*
	 * Update entity status
	 */
	public void update() {
		setAction();
		
		collisionOn = false;
		gp.collisionChecker.checkTile(this);
		gp.collisionChecker.checkObject(this, false);
		gp.collisionChecker.checkEntity(this, gp.npc);
		gp.collisionChecker.checkEntity(this, gp.monster);
		boolean contactPlayer = gp.collisionChecker.checkPlayer(this);
		
		// When the entity moving is a slime and it touches the player, the player receives damage
		if (this.type == TYPE_MONSTER && contactPlayer)
			damagePlayer(attack);
		
		// Move the entity only in case there is no collision detected
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
			
			else
				spriteNumber = 1;
			
			spriteCounter = 0;
		}
		
		// Add temporary invincibility to entity 
		if (invincible) {
			invincibleCounter ++;
			
			if (invincibleCounter > 40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		// Reload time for a new projectile is 30 frames (1/2 second)
		if (reloadProjectileCounter < 30) {
			reloadProjectileCounter ++;
		}
	}
	
	/**
	 * Handle the damaging of the player when the entity attacks him.
	 * @param attack Attack value of the entity used to calculate the damage to the player.
	 */
	public void damagePlayer(int attack) {
		if (!gp.player.invincible) {
			int damage = attack - gp.player.defence;
			
			if (damage < 0)
				damage = 0;
			
			gp.playSoundEffect(6);
			gp.player.life -= damage;
			gp.player.invincible = true;
		}
	}
	
	/**
	 * Draw any entity in the game panel using Graphics2D. The drawing will be done every game update
	 * @param g2 Graphics2D responsible for drawing.
	 */
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;   // Entity position on the screen
		int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;   //
		
		// Render only the entities visible on the screen plus one extra tile in order to not show black edges when moving
		if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X && 
				worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X && 
				worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y && 
				worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
			
			switch (direction) {
				case "up":
					if (spriteNumber == 1) image = up1;
					if (spriteNumber == 2) image = up2;
					break;
				case "down":
					if (spriteNumber == 1) image = down1;
					if (spriteNumber == 2) image = down2; 
					break;
				case "left":
					if (spriteNumber == 1) image = left1;
					if (spriteNumber == 2) image = left2;
					break;
				case "right":
					if (spriteNumber == 1) image = right1;
					if (spriteNumber == 2) image = right2;
					break;
			}
			
			// Monster HP bar
			if (type == 2 && hpBarOn) {
				// Scale the health bar in order to contain the monster life
				double oneScale = (double)gp.TILE_SIZE / maxLife;
				double hpBarValue = oneScale * life; 
				
				g2.setColor(new Color(35, 35, 35));
				g2.fillRect(screenX - 1, screenY - 11, gp.TILE_SIZE + 2, 12);
				g2.setColor(new Color(255, 0, 30));
				g2.fillRect(screenX, screenY - 10, (int)hpBarValue, 10);
				
				hpBarCounter ++;
				
				// Hide health bar after 10 seconds
				if (hpBarCounter == 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			
			// When the entity receives damage it he becomes slightly transparent for a moment to show a visual effect of the damage
			if (invincible) {
				hpBarOn = true;   // Display health bar
				hpBarCounter = 0;
				changeAlpha(g2, 0.4f);   // Change the alpha level of the entity sprite 
			}
			// Start dying animation
			if (dying)
				dyingAnimation(g2);
			
			
			g2.drawImage(image, screenX, screenY, null);
			
			// Reset the transparency of the entity sprite
			changeAlpha(g2, 1f); 
			
			g2.setColor(Color.red);
			g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
		}
		
	}
	
	/*
	 * Animation when the entity is about to die by intermittently changing the alpha level of its sprite
	 */
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter ++;
		int i = 5;
		
		if (dyingCounter <= i) changeAlpha(g2, 0f);
		if (dyingCounter > i && dyingCounter <= i * 2) changeAlpha(g2, 1f);
		if (dyingCounter > i * 2 && dyingCounter <= i * 3) changeAlpha(g2, 0f);
		if (dyingCounter > i * 3 && dyingCounter <= i * 4) changeAlpha(g2, 1f);
		if (dyingCounter > i * 4 && dyingCounter <= i * 5) changeAlpha(g2, 0f);
		if (dyingCounter > i * 5 && dyingCounter <= i * 6) changeAlpha(g2, 1f);
		if (dyingCounter > i * 6 && dyingCounter <= i * 7) changeAlpha(g2, 0f);
		if (dyingCounter > i * 7 && dyingCounter <= i * 8) changeAlpha(g2, 1f);
		if (dyingCounter > 40)
			alive = false;
	}
	
	/*
	 * Change transparency of a sprite
	 */
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	
	/*
	 * Handle instantiation, import of images and scaling
	 */
	public BufferedImage setup(String imagePath, int width, int height) {
		UtilityTool utilityTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = utilityTool.scaleImage(image, width, height);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
}
