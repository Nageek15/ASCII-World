package ascii.sprites;

import java.awt.Point;

import ascii.App;

public class Sprite {
	protected final String name;
	protected final char c;
	protected Point pos;
	protected int hp;
	protected int maxhp;
	protected boolean solid;
	protected boolean space=false;
	protected boolean inWorld=true;
	protected boolean visible=true;
	
	public Sprite(String name,char c,Point pos) {
		this.name=name;
		this.c=c;
		this.pos=pos;
		hp=0;
		maxhp=0;
		solid=true;
	}
	
	public Sprite(String name,char c,Point pos,boolean solid) {
		this.name=name;
		this.c=c;
		this.pos=pos;
		hp=0;
		maxhp=0;
		this.solid=solid;
	}
	
	public Sprite(String name,char c) {
		this.name=name;
		this.c=c;
		this.pos=new Point(0,0);
		hp=0;
		maxhp=0;
		solid=true;
	}
	
	public Sprite(char c) {
		this.name=this.getClass().getSimpleName();
		this.c=c;
		this.pos=new Point(0,0);
		hp=0;
		maxhp=0;
		solid=true;
	}
	
	public Sprite(char c,Point pos) {
		this.name=this.getClass().getSimpleName();
		this.c=c;
		this.pos=pos;
		hp=0;
		maxhp=0;
		solid=true;
	}
	
	public Sprite(char c,Point pos,boolean solid) {
		this.name=this.getClass().getSimpleName();
		this.c=c;
		this.pos=pos;
		hp=0;
		maxhp=0;
		this.solid=solid;
	}
	
	public Sprite(String name,char c,Point pos,int hp) {
		this.name=name;
		this.c=c;
		this.pos=pos;
		this.hp=hp;
		maxhp=hp;
		solid=true;
	}
	
	public Sprite(String name,char c,int hp) {
		this.name=name;
		this.c=c;
		this.pos=new Point(0,0);
		this.hp=hp;
		maxhp=hp;
		solid=true;
	}
	
	public Sprite(char c,int hp) {
		this.name=this.getClass().getSimpleName();
		this.c=c;
		this.pos=new Point(0,0);
		this.hp=hp;
		maxhp=hp;
		solid=true;
	}
	
	public Sprite(char c,Point pos,int hp) {
		this.name=this.getClass().getSimpleName();
		this.c=c;
		this.pos=pos;
		this.hp=hp;
		maxhp=hp;
		solid=true;
	}
	
	public Sprite(String name,char c,Point pos,int hp,boolean solid) {
		this.name=name;
		this.c=c;
		this.pos=pos;
		this.hp=hp;
		maxhp=hp;
		this.solid=solid;
	}
	
	public Sprite() {
		this.name=this.getClass().getSimpleName();
		this.c='-';
		this.pos=new Point(0,0);
		hp=1;
		maxhp=1;
		solid=true;
	}
	
	public void setPos(Point pos) {
		this.pos=pos;
	}
	
	public char getChar() {
		return c;
	}
	
	public String getName() {
		return name;
	}
	
	public Point getPos() {
		return pos;
	}
	
	//override this
	public void update() {
		
	}
	
	//override this
	public void interact() {
		
	}
	
	public boolean attemptMove(Point pos) {
		if (App.map.solidAt(pos)) {
			return false;
		} else {
			this.pos=pos;
			return true;
		}
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	/**Should return the properties as a string that can be used to load the sprite
    *
    * @return
    */
   public String getProps() {
		return "String "+this.getClass().getTypeName()+" char - ";
	}
   
   public int getX() {
	   return pos.x;
   }
   
   public int getY() {
	   return pos.y;
   }
   
   public boolean useSpace() {
	   return space;
   }
   
   public boolean inWorld() {
	   return inWorld;
   }
   
   public void damage(int no) {
	   if (maxhp<=0) {
		   return;
	   }
		hp-=no;
	}
   
   public void setVisible(boolean visible) {
	   this.visible=visible;
   }
   
   public boolean isVisible() {
	   return visible;
   }
}
