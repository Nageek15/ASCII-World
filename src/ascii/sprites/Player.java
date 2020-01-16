package ascii.sprites;

import java.awt.Point;

import ascii.App;
import gameutil.text.Console;

public class Player extends Sprite{
	
	private Inventory i;
	private int jump=0;
	private int maxJump=3;
	private int jumpHorizontalVelocity=0;
	private boolean jumping=false;
	
	/**For new game
	 * 
	 * @param name
	 */
	public Player(String name) {
		super(name,'@',new Point(0,5),10);
		i=new Inventory(this);
		i.add(new Scroll("Scroll of Grabbing","This is a command scroll. They will tell you a list of things that you can type and what it will do.\n*note that \" \" are used for arguments (<argument>) that have spaces in them\n\nCommands:\ngrab - grab something"));
		i.add(new Scroll("Scroll of Walking", "walk <left/right> - walk left or walk right\n  nex: walk right"));
	}
	
	/**For loading from file
	 * 
	 * @param name
	 * @param pos
	 * @param hp
	 * @param mhp
	 * @param jump
	 * @param maxJump
	 * @param jumpHorizontalVelocity
	 * @param jumping
	 */
	public Player(String name, Point pos, int hp,int mhp,int jump,int maxJump,int jumpHorizontalVelocity,boolean jumping) {
		super(name,'@',pos,mhp);
		this.hp=hp;//set hp
		this.jump=jump;
		this.maxJump=maxJump;
		this.jumpHorizontalVelocity=jumpHorizontalVelocity;
		this.jumping=jumping;
		i=new Inventory(this);
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
		if (jumping==true) {
			jump--;
			attemptMove(new Point(getX()+jumpHorizontalVelocity,getY()+1));
			if (jump<=0) {
				jumping=false;
			}
		} else {
			attemptMove(new Point(getX(),getY()-1));
		}
	}
	
	/**Walk sideways.
	 * 
	 * @param distance
	 */
	public void walk(int distance) {
		//has to have solid below to push on to walk and yes; you can walk and jump at the same time as long as you have something under you.
		if (App.map.solidAt(new Point(getX(),getY()-1))) {
			attemptMove(new Point(getX()+distance,getY()));
		}
	}
	
	public void pickup() {
		if (App.map.isItemAt(pos)) {
			Item item=App.map.itemAt(pos);
			i.add(item);
			Console.s.println("You found a(n) "+item.getName()+"!");
		} else {
			Console.s.println("There was nothing there.");
		}
		Console.s.pause();
	}
	
	public void printInv() {
		i.printContents();
	}
	
	public int getHealth() {
		return hp;
	}
	
	public int getMaxHealth() {
		return maxhp;
	}
	
	public int getJump() {
		return jump;
	}
	
	public int getMaxJump() {
		return maxJump;
	}
	
	public int getJumpHorizontalVelocity() {
		return jumpHorizontalVelocity;
	}
	
	public boolean isJumping() {
		return jumping;
	}
	
}
