package zhrfrd.adventure2.main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[14];
	FloatControl floatControl;
	int volumeScale = 3;   // Default volume level
	float volume;
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sounds/soundtrack.wav");
		soundURL[1] = getClass().getResource("/sounds/coin.wav");
		soundURL[2] = getClass().getResource("/sounds/powerup.wav");
		soundURL[3] = getClass().getResource("/sounds/unlock.wav");
		soundURL[4] = getClass().getResource("/sounds/fanfare.wav");
		soundURL[5] = getClass().getResource("/sounds/hitmonster.wav");
		soundURL[6] = getClass().getResource("/sounds/receivedamage.wav");
		soundURL[7] = getClass().getResource("/sounds/swingweapon.wav");
		soundURL[8] = getClass().getResource("/sounds/levelup.wav");
		soundURL[9] = getClass().getResource("/sounds/cursor.wav");
		soundURL[10] = getClass().getResource("/sounds/burning.wav");
		soundURL[11] = getClass().getResource("/sounds/cuttree.wav");
		soundURL[12] = getClass().getResource("/sounds/gameover.wav");
	}
	
	/**
	 * Open audio file.
	 * 
	 *  @param i Index of the soundURL (file audio).
	 */
	public void setFile(int i) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);   // Enable changing volume of the clip  (-80f to 6f)
			
			checkVolume();
		} catch(Exception e) {
			
		}
	}
	
	/**
	 * Play audio file.
	 */
	public void play() {
		clip.start();
	}
	
	/**
	 * Loop audio file (example: sound-track).
	 */
	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * Stop audio file.
	 */
	public void stop() {
		clip.stop();
	}
	
	/**
	 * Assign the volume level to the FloatControl in relationship to the volumeScale selected. 
	 * -80f is the minimum volume level accepted.
	 * +6f is the maximum volume level accepted. 
	 */
	public void checkVolume() {
		switch (volumeScale) {
			case 0: volume = -80f; break;
			case 1: volume = -20f; break;
			case 2: volume = -12f; break;
			case 3: volume = -5f; break;
			case 4: volume = 1f; break;
			case 5: volume = 6f; break;
		}
		
		floatControl.setValue(volume);
	}
}
