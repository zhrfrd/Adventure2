package zhrfrd.adventure2.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.objects.Heart;
import zhrfrd.adventure2.objects.ManaCrystal;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	Font retganon;
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;;
	public final Font RETGANON_PLAIN_20;   // Debug message
	public final Font RETGANON_PLAIN_30;   // Pop-up notification
	public final Font RETGANON_BOLD_30;
	public final Font RETGANON_PLAIN_40;   // Objects number
	public final Font RETGANON_BOLD_80;   // Congratulation message
	public boolean messageOn = false;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDialog = "";
	public int commandNumber = 0;
	public int slotCol = 0;
	public int slotRow = 0;
	int subState = 0;
	
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
		RETGANON_BOLD_30 = retganon.deriveFont(Font.BOLD, 30);
		RETGANON_PLAIN_40 = retganon.deriveFont(Font.PLAIN, 40);
		RETGANON_BOLD_80 = retganon.deriveFont(Font.BOLD, 80);
		
		// Hearts
		Entity heart = new Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity crystal = new ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
	}
	
	/*
	 * Show pop-up notification
	 */
	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0); 
	}
	
	/**
	 * Draw UI elements in the panel (don't instantiate here. Since  draw(Graphics2D g2) will be called 60 times per seconds it will affect the performance of the game).
	 * 
	 * @param g2 The "paint-brush" responsible for drawing on the game panel.
	 */
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(retganon);
		g2.setColor(Color.white);
		
		// Title state
		if (gp.gameState == gp.TITLE_STATE)
			drawTitleScreen();
		
		// Play state
		if (gp.gameState == gp.PLAY_STATE) {
			drawPlayerLife();
			drawPlayerMana();
			drawMessage();
		}
		
		// Pause state
		if (gp.gameState == gp.PAUSE_STATE) {
			drawPlayerLife();
			drawPlayerMana();
			drawPauseScreen();
		}
		
		// Dialog state
		if (gp.gameState == gp.DIALOG_STATE) {
			drawPlayerLife();
			drawPlayerMana();
			drawDialogScreen();
		}
		
		// Character state
		if (gp.gameState == gp.CHARACTER_STATE) {
			drawCharacterScreen();
			drawInventory();
		}
		
		// Options state
		if (gp.gameState == gp.OPTIONS_STATE)
			drawOptionsScreen();
		
		// Gameover state
		if (gp.gameState == gp.GAMEOVER_STATE)
			drawGameOverScreen();
	}
	
	/**
	 * Draw the game over screen when the player dies
	 */
	public void drawGameOverScreen() {
		 g2.setColor(new Color(0, 0, 0, 150));
		 g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
		 
		 int x;
		 int y;
		 String text;
		 
		 g2.setFont(RETGANON_BOLD_80);
		 text = "Game Over!";
		 // Text shadow
		 g2.setColor(Color.black);
		 x = getXforCenteredText(text);
		 y = gp.TILE_SIZE * 4;
		 g2.drawString(text, x, y);
		 // Text
		 g2.setColor(Color.white);
		 g2.drawString(text, x - 4, y - 4);
		 
		 // Retry
		 g2.setFont(RETGANON_PLAIN_40);
		 text = "Retry";
		 x = getXforCenteredText(text);
		 y += gp.TILE_SIZE * 4;
		 g2.drawString(text, x, y);
		 
		 if (commandNumber == 0)
			 g2.drawString(">", x - 40, y);
		 
		 // Go back to the title screen
		 text = "Quit";
		 x = getXforCenteredText(text);
		 y += 55;
		 g2.drawString(text, x, y);
		 
		 if (commandNumber == 1)
			 g2.drawString(">", x - 40, y);
	}
	
	/**
	 * Draw the options screen.
	 */
	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(RETGANON_PLAIN_30);
		
		// Sub window
		int frameX = gp.TILE_SIZE * 4;
		int frameY = gp.TILE_SIZE;
		int frameWidth = gp.TILE_SIZE * 8;
		int frameHeight = gp.TILE_SIZE * 10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch (subState) {
			case 0: options_top(frameX, frameY); break;
			case 1: break;  //TODO Full screen
			case 2: options_control(frameX, frameY); break;
			case 3: options_endGameConfirmation(frameX, frameY); break;
		}
		
		gp.keyHandler.enterPressed = false;
	}
	
	/**
	 * Top part of the options menu.
	 * 
	 * @param frameX X position of the options menu on the screen.
	 * @param frameY Y position of the options menu on the screen.
	 */
	public void options_top(int frameX, int frameY) {
		int textX;
		int textY;
		
		// Title
		String text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY + gp.TILE_SIZE;
		g2.drawString(text, textX, textY);
		
		//TODO
		// Full screen ON/OFF
		textX = frameX + gp.TILE_SIZE;
		textY += gp.TILE_SIZE * 2;
		g2.drawString("Full Screen", textX, textY);
		
		if (commandNumber == 0) {
			g2.drawString(">", textX - 20, textY);
			
			// Check if it's full full screen or not in order to activate or disactivate the check box
			if (gp.keyHandler.enterPressed) {
				if (!gp.fullScreenOn)
					gp.fullScreenOn = true;
				
				else if (gp.fullScreenOn)
					gp.fullScreenOn = false; 
			}
		}
		
		// Music
		textY += gp.TILE_SIZE;
		g2.drawString("Music", textX, textY);
		
		if (commandNumber == 1)
			g2.drawString(">", textX - 20, textY);
		
		// Sound effects
		textY += gp.TILE_SIZE;
		g2.drawString("Sound effects", textX, textY);
		
		if (commandNumber == 2)
			g2.drawString(">", textX - 20, textY);
		
		// Control
		textY += gp.TILE_SIZE;
		g2.drawString("Control", textX, textY);
		
		if (commandNumber == 3) {
			g2.drawString(">", textX - 20, textY);
			
			if (gp.keyHandler.enterPressed) {
				subState = 2;
				commandNumber = 0;
			}
		}
		
		// End game
		textY += gp.TILE_SIZE;
		g2.drawString("End game", textX, textY);
		
		if (commandNumber == 4) {
			g2.drawString(">", textX - 20, textY);
			
			if (gp.keyHandler.enterPressed) {
				subState = 3;
				commandNumber = 0;
			}
		}
		
		// Back
		textY += gp.TILE_SIZE * 2;
		g2.drawString("Back", textX, textY);
		
		if (commandNumber == 5) {
			g2.drawString(">", textX - 20, textY);
			
			if (gp.keyHandler.enterPressed) {
				gp.gameState = gp.PLAY_STATE;
				commandNumber = 0;
			}
		}
		
		// Full screen check box
		textX = frameX + (int) (gp.TILE_SIZE * 4.5);
		textY = frameY + gp.TILE_SIZE * 2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		
		if (gp.fullScreenOn) 
			g2.fillRect(textX, textY, 24, 24);
		
		// Music volume
		textY += gp.TILE_SIZE;
		g2.drawRect(textX, textY, 120, 24);  // 120 / 5 = 24 pixels for one scale unit
		int volumeWidth = 24 * gp.soundTrack.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		// Sound effect
		textY += gp.TILE_SIZE;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.soundEffect.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		gp.config.saveConfig();   // Save the game sattings to the configuration file
	 }

	/**
	 * Control page showing the controls keys.
	 * 
	 * @param frameX X position of the control menu on the screen.
	 * @param frameY X position of the control menu on the screen.
	 */
	public void options_control(int frameX, int frameY) {
		int textX;
		int textY;
		
		// Title
		String text = "Control";
		textX = getXforCenteredText(text);
		textY = frameY + gp.TILE_SIZE;
		g2.drawString(text, textX, textY);
		
		textX = frameX += gp.TILE_SIZE;
		textY += gp.TILE_SIZE;
		g2.drawString("Move", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("Confirm, Attack", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("Shoot, Cast", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("Character Screen", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("Pause", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("Options", textX, textY);
		textY += gp.TILE_SIZE;
		
		textX = frameX + gp.TILE_SIZE * 5;
		textY = frameY + gp.TILE_SIZE * 2;
		g2.drawString("WASD", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("ENTER", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("F", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("C", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("P", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("ESC", textX, textY);
		textY += gp.TILE_SIZE;
		
		// Back
		textX = frameX + gp.TILE_SIZE;
		textY = frameY + gp.TILE_SIZE * 9;
		g2.drawString("Back", textX, textY);
		if (commandNumber == 0) {
			g2.drawString(">", textX - 25, textY);
			
			if (gp.keyHandler.enterPressed) {
				subState = 0;
				commandNumber = 3;
			}
		}
		
	}
	
	/**
	 * Control page showing asking the user if he wants to wuit the game.
	 * 
	 * @param frameX X position of the end game menu on the screen.
	 * @param frameY X position of the end game menu on the screen.
	 */
	public void options_endGameConfirmation(int frameX, int frameY) {
		int textX = frameX + gp.TILE_SIZE;
		int textY = frameY + gp.TILE_SIZE * 3;
		
		currentDialog = "Quit the game and return\n to the title screen?";
		
		for (String line: currentDialog.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		// Yes
		String text = "Yes";
		textX = getXforCenteredText(text);
		textY += gp.TILE_SIZE * 3;
		g2.drawString(text, textX, textY);
		
		if (commandNumber == 0) {
			g2.drawString(">",  textX - 25, textY);
			
			if (gp.keyHandler.enterPressed) {
				gp.stopSoundTrack();
				subState = 0;
				gp.gameState = gp.TITLE_STATE;
			}
		}
		
		// No
		text = "No";
		textX = getXforCenteredText(text);
		textY += gp.TILE_SIZE;
		g2.drawString(text, textX, textY);
		
		if (commandNumber == 1) {
			g2.drawString(">",  textX - 25, textY);
			
			if (gp.keyHandler.enterPressed) {
				subState = 0;
				commandNumber = 4;
			}
		}
	}
	
	/**
	 * Draw the player's life on the screen.
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
	
	/**
	 * Draw the player's mana on the screen.
	 */
	public void drawPlayerMana() {
		int x = (gp.TILE_SIZE / 2) - 5;
		int y = (int)(gp.TILE_SIZE * 1.5);
		int i = 0;
		
		// Draw max mana
		while (i < gp.player.maxMana) {
			g2.drawImage(crystal_blank,x, y, null);
			i ++;
			x += 35;
		}
		
		// Draw mana
		x = (gp.TILE_SIZE / 2) - 5;
		y = (int)(gp.TILE_SIZE * 1.5);
		i = 0;
		
		while (i < gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i ++;
			x += 35;
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
	 * Handle drawing of scrolling messages on screen while playing
	 */
	public void drawMessage() {
		int messageX = gp.TILE_SIZE;
		int messageY = gp.TILE_SIZE * 4;
		
		g2.setFont(RETGANON_BOLD_30);
		
		for (int i = 0; i < message.size(); i ++)
			if (message.get(i) != null) {
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX + 2, messageY + 2);   // Shadow
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1;   // messageCounter ++
				messageCounter.set(i, counter);   // Set the counter to the array list
				messageY += 50;
				
				// Remove messages after 3 seconds
				if (messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
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
	
	/**
	 * Handle drawing of the character screen.
	 */
	public void drawCharacterScreen() {
		// Frame
		final int frameX = gp.TILE_SIZE * 2;
		final int frameY = gp.TILE_SIZE;
		final int frameWidth = gp.TILE_SIZE * 5;
		final int frameHeight = gp.TILE_SIZE * 10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// Text
		g2.setColor(Color.white);
		g2.setFont(RETGANON_PLAIN_30);
		int textX = frameX + 20;
		int textY = frameY + gp.TILE_SIZE;
		final int lineHeight = 35;   // Space between text lines
		
		// Attributes to be displayed
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Mana", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defence", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		textY += lineHeight;
		g2.drawString("Next level", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		textY += lineHeight + 10;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight + 15;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight;
		
		// Values
		int tailX = (frameX + frameWidth) - 30;   // End of the frame 
		textY = frameY + gp.TILE_SIZE;   // Reset text y
		String value;
		
		value = String.valueOf(gp.player.level);
		textX = getXforAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.life + "-" + gp.player.maxLife);
		textX = getXforAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.mana + "-" + gp.player.maxMana);
		textX = getXforAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.strength);
		textX = getXforAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.attack);
		textX = getXforAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.defence);
		textX = getXforAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.exp);
		textX = getXforAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		value = String.valueOf(gp.player.coin);
		textX = getXforAlignedToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.TILE_SIZE, textY - 24, null);
		textY += lineHeight;
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.TILE_SIZE, textY - 14, null);
	}
	
	/**
	 * Handle drawing of the inventory screen
	 */
	public void drawInventory() {
		// Frame
		int frameX = gp.TILE_SIZE * 9;
		int frameY = gp.TILE_SIZE;
		int frameWidth = gp.TILE_SIZE * 6;
		int frameHeight = gp.TILE_SIZE * 5;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// Slot
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;   // Default
		int slotY = slotYstart;   //
		int slotSize = gp.TILE_SIZE + 3;
		
		// Draw player's items
		for (int i = 0; i < gp.player.inventory.size(); i ++) {
			// Highlight equipped items
			if (gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield) {
				g2.setColor(new Color( 240, 190, 90));
				g2.fillRoundRect(slotX, slotY, gp.TILE_SIZE, gp.TILE_SIZE, 10, 10);
			}
			
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			
			slotX += slotSize;
			
			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += slotSize;
			}
		}
		
		// Cursor
		int cursorX = slotXstart + (slotSize * slotCol);
		int cursorY = slotYstart + (slotSize * slotRow);
		int cursorWidth = gp.TILE_SIZE;
		int cursorHeight = gp.TILE_SIZE;
		
		// Draw cursor
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		// Description frame
		int dFrameX = frameX;
		int dFrameY = frameY + frameHeight; 
		int dFrameWidth = frameWidth;
		int dFrameHeight = gp.TILE_SIZE * 4;
		
		// Draw description text
		int textX = dFrameX + 20;
		int textY = dFrameY + gp.TILE_SIZE;
		
		g2.setFont(RETGANON_PLAIN_30);
		
		int itemIndex = getItemIndexOnSlot();
		
		if (itemIndex < gp.player.inventory.size()) {
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
			
			for (String line: gp.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);
				textY += 32;
			}
		}
	}
	
	/*
	 * Get the item index from its position on the inventory
	 */
	public int getItemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow * 5);
		
		return itemIndex;
	}
	
	/*
	 * Draw the frame for the sub windows (such as dialog, character ...)
	 */
	public void drawSubWindow(int x, int y, int width, int height) {
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
	 * Get the exact x position of the text when it needs to be aligned to the right of the frame
	 */
	public int getXforAlignedToRightText(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;

		return x;
	}
	
	/*
	 * Display debug message with useful information
	 */
	public void showDebugText(Graphics2D g2, long drawTime, int worldX, int worldY, int col, int row) {
		int x = 10;
		int y = 400;
		int lineHeight = 20;
		
		g2.setFont(RETGANON_PLAIN_20);
		g2.setColor(Color.white);
		g2.drawString("X:  " + worldX, x, y);
		y += lineHeight;
		g2.drawString("Y:  " + worldY, x, y);
		y += lineHeight;
		g2.drawString("Column:  " + col, x, y);
		y += lineHeight;
		g2.drawString("Row:  " + row, x, y);
		y += lineHeight;
		g2.drawString("Draw time:  " + drawTime + "ns", x, y);
	}
}
