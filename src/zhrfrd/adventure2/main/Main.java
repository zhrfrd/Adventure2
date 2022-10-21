package zhrfrd.adventure2.main;

import javax.swing.JFrame;

public class Main {
	public static JFrame window;
	
	public static void main (String [] args) {
		window = new JFrame();
		GamePanel gp = new GamePanel();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Adventure 2");
//		window.setUndecorated(true);
		window.add(gp);
		window.pack();   // Causes the window to be sized to fit the preferred size and layouts of its subcomponents (GamePanel)
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gp.setupGame();
		gp.startGameThread();
	}
}