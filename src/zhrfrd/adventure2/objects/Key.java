package zhrfrd.adventure2.objects;

import zhrfrd.adventure2.entities.Entity;
import zhrfrd.adventure2.main.GamePanel;

public class Key extends Entity {
	public Key(GamePanel gp) {
		super(gp);
		name = "Key";
		down1 = setup("/objects/key");
	}
}
