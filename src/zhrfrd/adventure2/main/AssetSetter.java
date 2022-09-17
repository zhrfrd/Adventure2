package zhrfrd.adventure2.main;

import zhrfrd.adventure2.entities.OldMan;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	/*
	 * Create and set position of the objects on the map
	 */
	public void setObject() {

	}
	
	/*
	 * Set all the npcs of the game
	 */
	public void setNPC() {
		gp.npc[0] = new OldMan(gp);
		gp.npc[0].worldX = gp.TILE_SIZE * 21;
		gp.npc[0].worldY = gp.TILE_SIZE * 21;
	}
}
