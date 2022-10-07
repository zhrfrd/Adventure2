package zhrfrd.adventure2.entities;

import zhrfrd.adventure2.main.GamePanel;

public class Projectile extends Entity {
	Entity user;
	
	public Projectile(GamePanel gp) {
		super(gp);
	}
	
	/*
	 * Generate the projectile in the world
	 */
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.life = this.maxLife;
	}
	
	/*
	 * Update the info and position of the projectile
	 */
	@Override
	public void update() {
		// Move in relation to the key pressed
		switch (direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
		}
		
		// Gradually decrease projectile's life until it reaches 0 (-1 life each game loop)
		life --;
		
		if (life <= 0)
			alive = false;
		
		// Switch sprites intermittently for the animation
		spriteCounter ++;
		
		if (spriteCounter > 12) {
			if (spriteNumber == 1)
				spriteNumber = 2;
			
			else if (spriteNumber == 2)
				spriteNumber = 1;
			
			spriteCounter = 0;
		}
	}
}
