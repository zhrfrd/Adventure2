package zhrfrd.adventure2.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.objects.Heart;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font retganon;
	BufferedImage heart_full, heart_half, heart_blank;
	public final Font RETGANON_PLAIN_20;   // Debug message
	public final Font RETGANON_PLAIN_30;   // Pop-up notification
	public final Font RETGANON_PLAIN_40;   // Objects number
	public final Font RETGANON_BOLD_80;   // Congratulation message
	public boolean messageOn = false;
	public String message = "";
	public boolean gameFinished = false;
	int notificationCounter = 0;
	public String currentDialog = "";
	public int commandNumber = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream inputStream = getClass().getResourceAsStream("/font/retganon.ttf");
			retganon = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RETGANON_PLAIN_20 = retganon.deriveFont(Font.PLAIN, 20);
		RETGANON_PLAIN_30 = retganon.deriveFont(Font.PLAIN, 30);
		RETGANON_PLAIN_40 = retganon.deriveFont(Font.PLAIN, 40);
		RETGANON_BOLD_80 = retganon.deriveFont(Font.BOLD, 80);
		
		// Hearts
		Entity heart = new Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
	}
	
	/*
	 * Show pop-up notification
	 */
	public void showMessage(String message) {
		this.message = message;
		messageOn = true;
	}
	
	/*
	 * Draw UI elements in the panel (don't instantiate here. Since  draw(Graphics2D g2) will be called 60 times per seconds it will affect the performance of the game)
	 */
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(retganon);
		g2.setColor(Color.white);
		
		// Title state
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		// Play state
		if (gp.gameState == gp.playState) {
			drawPlayerLife();
		}
		
		// Pause state
		if (gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		
		// Dialog state
		if (gp.gameState == gp.dialogState) {
			drawPlayerLife();
			drawDialogScreen();
		}
	}
	
	/*
	 * Draw the player life on the screen
	 */
	public void drawPlayerLife() {
		int x = gp.TILE_SIZE / 2;
		int y = gp.TILE_SIZE / 2;
		int i = 0;
		
		// Add blank hearts by default for every 2 lives
		while (i < gp.player.maxLife / 2) {
			g2.drawImage(heart_blank, x, y, null);
			x += gp.TILE_SIZE;
			
			i ++;
		}
		
		x = gp.TILE_SIZE / 2;
		y = gp.TILE_SIZE / 2;
		i = 0;
		
		// Each heart is 2 lives
		while (i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			
			i ++;
			
			if (i < gp.player.life) 
				g2.drawImage(heart_full, x, y, null);
			
			i ++;
			
			x += gp.TILE_SIZE;
		}
	}
	
	/*
	 * Draw the menu of the title screen
	 */
	public void drawTitleScreen () {
		// Title
		g2.setFont(RETGANON_BOLD_80);
		String text = "Adventure 2";
		int x = getXforCenteredText(text);
		int y = gp.TILE_SIZE * 3;
		
		// Shadow Color
		g2.setColor(Color.gray);
		g2.drawString(text, x + 5, y + 5);
		
		// Main text color
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		// Player image
		x = (gp.SCREEN_WIDTH / 2) - (gp.TILE_SIZE * 2) / 2;
		y += gp.TILE_SIZE * 2;
		
		g2.drawImage(gp.player.down1, x, y, gp.TILE_SIZE * 2, gp.TILE_SIZE * 2, null);
		
		// Menu
		g2.setFont(RETGANON_PLAIN_40);
		
		text = "NEW GAME";
		x = getXforCenteredText(text);
		y += gp.TILE_SIZE * 3.5;
		
		g2.drawString(text, x, y);
		
		if (commandNumber == 0) {
			g2.drawString(">", x - gp.TILE_SIZE, y);   // Draw cursor
		}
		
		text = "LOAD GAME";
		x = getXforCenteredText(text);
		y += gp.TILE_SIZE;
		
		g2.drawString(text, x, y);
		
		if (commandNumber == 1) {
			g2.drawString(">", x - gp.TILE_SIZE, y);
		}
		
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.TILE_SIZE;
		
		g2.drawString(text, x, y);
		
		if (commandNumber == 2) {
			g2.drawString(">", x - gp.TILE_SIZE, y);
		}
	}

	/*
	 * Handle the drawing of the screen when the game is paused such as PAUSE text
	 */
	public void drawPauseScreen() {
		String text = "PAUSED";
		g2.setFont(RETGANON_BOLD_80);
		int x = getXforCenteredText(text);
		int y = gp.SCREEN_HEIGHT / 2;
		
		g2.drawString(text, x, y);
	}
	
	/*
	 * Handle the drawing of the dialog screen
	 */
	public void drawDialogScreen() {
		// Dialog box
		int x = gp.TILE_SIZE * 2;
		int y = gp.TILE_SIZE / 2;
		int width = gp.SCREEN_WIDTH - (gp.TILE_SIZE * 4);
		int height = gp.TILE_SIZE * 4;
		
		drawSubWindow(x, y, width, height);
		
		// Text
//		g2.setFont(RETGANON_PLAIN_30);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32f));
		x += gp.TILE_SIZE;
		y += gp.TILE_SIZE;
		
		for (String line : currentDialog.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}
	
	public void  drawSubWindow(int x, int y, int width, int height) {
		Color color = new Color(0, 0, 0, 210);
		g2.setColor(color);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		color = new Color(255, 255, 255);
		g2.setColor(color);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}
	
	/*
	 * Get the exact x position of the text when it needs to be centered
	 */
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.SCREEN_WIDTH / 2 - length / 2;
		
		return x;
	}
	
	/*
	 * Display debug message with useful information
	 */
	public void showDrawTime(Graphics2D g2, long drawTime) {
		g2.setFont(RETGANON_PLAIN_20);
		g2.setColor(Color.white);
		g2.drawString("Draw time:  " + drawTime + "ns", 10, 500);
	}
}
