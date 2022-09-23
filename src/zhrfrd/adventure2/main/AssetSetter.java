package zhrfrd.adventure2.main;

import zhrfrd.adventure2.entities.OldMan;
import zhrfrd.adventure2.monsters.Slime;

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
		
//		gp.npc[0] = new OldMan(gp);
//		gp.npc[0].worldX = gp.TILE_SIZE * 9;
//		gp.npc[0].worldY = gp.TILE_SIZE * 10;
	}
	
	/*
	 * Set all the monsters of the game
	 */
	public void setMonster() {
		gp.monster[0] = new Slime(gp);
		gp.monster[0].worldX = gp.TILE_SIZE * 23;
		gp.monster[0].worldY = gp.TILE_SIZE * 36;
		
		gp.monster[1] = new Slime(gp);
		gp.monster[1].worldX = gp.TILE_SIZE * 23;
		gp.monster[1].worldY = gp.TILE_SIZE * 37;
		
//		gp.monster[0] = new Slime(gp);
//		gp.monster[0].worldX = gp.TILE_SIZE * 11;
//		gp.monster[0].worldY = gp.TILE_SIZE * 10;
//		
//		gp.monster[1] = new Slime(gp);
//		gp.monster[1].worldX = gp.TILE_SIZE * 11;
//		gp.monster[1].worldY = gp.TILE_SIZE * 11;
		
	}
}
