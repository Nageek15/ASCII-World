package ascii.sprites;

import java.awt.Point;

import ascii.App;
import ascii.map.RayCastor;
import gameutil.geom.g2D.VectorR2;
import gameutil.text.Console;

public class Player extends Sprite{
	
	private Inventory i;
	private int jump=0;
	private int maxJump=3;
	private int atk;
	private int jumpHorizontalVelocity=0;
	private boolean jumping=false;
	private RayCastor r;
	private double lowestAudibleVolume=.1;
	private int letters;
	private int highestLevel=0;
	
	/**For new game
	 * 
	 * @param name
	 */
	public Player(String name,int hp) {
		super(name,'@',new Point(0,5),hp);
		atk=1;
		letters=0;
		i=new Inventory(this);
		i.add(new Scroll("Scroll of Grabbing","This is a command scroll. They will tell you a list of things that you can type and what it will do.\n*note that \" \" are used for arguments (<argument>) that have spaces in them\n\nCommands:\ngrab - grab something"));
		i.add(new Scroll("Scroll of Walking", "walk <left/right> - walk left or walk right\n  ex: walk right"));
		r=new RayCastor(31);
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
	public Player(String name, Point pos, int hp,int mhp,int jump,int maxJump,int jumpHorizontalVelocity,boolean jumping,double lowestAudibleVolume,int atk,int letters,int highestLevel) {
		super(name,'@',pos,mhp);
		this.hp=hp;//set hp
		this.jump=jump;
		this.maxJump=maxJump;
		this.jumpHorizontalVelocity=jumpHorizontalVelocity;
		this.jumping=jumping;
		this.atk=atk;
		this.lowestAudibleVolume=lowestAudibleVolume;
		this.letters=letters;
		this.highestLevel=highestLevel;
		i=new Inventory(this);
		r=new RayCastor(31);
		
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
		if (App.map.spriteAt(pos,'3')) {
			App.setFP(false);
		} else if (App.map.spriteAt(pos,'1')) {
			App.setFP(true);
		}
		if (jumping==true) {
			jump--;
			attemptMove(new Point(getX()+jumpHorizontalVelocity,getY()+1));
			if (jump<=0) {
				jumping=false;
			}
		} else {
			attemptMove(new Point(getX(),getY()-1));
		}
		
		if (App.map.spriteAt(pos, 'x')) {
			inWorld=false;//sprite kills player
		}
		if (App.map.spriteAt(pos, '#')){
			hp--;
		}
		if (hp<=0) {
			inWorld=false;
		}
		if (App.map.spriteAt(pos,'3')) {
			App.setFP(false);
		} else if (App.map.spriteAt(pos,'1')) {
			App.setFP(true);
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
		} else if (App.map.isItemAt(new Point(pos.x+getDirection(),pos.y))) {
			Item item=App.map.itemAt(new Point(pos.x+getDirection(),pos.y));
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
	
	public void setHealth(int hp) {
		if (hp>maxhp) {
			hp=maxhp;
		}
		this.hp=hp;
	}
	
	public int getMaxHealth() {
		return maxhp;
	}
	
	public void setMaxHealth(int mhp) {
		maxhp=mhp;
		if (hp>mhp) {
			hp=mhp;
		}
	}
	
	public int getJump() {
		return jump;
	}
	
	public int getMaxJump() {
		return maxJump;
	}
	
	public void setMaxJump(int amount) {
		maxJump=amount;
	}
	
	public double getLowestAudibleVolume() {
		return lowestAudibleVolume;
	}
	
	public void setLowestAudibleVolume(double d) {
		lowestAudibleVolume=d;
	}
	
	public int getAtk() {
		return atk;
	}
	
	public void setAtk(int atk) {
		this.atk=atk;
	}
	
	public int getLetters() {
		return letters;
	}
	
	public void setLetters(int letters) {
		this.letters=letters;
	}
	
	public int getJumpHorizontalVelocity() {
		return jumpHorizontalVelocity;
	}
	
	public boolean isJumping() {
		return jumping;
	}
	
	public RayCastor getVision() {
		return r;
	}
	
	public void turn() {
		r.turn();
	}
	
	public int getDirection() {
		return r.getDirection();
	}
	
	public void hear(Sound s) {
		if (s.getAmplitude()>=lowestAudibleVolume) {
			s.play();
		}
	}
	
	
	
	public void yell() {
		Sound s=null;
		try {
			s = new Sound(pos,"lelelele",12,new VectorR2(r.getDirection(),0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		App.map.getCurrentLevel().add(s);
		s.play();
	}
	
	public void setHighestLevel(int l) {
		if (l>highestLevel) {
			highestLevel=l;
		}
	}
	
	public int getHighestLevel() {
		return highestLevel;
	}
	
}
