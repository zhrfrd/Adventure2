package zhrfrd.adventure2.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import zhrfrd.adventure2.main.GamePanel;

public class Particle extends Entity {
	Entity generator;   // Entity that produces this particle
	Color color;
	int size;
	int xDirection;   // Particle direction vector (eg. top-left: -1, -1; top-right: , -1; bottom-left: -1, 1; bottom-right: 1, 1)
	int yDirection;   //
	
	/**
	 * Particle generated when hitting a specific entity.
	 * 
	 * @param gp Game panel.
	 * @param generator Entity from where the particle is originated.
	 * @param color Color of the particle.
	 * @param size Size of the particle in pixels.
	 * @param speed Speed of the moving particle.
	 * @param maxLife Maximum life of the particle.
	 * @param xd Direction of the particle along the x axis (-1 or 1)
	 * @param yd Direction of the particle along the y axis (-1 or 1)
	 */
	public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xDirection, int yDirection) {
		super(gp);
		
		this.generator = generator;
		this.color = color;
		this.size = size;
		this.speed = speed;
		this.maxLife = maxLife;
		this.xDirection = xDirection;
		this.yDirection = yDirection;
		life = maxLife;
		int offset = (gp.TILE_SIZE / 2) - (size / 2);   // Add offset to particle worldX and worldY in order to center it to the tile from where it's originated
		worldX = generator.worldX + offset;
		worldY = generator.worldY + offset; 
	}
	@Override
	/**
	 * Update information relative to the particle to apply animation.
	 */
	public void update() {
		life --;
		
		// Add a little bit of physics to the falling particles
		if (life < maxLife / 3) 
			yDirection ++;
		
		worldX += xDirection * speed;
		worldY += yDirection * speed;
		
		if (life == 0) 
			alive = false;
	}
	
	/**
	 * Draw the particle on the map.
	 */
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
		int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;
		g2.setColor(color);
		g2.fillRect(screenX, screenY, size, size);
	}
}
