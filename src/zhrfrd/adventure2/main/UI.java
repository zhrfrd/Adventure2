package zhrfrd.adventure2.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	final Font ARIAL_PLAIN_20;   // Debug message
	final Font ARIAL_PLAIN_30;   // Pop-up notification
	final Font ARIAL_PLAIN_40;   // Objects number
	final Font ARIAL_BOLD_80;   // Congratulation message
	public boolean messageOn = false;
	public String message = "";
	public boolean gameFinished = false;
	int notificationCounter = 0;
	public String currentDialog = "";
	
	public UI(GamePanel gp) {
		this.gp = gp;
		ARIAL_PLAIN_20 = new Font("Arial", Font.PLAIN, 20);
		ARIAL_PLAIN_30 = new Font("Arial", Font.PLAIN, 30);
		ARIAL_PLAIN_40 = new Font("Arial", Font.PLAIN, 40);
		ARIAL_BOLD_80 = new Font("Arial", Font.BOLD, 80);
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
		
		g2.setFont(ARIAL_PLAIN_40);
		g2.setColor(Color.white);
		
		// Play state
		if (gp.gameState == gp.playState) {
			// TODO
		}
		
		// Pause state
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		
		// Dialog state
		if (gp.gameState == gp.dialogState) {
			drawDialogScreen();
		}
	}
	
	/*
	 * Handle the drawing of the screen when the game is paused such as PAUSE text
	 */
	public void drawPauseScreen() {
		String text = "PAUSED";
		g2.setFont(ARIAL_BOLD_80);
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
		g2.setFont(ARIAL_PLAIN_30);
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
		g2.setFont(ARIAL_PLAIN_20);
		g2.setColor(Color.white);
		g2.drawString("Draw time:  " + drawTime + "ns", 10, 500);
	}
}
