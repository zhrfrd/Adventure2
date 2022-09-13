package zhrfrd.adventure2.tiles;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import zhrfrd.adventure2.main.GamePanel;
import zhrfrd.adventure2.main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[50];
		mapTileNum = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
		
		getTileImage();
		loadMap("/maps/worldV2.txt");
	}
	
	/*
	 * Get the tiles image and relative information
	 */
	public void getTileImage() {
		// Placeholder (In order to use only 2 digits indexes for the map text file and make it more readable)
		setup(0, "grass0", false);
		setup(1, "grass0", false);
		setup(2, "grass0", false);
		setup(3, "grass0", false);
		setup(4, "grass0", false);
		setup(5, "grass0", false);
		setup(6, "grass0", false);
		setup(7, "grass0", false);
		setup(8, "grass0", false);
		setup(9, "grass0", false);
		
		// Used tiles
		setup(10, "grass0", false);
		setup(11, "grass1", false);
		setup(12, "water0", true);
		setup(13, "water1", true);
		setup(14, "water2", true);
		setup(15, "water3", true);
		setup(16, "water4", true);
		setup(17, "water5", true);
		setup(18, "water6", true);
		setup(19, "water7", true);
		setup(20, "water8", true);
		setup(21, "water9", true);
		setup(22, "water10", true);
		setup(23, "water11", true);
		setup(24, "water12", true);
		setup(25, "water13", true);
		setup(26, "road0", false);
		setup(27, "road1", false);
		setup(28, "road2", false);
		setup(29, "road3", false);
		setup(30, "road4", false);
		setup(31, "road5", false);
		setup(32, "road6", false);
		setup(33, "road7", false);
		setup(34, "road8", false);
		setup(35, "road9", false);
		setup(36, "road10", false);
		setup(37, "road11", false);
		setup(38, "road12", false);
		setup(39, "earth", false);
		setup(40, "wall", true);
		setup(41, "tree", true);
	}
	
	/*
	 * Handle instantiation, import of images, scaling and collision
	 */
	public void setup(int index, String imageName, boolean solid) {
		UtilityTool utilityTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = utilityTool.scaleImage(tile[index].image, gp.TILE_SIZE, gp.TILE_SIZE);   // Scale the image by using the UtilityTool class created
			tile[index].solid = solid;
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Read text file and load the map from 
	 */
	public void loadMap(String filePath) {
		try {
			InputStream inputStream = getClass().getResourceAsStream(filePath);   // Get text file containing map data
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));   // Read from inpputStream
			
			int col = 0;
			int row = 0;
			
			while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
				String line = bufferedReader.readLine();
				
				while (col < gp.MAX_WORLD_COL) {
					String number[] = line.split(" ");   // Read each number in a line excluding spaces and save it inside the array
					int num = Integer.parseInt(number[col]);
					mapTileNum[col][row] = num;
					
					col ++;
				}
				
				if (col == gp.MAX_WORLD_COL) {
					col = 0;
					row ++;
				}
			}
			
			bufferedReader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Render the tiles of the map on the gamePanel from the map data retrieved from the text file
	 */
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		while (worldCol < gp.MAX_WORLD_COL && worldRow < gp.MAX_WORLD_ROW) {
			int tileNum = mapTileNum[worldCol][worldRow];
			int worldX = worldCol * gp.TILE_SIZE;   // Tiles position of the world
			int worldY = worldRow * gp.TILE_SIZE;   //
			int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;   // Tiles position on the screen
			int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;   //
			
			// Render only the tiles visible on the screen plus one extra tile in order to not show black edges when moving
			if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X && 
					worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X && 
					worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y && 
					worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) 
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			
			worldCol ++;
			
			if (worldCol == gp.MAX_WORLD_COL) {
				worldCol = 0;
				worldRow ++;
			}
		}
	}
}
