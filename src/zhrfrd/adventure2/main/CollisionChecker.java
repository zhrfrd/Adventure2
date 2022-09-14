package zhrfrd.adventure2.main;

import zhrfrd.adventure2.entities.Entity;

public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	/*
	 * Check tile for collision detection and pass the entity that collide with the tile
	 */
	public void checkTile(Entity entity) {
		// Calculate the position in the world of the 4 sides of the entity solid area (left, right, top, bottom)
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		// Calculate the column and row position of the solid area of the entity
		int entityLeftCol = entityLeftWorldX / gp.TILE_SIZE;
		int entityRightCol = entityRightWorldX / gp.TILE_SIZE;
		int entityTopRow = entityTopWorldY / gp.TILE_SIZE;
		int entityBottomRow = entityBottomWorldY / gp.TILE_SIZE;
		// You need to check only two tiles for each direction the entity goes
		int tileNum1, tileNum2;
		
		if (entity.direction == "up") {
			entityTopRow = (entityTopWorldY - entity.speed) / gp.TILE_SIZE;   // Predict the next tile the entity will move to
			tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];    // Get the position of the two tiles in front of the entity direction
			tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];   //
			
			//Check if the two tiles in front of the entity direction are solid and in case they are detect the collision
			if (gp.tileManager.tile[tileNum1].solid || gp.tileManager.tile[tileNum2].solid)
				entity.collisionOn = true;
		}
		
		if (entity.direction == "down") {
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.TILE_SIZE;   // Predict the next tile the entity will move to
			tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];    // Get the position of the two tiles in front of the entity direction
			tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];   //
			
			//Check if the two tiles in front of the entity direction are solid and in case they are detect the collision
			if (gp.tileManager.tile[tileNum1].solid || gp.tileManager.tile[tileNum2].solid)
				entity.collisionOn = true;
		}
		
		if (entity.direction == "left") {
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.TILE_SIZE;   // Predict the next tile the entity will move to
			tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];    // Get the position of the two tiles in front of the entity direction
			tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];   //
			
			//Check if the two tiles in front of the entity direction are solid and in case they are detect the collision
			if (gp.tileManager.tile[tileNum1].solid || gp.tileManager.tile[tileNum2].solid)
				entity.collisionOn = true;
		}
		
		if (entity.direction == "right") {
			entityRightCol = (entityRightWorldX + entity.speed) / gp.TILE_SIZE;   // Predict the next tile the entity will move to
			tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];    // Get the position of the two tiles in front of the entity direction
			tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];   //
			
			//Check if the two tiles in front of the entity direction are solid and in case they are detect the collision
			if (gp.tileManager.tile[tileNum1].solid || gp.tileManager.tile[tileNum2].solid)
				entity.collisionOn = true;
		}
	}
	
	/*
	 * Check object for collision detection and check if the entity that collided with the object is a player or not. Return the index of the object in order to process the reaction accordingly
	 */
	public int checkObject(Entity entity, boolean player) {
		int index = 999;   // Default index (not associated to any object)
		
		for (int i = 0; i < gp.obj.length; i ++) {
			if (gp.obj[i] != null) {
				entity.solidArea.x = entity.worldX + entity.solidArea.x;   // Get entity's solid area position 
				entity.solidArea.y = entity.worldY + entity.solidArea.y;   //
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;   // Get object solid area position
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;   //
				
				// Predict solid area position to check if it will collide with an object
				if (entity.direction == "up") {
					entity.solidArea.y -= entity.speed;
					
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].solid)
							entity.collisionOn = true;
						
						if (player)   // Only player can interact with the object.
							index = i;
					}	
				}
				
				if (entity.direction == "down") {
					entity.solidArea.y += entity.speed;
					
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].solid)
							entity.collisionOn = true;
						
						if (player)   // Only player can interact with the object.
							index = i;
					}	
				}
				
				if (entity.direction == "left") {
					entity.solidArea.x -= entity.speed;
					
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].solid)
							entity.collisionOn = true;
						
						if (player)   // Only player can interact with the object. index is used to indicate that an object has been picked up by the player
							index = i;
					}	
				}
				
				if (entity.direction == "right") {
					entity.solidArea.x -= entity.speed;
					
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].solid)
							entity.collisionOn = true;
						
						if (player)   // Only player can interact with the object.
							index = i;
					}	
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;         // Reset the solid area position to the default value (remember that NOW solidArea position is the one predicted for the imminent collision with an object)
				entity.solidArea.y = entity.solidAreaDefaultY;         //
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;   //
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;   //
			}
		}
		
		return index;
	}
	
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;   // Default index (not associated to any object)
		
		for (int i = 0; i < gp.obj.length; i ++) {
			if (target[i] != null) {
				entity.solidArea.x = entity.worldX + entity.solidArea.x;   // Get entity's solid area position 
				entity.solidArea.y = entity.worldY + entity.solidArea.y;   //
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;   // Get entity's solid area position
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;   //
				
				// Predict solid area position to check if it will collide with an entity
				if (entity.direction == "up") {
					entity.solidArea.y -= entity.speed;
					
					if (entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn = true;
						index = i;
					}	
				}
				
				if (entity.direction == "down") {
					entity.solidArea.y += entity.speed;
					
					if (entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn = true;
						index = i;
					}	
				}
				
				if (entity.direction == "left") {
					entity.solidArea.x -= entity.speed;
					
					if (entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn = true;
						index = i;
					}	
				}
				
				if (entity.direction == "right") {
					entity.solidArea.x -= entity.speed;
					
					if (entity.solidArea.intersects(target[i].solidArea)) {
						entity.collisionOn = true;
						index = i;
					}	
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;         // Reset the solid area position to the default value (remember that NOW solidArea position is the one predicted for the imminent collision with an entity)
				entity.solidArea.y = entity.solidAreaDefaultY;         //
				target[i].solidArea.x = target[i].solidAreaDefaultX;   //
				target[i].solidArea.y = target[i].solidAreaDefaultY;   //
			}
		}
		
		return index;
	}
	
	/*
	 * Handle collision from npc to player
	 */
	public void checkPlayer(Entity entity) {
		entity.solidArea.x = entity.worldX + entity.solidArea.x;   // Get player's solid area position 
		entity.solidArea.y = entity.worldY + entity.solidArea.y;   //
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;   // Get player's solid area position
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;   //
		
		// Predict solid area position to check if it will collide with the player
		if (entity.direction == "up") {
			entity.solidArea.y -= entity.speed;
			
			if (entity.solidArea.intersects(gp.player.solidArea)) {
				entity.collisionOn = true;
			}	
		}
		
		if (entity.direction == "down") {
			entity.solidArea.y += entity.speed;
			
			if (entity.solidArea.intersects(gp.player.solidArea)) {
				entity.collisionOn = true;
			}	
		}
		
		if (entity.direction == "left") {
			entity.solidArea.x -= entity.speed;
			
			if (entity.solidArea.intersects(gp.player.solidArea)) {
				entity.collisionOn = true;
			}	
		}
		
		if (entity.direction == "right") {
			entity.solidArea.x -= entity.speed;
			
			if (entity.solidArea.intersects(gp.player.solidArea)) {
				entity.collisionOn = true;
			}	
		}
		
		entity.solidArea.x = entity.solidAreaDefaultX;         // Reset the solid area position to the default value (remember that NOW solidArea position is the one predicted for the imminent collision with a player)
		entity.solidArea.y = entity.solidAreaDefaultY;         //
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;   //
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;   //
	}
}
