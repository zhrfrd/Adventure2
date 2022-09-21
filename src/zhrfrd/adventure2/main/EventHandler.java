package zhrfrd.adventure2.main;

import java.awt.Rectangle;

public class EventHandler {
	GamePanel gp;
	Rectangle eventRect;
	int eventRectDefaultX, eventRectDefaultY;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventRect = new Rectangle(23, 23, 2, 2);
		eventRectDefaultX = eventRect.x;
		eventRectDefaultY = eventRect.y;
	}
	
	/*
	 * Check which event happens such as player hitting a pit or teleport
	 */
	public void checkEvent() {
		if (hit(27, 16, "right")) damagePit(gp.dialogState);
		
		if (hit(27, 16, "right")) teleport(gp.dialogState);
		
		if (hit(23, 12, "up")) healingPool(gp.dialogState);
	}
	
	/*
	 * Check event collision such as teleport, pit ...
	 */
	public boolean hit(int eventCol, int eventRow, String requiredDirection) {
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect.x = eventCol * gp.TILE_SIZE + eventRect.x;
		eventRect.y = eventRow * gp.TILE_SIZE + eventRect.y;
		
		if (gp.player.solidArea.intersects(eventRect))
			if (gp.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any"))
				hit = true;
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect.x = eventRectDefaultX;
		eventRect.y = eventRectDefaultY;
		
		return hit;
	}
	
	/*
	 * Teleport player to another area of the map
	 */
	public void teleport(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialog = "Wait what! Where am I? ";
		gp.player.worldX = gp.TILE_SIZE * 37;
		gp.player.worldY = gp.TILE_SIZE * 10;
	}
	
	/*
	 * Decrease player's life and display dialog message when player hits a pit 
	 */
	public void damagePit(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialog = "You fall into a pit!";
		gp.player.life --;
	}
	
	public void healingPool(int gameState) {
		if (gp.keyHandler.enterPressed) {
			gp.gameState = gameState;
			gp.ui.currentDialog = "This is a pond.\nDespite the amount of mosquitoes flying\naround and its nasty smell, I'm going to drink\nsome of this water and hope for my best!";
			gp.player.life = gp.player.maxLife;
		}
	}
}
