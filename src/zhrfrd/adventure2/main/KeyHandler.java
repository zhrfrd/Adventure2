package zhrfrd.adventure2.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
	boolean showDebugText = false;
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	/**
	 * Handle the title screen state.
	 * 
	 * @param code The key pressed.
	 */
	public void titleState(int code) {
		// Move cursor through the menu when pressing the keys W and S
		if (code == KeyEvent.VK_W) {
			gp.ui.commandNumber --;
			
			if (gp.ui.commandNumber < 0)
				gp.ui.commandNumber = 2;
		}
			
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNumber ++;
			
			if (gp.ui.commandNumber > 2)
				gp.ui.commandNumber = 0;
		}
		
		// Select from menu
		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNumber == 0) {
				gp.gameState = gp.PLAY_STATE;
//				gp.playSoundTrack(0);
			}
			
			if (gp.ui.commandNumber == 1) {
				// Load game
			}
				
			if (gp.ui.commandNumber == 2) {
				System.exit(0);
			}
		}
	}
	
	/**
	 * Handle the play state.
	 * 
	 * @param code The key pressed.
	 */
	public void playState(int code) {
		if (code == KeyEvent.VK_W)
			upPressed = true;
		
		if (code == KeyEvent.VK_S)
			downPressed = true;
		
		if (code == KeyEvent.VK_A)
			leftPressed = true;
		
		if (code == KeyEvent.VK_D)
			rightPressed = true;
		
		if (code == KeyEvent.VK_P)
			gp.gameState = gp.PAUSE_STATE;
		
		if (code == KeyEvent.VK_C)
			gp.gameState = gp.CHARACTER_STATE;
		
		if (code == KeyEvent.VK_ENTER)
			enterPressed = true;
		
		if (code == KeyEvent.VK_F)
			shotKeyPressed = true;
		
		if (code == KeyEvent.VK_ESCAPE)
			gp.gameState = gp.OPTIONS_STATE;
		
		// Debug 
		if (code == KeyEvent.VK_T) {
			if (showDebugText)
				showDebugText = false;
			
			else
				showDebugText = true;
		}
		
		// Refresh map 
		if (code == KeyEvent.VK_R)
			gp.tileManager.loadMap("/maps/worldV2.txt");
	}

	/**
	 * Handle the pause state.
	 * 
	 * @param code The key pressed.
	 */
	public void pauseState(int code) {
		if (code == KeyEvent.VK_P)
			gp.gameState = gp.PLAY_STATE;
	}

	/**
	 * Handle the dialog state.
	 * 
	 * @param code The key pressed.
	 */
	public void dialogState(int code) {
		if (code == KeyEvent.VK_ENTER)
			gp.gameState = gp.PLAY_STATE;
	}
	
	/**
	 * Handle the character menu state.
	 * 
	 * @param code The key pressed.
	 */
	public void characterState(int code) {
		if (code == KeyEvent.VK_C)
			gp.gameState = gp.PLAY_STATE;
		
		// Move the cursor around the inventory page without going out from its frame
		if (code == KeyEvent.VK_W) {
			if (gp.ui.slotRow != 0) {
				gp.ui.slotRow --;
				gp.playSoundEffect(9);
			}
		}
		
		if (code == KeyEvent.VK_S) {
			if (gp.ui.slotRow != 3) {
				gp.ui.slotRow ++;
				gp.playSoundEffect(9);
			}
		}
				
		if (code == KeyEvent.VK_A) {
			if (gp.ui.slotCol != 0) {
				gp.ui.slotCol --;
				gp.playSoundEffect(9);
			}
		}
		
		if (code == KeyEvent.VK_D) {
			if (gp.ui.slotCol != 4) {
				gp.ui.slotCol ++;
				gp.playSoundEffect(9);
			}
		}
	
		// Select the item in the inventory
		if (code == KeyEvent.VK_ENTER)
			gp.player.selectItem();
	}

	/**
	 * Handle the option menu state.
	 * 
	 * @param code The key pressed.
	 */
	public void optionsState(int code) {
		if (code == KeyEvent.VK_ESCAPE)
			gp.gameState = gp.PLAY_STATE;
		
		if (code == KeyEvent.VK_ENTER)
			enterPressed = true;
		
		int maxCommandNumber = 0;
		
		switch (gp.ui.subState) {
			case 0: maxCommandNumber = 5;
		}
		
		// Move the cursor around the option menu
		if (code == KeyEvent.VK_W) {
			gp.ui.commandNumber --;
			gp.playSoundEffect(9);
			
			if (gp.ui.commandNumber < 0)
				gp.ui.commandNumber = maxCommandNumber;
		} 
		
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNumber ++;
			gp.playSoundEffect(9);
			
			if (gp.ui.commandNumber > maxCommandNumber)
				gp.ui.commandNumber = 0;	
		} 
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		// Title state
		if (gp.gameState == gp.TITLE_STATE) 
			titleState(code);
		
		// Play state
		else if (gp.gameState == gp.PLAY_STATE)
			playState(code);
		
		// Pause state
		else if (gp.gameState == gp.PAUSE_STATE)
			pauseState(code);
		
		// Dialog state
		else if (gp.gameState == gp.DIALOG_STATE)
			dialogState(code);
		
		// Character state
		else if (gp.gameState == gp.CHARACTER_STATE)
			characterState(code);
		
		// Options state
		else if (gp.gameState == gp.OPTIONS_STATE)
			optionsState(code);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W)
			upPressed = false;
		
		if (code == KeyEvent.VK_S)
			downPressed = false;
		
		if (code == KeyEvent.VK_A)
			leftPressed = false;
		
		if (code == KeyEvent.VK_D)
			rightPressed = false;
		
		if (code == KeyEvent.VK_F)
			shotKeyPressed = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
