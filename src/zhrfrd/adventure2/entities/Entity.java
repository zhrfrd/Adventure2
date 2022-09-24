package zhrfrd.adventure2.entities;

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
	public int worldX, worldY;   // Position of the entity in the world map
	public int speed;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public String direction = "down";
	public int spriteCounter = 0;
	public int spriteNumber = 1;
	public Rectangle solidArea = new Rectangle(0, 0, 48,48);   // Default solid area for the collision detection
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public int actionLockCounter = 0;   // Interval for the npc movement
	String dialogs[] = new String[20];
	int dialogIndex = 0;
	public BufferedImage image, image2, image3;
	public String name;
	public boolean solid = false;
	public int type;   // Type of entity (eg. player, npc, monster ...)
	// Player status
	public int maxLife, life;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	/*
	 * Set entity behaviour such as movements
	 */
	public void setAction() {}
	
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
		if (this.type == 2 && contactPlayer)
			if (!gp.player.invincible) {
				gp.player.life --;
				gp.player.invincible = true;
			}
		
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
	}
	
	/*
	 * Draw the entity in the game panel
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
			
			g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
			g2.setColor(Color.red);
			g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
		}
		
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
