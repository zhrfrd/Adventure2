package zhrfrd.adventure2.main;

import zhrfrd.adventure2.entities.OldMan;
import zhrfrd.adventure2.monsters.Slime;
import zhrfrd.adventure2.objects.Axe;
import zhrfrd.adventure2.objects.CoinBronze;
import zhrfrd.adventure2.objects.Heart;
import zhrfrd.adventure2.objects.ManaCrystal;
import zhrfrd.adventure2.objects.PotionRed;
import zhrfrd.adventure2.objects.ShieldBlue;
import zhrfrd.adventure2.tiles_interactive.DryTree;

public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	/**
	 * Create and set position of the objects on the map.
	 */
	public void setObject() {
		int mapNum = 0;
		int i = 0;
		gp.obj[mapNum][i] = new CoinBronze(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE * 25;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE * 23;
		
		i ++;
		
		gp.obj[mapNum][i] = new CoinBronze(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE * 21;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE * 19;
		
		i ++;
		
		gp.obj[mapNum][i] = new CoinBronze(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE * 26;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE * 21;

		i ++;
		
		gp.obj[mapNum][i] = new Axe(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE * 33;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE * 21;

		i ++;
		
		gp.obj[mapNum][i] = new ShieldBlue(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE * 35;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE * 21;

		i ++;
		
		gp.obj[mapNum][i] = new PotionRed(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE * 37;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE * 22;

		i ++;
		
		gp.obj[mapNum][i] = new Heart(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE * 22;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE * 29;

		i ++;
		
		gp.obj[mapNum][i] = new ManaCrystal(gp);
		gp.obj[mapNum][i].worldX = gp.TILE_SIZE * 22;
		gp.obj[mapNum][i].worldY = gp.TILE_SIZE * 31;
	}
	
	/**
	 * Set all the npcs of the game.
	 */
	public void setNPC() {
		int mapNum = 0;
		int i = 0;
		
		// Map 0
		gp.npc[mapNum][0] = new OldMan(gp);
		gp.npc[mapNum][0].worldX = gp.TILE_SIZE * 21;
		gp.npc[mapNum][0].worldY = gp.TILE_SIZE * 21;
		
		i ++;
		
		// Map 1
		mapNum = 1;
		gp.npc[mapNum][0] = new OldMan(gp);
		gp.npc[mapNum][0].worldX = gp.TILE_SIZE * 12;
		gp.npc[mapNum][0].worldY = gp.TILE_SIZE * 7;
	}
	
	/**
	 * Set all the monsters of the game.
	 */
	public void setMonster() {
		int mapNum = 0;
		int i = 0;
		gp.monster[mapNum][i] = new Slime(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE * 21;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE * 38;
		
		i ++;
		
		gp.monster[mapNum][i] = new Slime(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE * 23;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE * 42;
		
		i ++;
		
		gp.monster[mapNum][i] = new Slime(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE * 24;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE * 37;
		
		i ++;
		
		gp.monster[mapNum][i] = new Slime(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE * 34;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE * 42;
		
		i ++;
		
		gp.monster[mapNum][i] = new Slime(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE * 38;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE * 42;
	}
	
	/**
	 * Set interactive tiles on the map.
	 */
	public void setInteractiveTile() {
		int mapNum = 0;
		int i = 0;
		
		gp.interactiveTile[mapNum][i] = new DryTree(gp, 27, 12);
		
		i ++;
		
		gp.interactiveTile[mapNum][i] = new DryTree(gp, 28, 12);
		
		i ++;
		
		gp.interactiveTile[mapNum][i] = new DryTree(gp, 29, 12);
		
		i ++;
		
		gp.interactiveTile[mapNum][i] = new DryTree(gp, 30, 12);
		
		i ++;
		
		gp.interactiveTile[mapNum][i] = new DryTree(gp, 31, 12);
		
		i ++;
		
		gp.interactiveTile[mapNum][i] = new DryTree(gp, 32, 12);
		
		i ++;
		
		gp.interactiveTile[mapNum][i] = new DryTree(gp, 33, 12);
	}
}
