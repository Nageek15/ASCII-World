package ascii.sprites;

import java.awt.Point;

public class Player extends Sprite{
	
	private Inventory i;
	private int jump;
	private int maxJump=5;
	private boolean jumping=false;
	
	public Player(String name) {
		super(name,'@',new Point(0,5),10);
	}
	
	public Inventory getInventory() {
		return i;
	}
	
	public void jump() {
		if (!jumping) {
			jumping=true;
			jump=maxJump;
		}
	}
	
	public void jump(int amount) {
		if (!jumping) {
			jumping=true;
			jump=amount;
			if (jump>maxJump) {
				jump=maxJump;
			}
		}
	}
	
	@Override
	public void update() {
		if (jumping=true) {
			
		}
	}
	
}
