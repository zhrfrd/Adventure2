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
		int i = 0;
		gp.obj[i] = new CoinBronze(gp);
		gp.obj[i].worldX = gp.TILE_SIZE * 25;
		gp.obj[i].worldY = gp.TILE_SIZE * 23;
		
		i ++;
		
		gp.obj[i] = new CoinBronze(gp);
		gp.obj[i].worldX = gp.TILE_SIZE * 21;
		gp.obj[i].worldY = gp.TILE_SIZE * 19;
		
		i ++;
		
		gp.obj[i] = new CoinBronze(gp);
		gp.obj[i].worldX = gp.TILE_SIZE * 26;
		gp.obj[i].worldY = gp.TILE_SIZE * 21;

		i ++;
		
		gp.obj[i] = new Axe(gp);
		gp.obj[i].worldX = gp.TILE_SIZE * 33;
		gp.obj[i].worldY = gp.TILE_SIZE * 21;

		i ++;
		
		gp.obj[i] = new ShieldBlue(gp);
		gp.obj[i].worldX = gp.TILE_SIZE * 35;
		gp.obj[i].worldY = gp.TILE_SIZE * 21;

		i ++;
		
		gp.obj[i] = new PotionRed(gp);
		gp.obj[i].worldX = gp.TILE_SIZE * 37;
		gp.obj[i].worldY = gp.TILE_SIZE * 22;

		i ++;
		
		gp.obj[i] = new Heart(gp);
		gp.obj[i].worldX = gp.TILE_SIZE * 22;
		gp.obj[i].worldY = gp.TILE_SIZE * 29;

		i ++;
		
		gp.obj[i] = new ManaCrystal(gp);
		gp.obj[i].worldX = gp.TILE_SIZE * 22;
		gp.obj[i].worldY = gp.TILE_SIZE * 31;
	}
	
	/**
	 * Set all the npcs of the game.
	 */
	public void setNPC() {
		gp.npc[0] = new OldMan(gp);
		gp.npc[0].worldX = gp.TILE_SIZE * 21;
		gp.npc[0].worldY = gp.TILE_SIZE * 21;
	}
	
	/**
	 * Set all the monsters of the game.
	 */
	public void setMonster() {
		int i = 0;
		gp.monster[i] = new Slime(gp);
		gp.monster[i].worldX = gp.TILE_SIZE * 21;
		gp.monster[i].worldY = gp.TILE_SIZE * 38;
		
		i ++;
		
		gp.monster[i] = new Slime(gp);
		gp.monster[i].worldX = gp.TILE_SIZE * 23;
		gp.monster[i].worldY = gp.TILE_SIZE * 42;
		
		i ++;
		
		gp.monster[i] = new Slime(gp);
		gp.monster[i].worldX = gp.TILE_SIZE * 24;
		gp.monster[i].worldY = gp.TILE_SIZE * 37;
		
		i ++;
		
		gp.monster[i] = new Slime(gp);
		gp.monster[i].worldX = gp.TILE_SIZE * 34;
		gp.monster[i].worldY = gp.TILE_SIZE * 42;
		
		i ++;
		
		gp.monster[i] = new Slime(gp);
		gp.monster[i].worldX = gp.TILE_SIZE * 38;
		gp.monster[i].worldY = gp.TILE_SIZE * 42;
	}
	
	/**
	 * Set interactive tiles on the map.
	 */
	public void setInteractiveTile() {
		int i = 0;
		
		gp.interactiveTile[i] = new DryTree(gp, 27, 12);
		
		i ++;
		
		gp.interactiveTile[i] = new DryTree(gp, 28, 12);
		
		i ++;
		
		gp.interactiveTile[i] = new DryTree(gp, 29, 12);
		
		i ++;
		
		gp.interactiveTile[i] = new DryTree(gp, 30, 12);
		
		i ++;
		
		gp.interactiveTile[i] = new DryTree(gp, 31, 12);
		
		i ++;
		
		gp.interactiveTile[i] = new DryTree(gp, 32, 12);
		
		i ++;
		
		gp.interactiveTile[i] = new DryTree(gp, 33, 12);
	}
}
