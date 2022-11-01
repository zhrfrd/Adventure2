package zhrfrd.adventure2.main;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][][];
	// If an event happen, it won't happen again until the player moves away from
	// the event tile
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;

	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventRect = new EventRect[gp.MAX_MAP][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

		int map = 0;
		int col = 0;
		int row = 0;

		// Assign a event rectangle to each tile of the map
		while (map < gp.MAX_MAP && col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height = 2;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

			col++;

			// Create event rectangle for each map
			if (col == gp.MAX_WORLD_COL) {
				col = 0;
				row++;
				
				if (row == gp.MAX_WORLD_ROW) {
					row = 0;
					map ++;
				}
			}
		}
	}

	/**
	 * Check which event happens such as player hitting a pit or teleport.
	 * 
	 */
	public void checkEvent() {

		// Calculate absolute value of the distance between player last event rectangle
		int distanceX = Math.abs(gp.player.worldX - previousEventX);
		int distanceY = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(distanceX, distanceY);

		// Check if the player is more than 1 tile away from the last event rectangle
		if (distance > gp.TILE_SIZE)
			canTouchEvent = true;

		if (canTouchEvent) {
			if (hit(0, 27, 16, "right")) damagePit(gp.DIALOG_STATE);
			else if (hit(0, 23, 12, "up")) healingPool(gp.DIALOG_STATE);
			else if (hit(0, 10, 39, "any")) teleport(1, 12, 13);   // Enter hut from map 1
			else if (hit(1, 12, 13, "any")) teleport(0, 10, 39);   // Exit the hut and go back to map 1
		}
	}

	/**
	 * Check event collision such as teleport, pit ...
	 * 
	 * @param col               The columns position in the grid map of the event.
	 * @param row               The row position in the grid map of the event.
	 * @param requiredDirection Required direction for the event to be called.
	 * @return True if the player hits the event, false otherwise.
	 */
	public boolean hit(int map, int col, int row, String requiredDirection) {
		boolean hit = false;

		if (map == gp.currentMap) {
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			eventRect[map][col][row].x = col * gp.TILE_SIZE + eventRect[map][col][row].x;
			eventRect[map][col][row].y = row * gp.TILE_SIZE + eventRect[map][col][row].y;

			if (gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone)
				if (gp.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
					hit = true;

					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}

			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		}
		
		return hit;
	}

	/**
	 * Decrease player's life and display dialog message when player hits a pit.
	 * 
	 * @param gameState The game state at which the event is called.
	 */
	public void damagePit(int gameState) {
		gp.gameState = gameState;
		gp.playSoundEffect(6);
		gp.ui.currentDialog = "You fall into a pit!";
		gp.player.life--;
		canTouchEvent = false;
	}

	/**
	 * The healing pool regenerates the player's life and mana.
	 * 
	 * @param col       The columns position in the grid map of the healing pool
	 *                  event.
	 * @param row       The row position in the grid map of the healing pool event.
	 * @param gameState The game state at which the event is called.
	 */
	public void healingPool(int gameState) {
		if (gp.keyHandler.enterPressed) {
			gp.gameState = gameState;
			gp.player.attackCancelled = true;
			gp.playSoundEffect(2);
			gp.ui.currentDialog = "This is a pond.\nDespite the amount of mosquitoes flying\naround and its nasty smell, I'm going to drink\nsome of this water and hope for my best!";
			gp.player.life = gp.player.maxLife;
			gp.player.mana = gp.player.maxMana;
			gp.assetSetter.setMonster(); // Respawn monsters when drinking the water
		}
	}
	
	/**
	 * Teleport to another are of the current map or another map specified.
	 * 
	 * @param map Index of the map target.
	 * @param col Column position in the map.
	 * @param row Row position in the map.
	 */
	public void teleport(int map, int col, int row) {
		gp.currentMap = map;
		gp.player.worldX = gp.TILE_SIZE * col;
		gp.player.worldY = gp.TILE_SIZE * row;
		// The teleport event will stop as soon the player teleport to a specific area to avoid going in a teleport loop. It will be reactivated only when the player moves.
		previousEventX = gp.player.worldX;
		previousEventY = gp.player.worldY;
		canTouchEvent = false;
		gp.playSoundEffect(13);
	}
}
