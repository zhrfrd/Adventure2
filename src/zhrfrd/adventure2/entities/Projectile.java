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
	
	/**
	 * Check if the user has enough mana to shoot a projectile.
	 * 
	 * @param user The entity that wants to shoot the projectile.
	 * @return True if the users has enough mana to shoot a fireball, false otherwise. This method will return false by default.
	 */
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		return haveResource;
	}
	
	/**
	 * Subtract the mana from the user when he uses it. This method is left blank by default because it's used inside some subclasses of projectile.
	 * @param user The entity that wants to shoot the projectile.
	 */
	public void subtractResource(Entity user) {
	}
	
	/**
	 * Update the info and position of the projectile each game update.
	 */
	@Override
	public void update() {
		// Check if the projectile is shot by the player
		if (user == gp.player) {
			int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);   // Get the index of the entity collided with the projectile
			
			if (monsterIndex != 999) {
				gp.player.damageMonster(monsterIndex, attack);
				generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
				alive = false;    // When the projectile hits the monster, the projectile disappears
			}
		}
			
		// Check if the projectile is shot by an entity different than the player
		if (user != gp.player) {
			boolean contactPlayer = gp.collisionChecker.checkPlayer(this);   // Check projectile collision with the player
			
			if (!gp.player.invincible && contactPlayer) {
				damagePlayer(attack);
				generateParticle(user.projectile, gp.player);
				alive = false;
			}
		}
		
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
