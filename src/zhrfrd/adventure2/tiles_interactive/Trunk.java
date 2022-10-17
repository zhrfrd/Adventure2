package zhrfrd.adventure2.tiles_interactive;

import java.awt.Rectangle;

import zhrfrd.adventure2.main.GamePanel;

public class Trunk extends InteractiveTile {
	GamePanel gp;
	
	public Trunk(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;

		down1 = setup("/tiles_interactive/trunk", gp.TILE_SIZE, gp.TILE_SIZE);
		
		// No solid area
		solidArea = new Rectangle(0, 0, 0, 0);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
