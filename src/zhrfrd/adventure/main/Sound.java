package zhrfrd.adventure.main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[14];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sounds/soundtrack.wav");
		soundURL[1] = getClass().getResource("/sounds/coin.wav");
		soundURL[2] = getClass().getResource("/sounds/powerup.wav");
		soundURL[3] = getClass().getResource("/sounds/unlock.wav");
		soundURL[4] = getClass().getResource("/sounds/fanfare.wav");
	}
	
	/*
	 * Open audio file 
	 */
	public void setFile(int i) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch(Exception e) {
			
		}
	}
	
	/*
	 * Play audio file
	 */
	public void play() {
		clip.start();
	}
	
	/*
	 * Loop audio file (example: sound-track)
	 */
	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	/*
	 * Stop audio file
	 */
	public void stop() {
		clip.stop();
	}
}
