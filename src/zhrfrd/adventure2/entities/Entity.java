package zhrfrd.adventure2.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	public int worldX, worldY;   // Position of the entity in the world map
	public int speed;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	public int spriteCounter = 0;
	public int spriteNumber = 1;
	public Rectangle solidArea;   // Solid area for the collision detection
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
}
