package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class Boots extends Entity{
	public Boots(GamePanel gp) {
		super(gp);
		name = "Boots";
		down1 = setup("/objects/boots");
	}
}
