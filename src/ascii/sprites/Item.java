package ascii.sprites;

import java.awt.Point;

import gameutil.text.Console;

public class Item extends Sprite{
	protected boolean useable;
	protected boolean inWorld;
	protected String desc;
	
	public Item() {
		super('*',new Point(0,0),false);
		inWorld=false;
		desc="An item";
		useable=false;
	}
	
	public Item(Point pos) {
		super('*',pos,false);
		inWorld=true;
		desc="An item";
		useable=true;
	}
	
	public Item(Point pos,String desc,boolean useable) {
		super('*',pos,false);
		this.desc=desc;
		this.useable=useable;
	}
	
	public Item(Point pos,String desc,boolean useable,boolean inWorld) {
		super('*',pos,false);
		this.desc=desc;
		this.useable=useable;
		this.inWorld=inWorld;
	}
	
	public Item(String name,Point pos,String desc,boolean useable) {
		super(name,'*',pos,false);
		this.desc=desc;
		this.useable=useable;
	}
	
	public Item(String name,Point pos,String desc,boolean useable,boolean inWorld) {
		super(name,'*',pos,false);
		this.desc=desc;
		this.useable=useable;
		this.inWorld=inWorld;
	}
	
	public Item(char c,Point pos,String desc,boolean useable,boolean inWorld) {
		super('*',pos,false);
		this.desc=desc;
		this.useable=useable;
		this.inWorld=inWorld;
	}
	
	public Item(String name,char c,Point pos,String desc,boolean useable) {
		super(name,'*',pos,false);
		this.desc=desc;
		this.useable=useable;
	}
	
	public Item(String name,char c,Point pos,String desc,boolean useable,boolean inWorld) {
		super(name,'*',pos,false);
		this.desc=desc;
		this.useable=useable;
		this.inWorld=inWorld;
	}
	
	public void use() {
		Console.s.println("Nothing happens.");
	}

}
