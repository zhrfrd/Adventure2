package zhrfrd.adventure.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import zhrfrd.adventure.objects.Key;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	final Font ARIAL_PLAIN_20;   // Debug message
	final Font ARIAL_PLAIN_30;   // Pop-up notification
	final Font ARIAL_PLAIN_40;   // Objects number
	final Font ARIAL_BOLD_80;   // Congratulation message
//	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	public boolean gameFinished = false;
	int notificationCounter = 0;
	double playTime;
	DecimalFormat decimalFormat = new DecimalFormat("#0.00");   // Decimal format with 2 decimal digits
	
	public UI(GamePanel gp) {
		this.gp = gp;
		ARIAL_PLAIN_20 = new Font("Arial", Font.PLAIN, 20);
		ARIAL_PLAIN_30 = new Font("Arial", Font.PLAIN, 30);
		ARIAL_PLAIN_40 = new Font("Arial", Font.PLAIN, 40);
		ARIAL_BOLD_80 = new Font("Arial", Font.BOLD, 80);
//		Key key = new Key(gp);
//		keyImage = key.image; 
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
		
		if (gp.gameState == gp.playState) {
			// TODO
		}
		
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
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
