package zhrfrd.adventure2.main;

import zhrfrd.adventure2.entities.Entity;

public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	/**
	 * Check tile for collision detection and pass the entity that collide with the tile.
	 * 
	 * @param entity The entity that collided with the tile.
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
			tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];    // Get the position of the two tiles in front of the entity direction
			tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];   //
			
			//Check if the two tiles in front of the entity direction are solid and in case they are detect the collision
			if (gp.tileManager.tile[tileNum1].solid || gp.tileManager.tile[tileNum2].solid)
				entity.collisionOn = true;
		}
		
		if (entity.direction == "down") {
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.TILE_SIZE;   // Predict the next tile the entity will move to
			tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];    // Get the position of the two tiles in front of the entity direction
			tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];   //
			
			//Check if the two tiles in front of the entity direction are solid and in case they are detect the collision
			if (gp.tileManager.tile[tileNum1].solid || gp.tileManager.tile[tileNum2].solid)
				entity.collisionOn = true;
		}
		
		if (entity.direction == "left") {
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.TILE_SIZE;   // Predict the next tile the entity will move to
			tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];    // Get the position of the two tiles in front of the entity direction
			tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];   //
			
			//Check if the two tiles in front of the entity direction are solid and in case they are detect the collision
			if (gp.tileManager.tile[tileNum1].solid || gp.tileManager.tile[tileNum2].solid)
				entity.collisionOn = true;
		}
		
		if (entity.direction == "right") {
			entityRightCol = (entityRightWorldX + entity.speed) / gp.TILE_SIZE;   // Predict the next tile the entity will move to
			tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];    // Get the position of the two tiles in front of the entity direction
			tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];   //
			
			//Check if the two tiles in front of the entity direction are solid and in case they are detect the collision
			if (gp.tileManager.tile[tileNum1].solid || gp.tileManager.tile[tileNum2].solid)
				entity.collisionOn = true;
		}
	}
	
	/**
	 * Check object for collision detection and check if the entity that collided with the object is a player or not.
	 * 
	 * @param entity The entity that collided with the object.
	 * @param player Return if the entity is the player, false otherwise.
	 * @return The index of the object in order to process the reaction accordingly.
	 */
	public int checkObject(Entity entity, boolean player) {
		int index = 999;   // Default index (not associated to any object)
		
		for (int i = 0; i < gp.obj[1].length; i ++) {
			if (gp.obj[gp.currentMap][i] != null) {
				entity.solidArea.x = entity.worldX + entity.solidArea.x;   // Get entity's solid area position 
				entity.solidArea.y = entity.worldY + entity.solidArea.y;   //
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;   // Get object solid area position
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;   //
				
				// Predict solid area position to...
				if (entity.direction == "up")
					entity.solidArea.y -= entity.speed;
				
				if (entity.direction == "down")
					entity.solidArea.y += entity.speed;
				
				if (entity.direction == "left")
					entity.solidArea.x -= entity.speed;
				
				if (entity.direction == "right")
					entity.solidArea.x += entity.speed;
				
				// ...check if it will collide with an object
				if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
					if (gp.obj[gp.currentMap][i].solid)
						entity.collisionOn = true;
					
					if (player)   // Only player can interact with the object.
						index = i;
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;         // Reset the solid area position to the default value (remember that NOW solidArea position is the one predicted for the imminent collision with an object)
				entity.solidArea.y = entity.solidAreaDefaultY;         //
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;   //
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;   //
			}
		}
		
		return index;
	}
	
	/**
	 * Check entity for collision detection.
	 * 
	 * @param entity The entity considered that collided to the entity target.
	 * @param target The entity target that received collision from the entity.
	 * @return Return the index of the object in entity to process the reaction accordingly.
	 */
	public int checkEntity(Entity entity, Entity[][] target) {
		int index = 999;   // Default index (not associated to any object)
		
		for (int i = 0; i < target[1].length; i ++) {
			if (target[gp.currentMap][i] != null) {
				entity.solidArea.x = entity.worldX + entity.solidArea.x;   // Get entity's solid area position 
				entity.solidArea.y = entity.worldY + entity.solidArea.y;   //
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;   // Get target entity's solid area position
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;   //
				
				// Predict solid area position to...
				if (entity.direction == "up")
					entity.solidArea.y -= entity.speed;
				
				if (entity.direction == "down")
					entity.solidArea.y += entity.speed;
				
				if (entity.direction == "left")
					entity.solidArea.x -= entity.speed;
				
				if (entity.direction == "right")
					entity.solidArea.x += entity.speed;
				
				// ...check if it will collide with an entity
				if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
					// Add collision only if the two entities are different, otherwise one entity will identify itself as a collision target and it will not move
					if (target[gp.currentMap][i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
				}	
				
				entity.solidArea.x = entity.solidAreaDefaultX;         // Reset the solid area position to the default value (remember that NOW solidArea position is the one predicted for the imminent collision with an entity)
				entity.solidArea.y = entity.solidAreaDefaultY;         //
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;   //
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;   //
			}
		}
		
		return index;
	}
	
	/**
	 * Handle collision from entity to player.
	 * 
	 * @param entity The entity that collided with the player.
	 * @return True if there is collision, false otherwise.
	 */
	public boolean checkPlayer(Entity entity) {
		boolean  contactPlayer = false;
		
		entity.solidArea.x = entity.worldX + entity.solidArea.x;   // Get entity's solid area position 
		entity.solidArea.y = entity.worldY + entity.solidArea.y;   //
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;   // Get player's solid area position
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;   //
		
		// Predict solid area position to check if it will collide with the player
		if (entity.direction == "up")
			entity.solidArea.y -= entity.speed;
		
		if (entity.direction == "down")
			entity.solidArea.y += entity.speed;
		
		if (entity.direction == "left")
			entity.solidArea.x -= entity.speed;
		
		if (entity.direction == "right")
			entity.solidArea.x -= entity.speed;
		
		if (entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
			contactPlayer = true;
		}
		
		entity.solidArea.x = entity.solidAreaDefaultX;         // Reset the solid area position to the default value (remember that NOW solidArea position is the one predicted for the imminent collision with a player)
		entity.solidArea.y = entity.solidAreaDefaultY;         //
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;   //
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;   //
		
		return contactPlayer;
	}
}
