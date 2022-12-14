package zhrfrd.adventure2.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.entities.Player;
import zhrfrd.adventure2.tiles.TileManager;
import zhrfrd.adventure2.tiles_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {
	// Screen settings
	final int ORIGINAL_TILE_SIZE = 16; // 16x16 pixels tile
	final int SCALE = 3; // Re-scale tiles
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48*48 pixels tile
	public final int MAX_SCREEN_COL = 16; // Max tiles to display on the screen
	public final int MAX_SCREEN_ROW = 12; //
	public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
	public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels
	// World Settings
	public final int MAX_WORLD_COL = 50;
	public final int MAX_WORLD_ROW = 50;
	public final int MAX_MAP = 10;   // Maximum number of possible maps
	public int currentMap = 0;   // Index of the current map
 	final int FPS = 60;
	int drawCount = 0;
	long drawTime = 0;
	// TODO
	// Full screen
	public boolean fullScreenOn = false;
	// System
	Thread gameThread;
	Config config = new Config(this);
	public KeyHandler keyHandler = new KeyHandler(this);
	TileManager tileManager = new TileManager(this);
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	public AssetSetter assetSetter = new AssetSetter(this);
	public Sound soundTrack = new Sound();
	public Sound soundEffect = new Sound();
	public UI ui = new UI(this);
	public EventHandler eventHandler = new EventHandler(this);
	// Entities and objects
	public Player player = new Player(this, keyHandler);
	public Entity obj[][] = new Entity[MAX_MAP][20]; // Total objects that can be displayed at the same time, not the number of objects existing
	public Entity npc[][] = new Entity[MAX_MAP][20];
	public Entity monster[][] = new Entity[MAX_MAP][20];
	public InteractiveTile interactiveTile[][] = new InteractiveTile[MAX_MAP][50];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();
	// Game state
	public int gameState;
	public final int TITLE_STATE = 0;
	public final int PLAY_STATE = 1;
	public final int PAUSE_STATE = 2;
	public final int DIALOG_STATE = 3;
	public final int CHARACTER_STATE = 4;
	public final int OPTIONS_STATE = 5;
	public final int GAMEOVER_STATE = 6;
	

	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black); // Unnecessary
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}

	/**
	 * Setup up game objects and more
	 */
	public void setupGame() {
		assetSetter.setObject();
		assetSetter.setNPC();
		assetSetter.setMonster();
		assetSetter.setInteractiveTile();
//		playSoundTrack(0);   // Play sound-track
		gameState = TITLE_STATE;
	}

	/**
	 * Start the game thread.
	 */
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	/**
	 * If the player press the "Retry" button when he dies, reset his and other world's default values but keep his inventory.
	 */
	public void retry() {
		player.setDefaultPosition();
		player.restoreLifeAndMana();
		assetSetter.setNPC();
		assetSetter.setMonster();
	}
	
	/**
	 * Reset ALL the default values when the player want to restart a new game.
	 */
	public void restart() {
		player.setDefaultValues();
		player.setDefaultPosition();
		player.restoreLifeAndMana();
		player.setItems();
		assetSetter.setObject();
		assetSetter.setNPC();
		assetSetter.setMonster();
		assetSetter.setInteractiveTile();
	}

	/**
	 * Update information such as character, monsters, npcs, projectiles, particles, interactive tiles....
	 */
	public void update() {
		if (gameState == PLAY_STATE) {
			// Player
			player.update();
			// NPC
			for (int i = 0; i < npc[1].length; i++)
				if (npc[currentMap][i] != null)
					npc[currentMap][i].update();

			// Monsters
			for (int i = 0; i < monster[1].length; i++)
				if (monster[currentMap][i] != null) {
					if (monster[currentMap][i].alive && !monster[currentMap][i].dying)
						monster[currentMap][i].update();

					// Remove monster if it's not alive and check the item dropped by it
					if (!monster[currentMap][i].alive) {
						monster[currentMap][i].checkDrop();
						monster[i] = null;
					}
				}

			// Projectile
			for (int i = 0; i < projectileList.size(); i++)
				if (projectileList.get(i) != null) {
					if (projectileList.get(i).alive)
						projectileList.get(i).update();

					// Remove projectile if it's not alive
					if (!projectileList.get(i).alive)
						projectileList.remove(i);
				}

			// Particle
			for (int i = 0; i < particleList.size(); i++)
				if (particleList.get(i) != null) {
					if (particleList.get(i).alive)
						particleList.get(i).update();

					// Remove particle if it's not alive
					if (!particleList.get(i).alive)
						particleList.remove(i);
				}

			// Interactive tiles
			for (int i = 0; i < interactiveTile[1].length; i ++) {
				if (interactiveTile[currentMap][i] != null) {
					interactiveTile[currentMap][i].update();
				}
			}
		}
	}

	/*
	 * Play sound-track
	 */
	public void playSoundTrack(int i) {
		soundTrack.setFile(i);
		soundTrack.play();
		soundTrack.loop();
	}

	/*
	 * Stop sound-track
	 */
	public void stopSoundTrack() {
		soundTrack.stop();
	}

	/*
	 * Play sound effect
	 */
	public void playSoundEffect(int i) {
		soundEffect.setFile(i);
		soundEffect.play();
	}

	/*
	 * To paint on a subclass of a JPanel you must override paintComponent()
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Must be done for the JPanel painting takes place

		Graphics2D g2 = (Graphics2D)g;

		// Debug performance
		long drawStart = 0;

		if (keyHandler.showDebugText)
			drawStart = System.nanoTime();

		// Title screen
		if (gameState == TITLE_STATE) {
			ui.draw(g2);
		}

		// Others
		else {
			// Draw tiles
			tileManager.draw(g2);
			
			// Draw interactive tiles
			for (int i = 0; i < interactiveTile[1].length; i ++)
				if (interactiveTile[currentMap][i] != null)
					interactiveTile[currentMap][i].draw(g2);

			// Add player to array list
			entityList.add(player);

			// Add npcs to array list
			for (int i = 0; i < npc[1].length; i++)
				if (npc[currentMap][i] != null)
					entityList.add(npc[currentMap][i]);

			// Add objects to array list
			for (int i = 0; i < obj[1].length; i++)
				if (obj[currentMap][i] != null)
					entityList.add(obj[currentMap][i]);

			// Add monsters to array list
			for (int i = 0; i < monster[1].length; i++)
				if (monster[currentMap][i] != null)
					entityList.add(monster[currentMap][i]);

			// Add projectile list to array list
			for (int i = 0; i < projectileList.size(); i++)
				if (projectileList.get(i) != null)
					entityList.add(projectileList.get(i));
			
			// Add particle list to array list
			for (int i = 0; i < particleList.size(); i++)
				if (particleList.get(i) != null)
					entityList.add(particleList.get(i));

			// Sort the order to display the entities inside entityList to avoid overlapping. (eg. if player comes from the top toward an npc, the npc should
			// overlap the player so, it should be drawn after the player. otherwise the
			// opposite)
			Collections.sort(entityList, new Comparator<Entity>() {
				// Compare the worldY of the two entities. If e1 < e1 return -1, if e1 == e2 return 0, if e1 > e2 return 1
				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);

					return result;
				}
			});

			// Draw entities after ordering them
			for (int i = 0; i < entityList.size(); i++)
				entityList.get(i).draw(g2);

			// Empty the array list otherwise it will get bigger and bigger over time
			entityList.clear();

			// Draw UI
			ui.draw(g2);
		}

		// Debug
		drawCount++;

		if (keyHandler.showDebugText) {
			// Update the time every half second
			if (drawCount % 30 == 0) {
				long drawEnd = System.nanoTime();
				long timePassed = drawEnd - drawStart;
				drawTime = timePassed;

			}

			ui.showDebugText(g2, drawTime, player.worldX, player.worldY,
					(player.worldX + player.solidArea.x) / TILE_SIZE, (player.worldY + player.solidArea.y) / TILE_SIZE);
		}

		// Reset the drawCount when it reaches 1000 to avoid overflow
		if (drawCount >= 1000)
			drawCount = 0;

		g2.dispose(); // Dispose g2 in order to save resources
	}

	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS; // Draw every 0.01666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		// Game loop
		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			// Every 0.01666 seconds (60 FPS)
			if (delta >= 1) {
				update(); // Update information such as character position
				repaint(); // Call paintComponent() to draw the screen with the updated information

				delta--;
			}
		}
	}
}
