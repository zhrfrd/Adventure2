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
	public String direction;
	public int spriteCounter = 0;
	public int spriteNumber = 1;
	public Rectangle solidArea = new Rectangle(0, 0, 46,46);   // Default solid area for the collision detection
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public int actionLockCounter = 0;   // Interval for the npc movement
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	/*
	 * Set character behaviour such as movements
	 */
	public void setAction() {
		
	}
	
	/*
	 * Update entity status
	 */
	public void update() {
		setAction();
		
		collisionOn = false;
		gp.collisionChecker.checkTile(this);
		gp.collisionChecker.checkObject(this, false);
		gp.collisionChecker.checkPlayer(this);
		
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
		int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;   // Entity position on the screen
		int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;   //
		BufferedImage image = null;
		
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
	public BufferedImage setup(String imagePath) {
		UtilityTool utilityTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = utilityTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
}
