package zhrfrd.adventure2.main;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][];
	// If an event happen, it won't happen again until the player moves away from
	// the event tile
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;

	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventRect = new EventRect[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

		int col = 0;
		int row = 0;

		// Assign a event rectangle to each tile of the map
		while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

			col++;

			if (col == gp.MAX_WORLD_COL) {
				col = 0;
				row++;
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
			if (hit(27, 16, "right"))
				damagePit(27, 16, gp.dialogState);
			if (hit(23, 12, "up"))
				healingPool(23, 12, gp.dialogState);
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
	public boolean hit(int col, int row, String requiredDirection) {
		boolean hit = false;

		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect[col][row].x = col * gp.TILE_SIZE + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.TILE_SIZE + eventRect[col][row].y;

		if (gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone)
			if (gp.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
				hit = true;

				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}

		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

		return hit;
	}

	/**
	 * Decrease player's life and display dialog message when player hits a pit.
	 * 
	 * @param col       The columns position in the grid map of the damaging pit
	 *                  event.
	 * @param row       The row position in the grid map of the damaging pit event.
	 * @param gameState The game state at which the event is called.
	 */
	public void damagePit(int col, int row, int gameState) {
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
	public void healingPool(int col, int row, int gameState) {
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
}
