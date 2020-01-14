package ascii.sprites;

import java.awt.Point;

import ascii.App;

public class Player extends Sprite{
	
	private Inventory i;
	private int jump=0;
	private int maxJump=3;
	private int jumpHorizontalVelocity=0;
	private boolean jumping=false;
	
	public Player(String name) {
		super(name,'@',new Point(0,5),10);
	}
	
	public Inventory getInventory() {
		return i;
	}
	
	public void jump(int horizontalVelocity) {
		jump(horizontalVelocity,maxJump);
	}
	
	public void jump(int horizontalVelocity,int amount) {
		//can't already be jumping and has to have solid below to push on to jump
		if (!jumping&&App.map.solidAt(new Point(getX(),getY()-1))) {
			jumping=true;
			jump=amount;
			jumpHorizontalVelocity=horizontalVelocity;
			if (jump>maxJump) {
				jump=maxJump;
				
			}
		}
	}
	
	@Override
	public void update() {
		if (jumping=true) {
			jump--;
			attemptMove(new Point(getX()+jumpHorizontalVelocity,getY()-1));
			if (jump<=0) {
				jumping=false;
			}
		} else {
			attemptMove(new Point(getX(),getY()+1));
		}
	}
	
	/**Walk sideways.
	 * 
	 * @param distance
	 */
	public void walk(int distance) {
		//has to have solid below to push on to walk
		if (App.map.solidAt(new Point(getX(),getY()+1))) {
			attemptMove(new Point(getX()-distance,getY()));
		}
	}
	
}
