package zhrfrd.adventure2.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.entities.Player;
import zhrfrd.adventure2.objects.SuperObject;
import zhrfrd.adventure2.tiles.TileManager;

public class GamePanel extends JPanel implements Runnable {
	// Screen settings
	final int ORIGINAL_TILE_SIZE = 16;   // 16x16 pixels tile
	final int SCALE = 3;   // Re-scale tiles 
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;   // 48*48 pixels tile
	public final int MAX_SCREEN_COL = 16;   // Max tiles to display on the screen
	public final int MAX_SCREEN_ROW = 12;   // 
	public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;    // 768 pixels
	public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;   // 576 pixels
	//World Settings
	public final int MAX_WORLD_COL = 50;
	public final int MAX_WORLD_ROW = 50;
	final int FPS = 60;
	int drawCount = 0;
	long drawTime = 0;
	// System
	Thread gameThread;
	KeyHandler keyHandler = new KeyHandler(this);
	TileManager tileManager = new TileManager(this);
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	public AssetSetter assetSetter= new AssetSetter(this);
	public Sound soundTrack = new Sound();
	public Sound soundEffect = new Sound();
	public UI ui = new UI(this);
	// Entities and objects
	public Player player = new Player(this, keyHandler);
	public SuperObject obj[] = new SuperObject[10];
	public Entity npc[] = new Entity[10];
	// Game state
	public int gameState;
	public final int playState = 1;
	public int pauseState = 2;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);   // Unnecessary
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
	}
	
	/*
	 * Setup up game objects and more
	 */
	public void setupGame() {
		assetSetter.setObject();
		assetSetter.setNPC();
		playSoundTrack(0);   // Play sound-track
		stopSoundTrack();
		gameState = playState;
	}
	
	/*
	 * Start the game thread
	 */
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	/*
	 * Update information such as character position
	 */
	public void update() {
		if (gameState == playState) {
			// Player
			player.update();
			//NPC
			for (int i = 0; i < npc.length; i ++)
				if (npc[i] != null)
					npc[i].update();
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
		super.paintComponent(g);   // Must be done for the JPanel painting takes place
		
		Graphics2D g2 = (Graphics2D)g;
		
		// Debug performance
		long drawStart = 0;
		
		if (keyHandler.checkDrawTime)
			drawStart = System.nanoTime();
		
		// Draw tiles
		tileManager.draw(g2);
		
		// Draw objects
		for (int i = 0; i < obj.length; i ++)
			if (obj[i] != null)
				obj[i].draw(g2, this);
		
		// Draw npc
		for (int i = 0; i < npc.length; i ++)
			if (npc[i] != null)
				npc[i].draw(g2);
		
		// Draw player
		player.draw(g2);
		
		// Draw UI
		ui.draw(g2);
		
		drawCount ++;
		// Debug performances 
		if (keyHandler.checkDrawTime) {
			// Update the time every half second
			if (drawCount % 30 == 0) {
				long drawEnd = System.nanoTime();
				long timePassed = drawEnd - drawStart;
				drawTime = timePassed;
				
			}
			
			ui.showDrawTime(g2, drawTime);
		}
		
		// Reset the drawCount when it reaches 1000 to avoid overflow
		if (drawCount >= 1000)
			drawCount = 0;
		
		g2.dispose();   // Dispose g2 in order to save resources
	}

	@Override
	public void run () {
		double drawInterval = 1000000000 / FPS;   // Draw every 0.01666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		// Game loop
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			// Every 0.01666 seconds (60 FPS)
			if (delta >= 1) {
				update();   // Update information such as character position
				repaint();   // Call paintComponent() to draw the screen with the updated information
				
				delta --;
			}
		}
	}
}
