package zhrfrd.adventure2.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
	GamePanel gp;
	
	public Config(GamePanel gp) {
		this.gp = gp;
	}
	
	/**
	 * Save the game settings to the configuration file.
	 */
	public void saveConfig() {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("config.txt"));
			
			// Full screen
			if (gp.fullScreenOn)
				bufferedWriter.write("On");
			
			if (!gp.fullScreenOn)
				bufferedWriter.write("Off");
			
			bufferedWriter.newLine();
			
			// Music volume
			bufferedWriter.write(String.valueOf(gp.soundTrack.volumeScale));
			bufferedWriter.newLine();
			
			// Sound effects volume
			bufferedWriter.write(String.valueOf(gp.soundEffect.volumeScale));
			bufferedWriter.newLine();
			
			bufferedWriter.close();
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Load the game settings saved inside the configuration file.
	 */
	public void loadConfig() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("config.txt"));
			String text = bufferedReader.readLine();
			
			// Full screen
			if (text.equals("On"))
				gp.fullScreenOn = true;
			
			if (text.equals("Off"))
				gp.fullScreenOn = false;
			
			// Music volume
			text = bufferedReader.readLine();
			gp.soundTrack.volumeScale = Integer.parseInt(text);
			
			// Sound effects volume
			text = bufferedReader.readLine();
			gp.soundEffect.volumeScale = Integer.parseInt(text);
			
			bufferedReader.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
