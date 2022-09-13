package zhrfrd.adventure2.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import zhrfrd.adventure2.main.GamePanel;
import zhrfrd.adventure2.main.KeyHandler;
import zhrfrd.adventure2.main.UtilityTool;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyHandler;
	public final int SCREEN_X, SCREEN_Y;   // Position of the player of the screen
//	public int keyCount = 0;
	boolean moving = false;
	int pixelCounter = 0;
	
	public Player(GamePanel gp, KeyHandler keyHandler) {
		this.gp = gp;
		this.keyHandler = keyHandler;
		SCREEN_X = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);    // Player in the middle of the screen
		SCREEN_Y = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);   //
		
		solidArea = new Rectangle(1, 1, 46, 46);   // Rectangle solid area of the player
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
		up1 = setup("player_up_1");
		up2 = setup("player_up_2");
		down1 = setup("player_down_1");
		down2 = setup("player_down_2");
		left1 = setup("player_left_1");
		left2 = setup("player_left_2");
		right1 = setup("player_right_1");
		right2 = setup("player_right_2");
	}
	
	/*
	 * Handle instantiation, import of images and scaling
	 */
	public BufferedImage setup(String imageName) {
		UtilityTool utilityTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
			image = utilityTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	/*
	 * Update player information such as position
	 */
	public void update() {
		// Tile based movement. The player doesn't stop until it reaches the end of the tile
		if (!moving) {
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
				
				moving = true;
				collisionOn = false;
				gp.collisionChecker.checkTile(this);   // Check the tile collision
				
				int objIndex = gp.collisionChecker.checkObject(this, true);   // Check if player collided with an object and save the object index
				
				pickUpObject(objIndex);	
			}
			
			// When the player stops moving, reset the sprite to the default one
			else
				spriteNumber = 1;
		}
		
		else {
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
				
				else
					spriteNumber = 1;
				
				spriteCounter = 0;
			}
			
			pixelCounter += speed;
			
			// Stop player movement only when it reaches the end of the tile
			if (pixelCounter == 48) {
				moving = false;
				pixelCounter = 0;
			}
		}
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
	}
}
